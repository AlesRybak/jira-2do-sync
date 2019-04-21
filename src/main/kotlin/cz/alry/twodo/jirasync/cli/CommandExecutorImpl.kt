package cz.alry.twodo.jirasync.cli

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class CommandExecutorImpl: CommandExecutor {

    private val runtime = Runtime.getRuntime()

    override fun exec(command: String): ExecResult {
        if (command.isBlank()) {
            return ExecResult("", "", 0)
        }

        val process = runtime.exec(command).onExit().get()
        return ExecResult(
                process.inputStream.bufferedReader().use { it.readText() },
                process.errorStream.bufferedReader().use { it.readText() },
                process.exitValue())
    }

}