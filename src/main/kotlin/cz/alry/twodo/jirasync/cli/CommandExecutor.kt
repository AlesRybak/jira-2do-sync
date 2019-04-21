package cz.alry.twodo.jirasync.cli

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
interface CommandExecutor {

    fun exec(command: String): ExecResult

}
