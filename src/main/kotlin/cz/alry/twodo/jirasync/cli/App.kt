package cz.alry.twodo.jirasync.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import java.io.File

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        CliCommand().main(args)
    }
}

class CliCommand: CliktCommand() {

    private val host by option("-h", help="Jira server").default("https://jira.lnd.bz")
    private val login by option("-u", help="User Login").required()
    private val password by option("-p", help="User Password").required()
    private val dataFolder by option("-d", help="Data folder").default(System.getProperty("user.home") + "/Library/2do-jira-sync")

    override fun run() {
        val syncConfig = SyncConfig(host, login, password, File(dataFolder))
        val core = SyncCore(syncConfig)
        core.run()
    }
}
