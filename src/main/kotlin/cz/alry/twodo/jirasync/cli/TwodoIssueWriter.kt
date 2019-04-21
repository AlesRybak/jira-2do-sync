package cz.alry.twodo.jirasync.cli

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URLEncoder

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class TwodoIssueWriter(private val dataFolder: File, private val commandExecutor: CommandExecutor): IssueWriter {

    private val syncedIssuesDataFile = File(dataFolder, FILE_SYNCED_ISSUES)
    private val syncedIssueKeys = getSyncedIssuesKeys()

    override fun isNewIssue(issue: MyIssue): Boolean = !syncedIssueKeys.contains(issue.key)

    override fun writeIssue(issue: MyIssue) {
        if (isNewIssue(issue)) {
            syncIssueToTwodo(issue)
        }
    }

    private fun urlEncode(input: String): String = URLEncoder.encode(input, "UTF-8").replace("+", "%20")

    private fun syncIssueToTwodo(issue: MyIssue) {
        LOG.debug("Syncing issue: {}", issue.key)

        val twodoUrl = issueToTwodoActionUrl(issue)
        LOG.debug("twodoUrl is '{}'", twodoUrl)

        val exitCode = commandExecutor.exec("open $twodoUrl").exitCode
        if (exitCode == 0) {
            syncedIssuesDataFile.appendText(text = "${issue.key}\n")
            LOG.info("Issue {} synced to 2Do", issue.key)
        }
    }

    private fun getSyncedIssuesKeys(): List<String> {
        if (!syncedIssuesDataFile.exists()) {
            if (syncedIssuesDataFile.createNewFile()) {
                LOG.debug("Created empty file: {}", syncedIssuesDataFile)
            }
        }

        val syncedIssueKeys = syncedIssuesDataFile.bufferedReader().readLines()

        LOG.debug("Loaded {} keys of previously synced issues.", syncedIssueKeys.size)

        return syncedIssueKeys
    }

    private fun issueToTwodoActionUrl(issue: MyIssue): String {
        val taskText = urlEncode(issue.title)
        val taskNote = urlEncode(issue.url)
        val taskTags = "jira,imported"

        return "twodo://x-callback-url/add?task=${taskText}&note=${taskNote}&tags=${taskTags}"
    }

    companion object {
        @JvmStatic
        val LOG: Logger = LoggerFactory.getLogger(TwodoIssueWriter::class.java)
    }

}
