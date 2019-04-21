package cz.alry.twodo.jirasync.cli

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
data class ExecResult(
        val stdout: String,
        val stderr: String,
        val exitCode: Int
)
