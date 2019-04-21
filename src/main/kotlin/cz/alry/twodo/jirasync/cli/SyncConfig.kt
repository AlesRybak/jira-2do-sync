package cz.alry.twodo.jirasync.cli

import java.io.File

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
data class SyncConfig(
    val jiraHost: String,
    val jiraUser: String,
    val jiraPassword: String,
    val dataFolder: File
)