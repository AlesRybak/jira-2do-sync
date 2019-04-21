package cz.alry.twodo.jirasync.cli


import cz.alry.twodo.jirasync.cli.client.JiraClient
import cz.alry.twodo.jirasync.cli.client.JiraClientFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class SyncCore(private val config: SyncConfig) {

    private val commandExecutor: CommandExecutor
    private val dataFolder: File
    private val jiraClient: JiraClient
    private val issueWriter: IssueWriter
    private val syncCommand: SyncCommand

    init {
        val jiraClientFactory = JiraClientFactory()
        jiraClient = jiraClientFactory.getInstance(config.jiraHost, config.jiraUser, config.jiraPassword)

        commandExecutor = CommandExecutorImpl()

        dataFolder = getOrCreateDataFolder()

        issueWriter = TwodoIssueWriter(dataFolder, commandExecutor)

        syncCommand = SyncCommand(config, jiraClient, issueWriter)
    }

    fun run() {
        syncCommand.run()
    }

    private fun getOrCreateDataFolder(): File {
        val syncDataFolder = config.dataFolder

        LOG.info("Data folder: {}", syncDataFolder)

        if (!syncDataFolder.exists()) {
            syncDataFolder.mkdirs()
            LOG.info("Created non-existing data folder: {}", syncDataFolder)
        }

        return syncDataFolder
    }


    companion object {
        @JvmStatic
        val LOG: Logger = LoggerFactory.getLogger(SyncCore::class.java)
    }

}
