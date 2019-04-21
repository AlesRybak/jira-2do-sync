package cz.alry.twodo.jirasync.cli


import cz.alry.twodo.jirasync.cli.client.JiraClient
import cz.alry.twodo.jirasync.cli.client.dto.JiraIssue
import cz.alry.twodo.jirasync.cli.client.dto.JiraIssuesList
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class SyncCommand(val config: SyncConfig, val jiraClient: JiraClient, val issueWriter: IssueWriter) {

    fun run() {
        LOG.info("Requesting for open issues.")
        val jiraIssues: JiraIssuesList = jiraClient.search(MY_OPEN_ISSUES_JQL, "key,summary")
        LOG.info("Received {} issues.", jiraIssues.issues.size)


        if (jiraIssues.total > 0) {
            val allIssues: List<MyIssue> = jiraIssues.issues
                    .map { jiraIssue -> convertJiraIssueToMyIssue(jiraIssue, config.jiraHost)}

            val newIssues: List<MyIssue> = allIssues
                    .filter { issue -> issueWriter.isNewIssue(issue) }

            LOG.info("Categorized issues: previously synced = {}, new = {}",
                    allIssues.size - newIssues.size, newIssues.size)

            if (newIssues.isNotEmpty()) {
                LOG.info("Syncing new issues into 2Do app.")
                newIssues.forEach { issue -> issueWriter.writeIssue(issue) }
            } else {
                LOG.info("No new issues found.")
            }
        }
    }

    private fun convertJiraIssueToMyIssue(jiraIssue: JiraIssue, jiraHost: String) =
            MyIssue(jiraIssue.key, jiraIssue.fields.summary, getJiraIssueUrl(jiraIssue, jiraHost))

    private fun getJiraIssueUrl(jiraIssue: JiraIssue, jiraHost: String) =
            "$jiraHost/browse/${jiraIssue.key}"


    companion object {
        @JvmStatic
        val LOG: Logger = LoggerFactory.getLogger(SyncCommand::class.java)
    }

}
