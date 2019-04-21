package cz.alry.twodo.jirasync.cli

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assumptions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
internal class CommandExecutorImplTest {

    private val commandExecutor: CommandExecutor = CommandExecutorImpl()

    @Test
    fun `Can execute empty command`() {
        // When
        val (stdout, stderr, exitCode) = commandExecutor.exec("")

        // Then
        assertThat(stdout).isBlank()
        assertThat(stderr).isBlank()
        assertThat(exitCode).isEqualTo(0)
    }

    @Test
    fun `Can execute echo command on MacOS`() {
        // This test runs only on MacOS
        Assumptions.assumeThat(System.getProperty("os.name").toLowerCase() == "mac")

        // When
        val (stdout, stderr, exitCode) = commandExecutor.exec("echo -n hello")

        // Then
        assertThat(stdout).isEqualTo("hello")
        assertThat(stderr).isBlank()
        assertThat(exitCode).isEqualTo(0)
    }
    
}