package cz.alry.twodo.jirasync.cli

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
internal class TwodoIssueWriterTest {

    private val dataFolder: File = createTempDir("test-data-dir")
    private val commandExecutorMock: CommandExecutor
    private val issueWriter: IssueWriter

    init {
        val configFile = File(dataFolder, FILE_SYNCED_ISSUES)
        configFile.createNewFile()
        configFile.appendText("KEY-2\n")

        commandExecutorMock = mockk()
        issueWriter = TwodoIssueWriter(dataFolder, commandExecutorMock)
    }

    @BeforeEach
    fun beforeEachTest() {
        clearMocks(commandExecutorMock)
    }


    @Test
    fun `Can distinguish new issues based on info in data folder`() {
        // Given: issues
        val issue1 = MyIssue("KEY-1", "My title 1", "https://mytest/browse/KEY-1")
        val issue2 = MyIssue("KEY-2", "My title 2", "https://mytest/browse/KEY-2")

        // Expect: KEY-1 to be new, KEY-2 not
        assertThat(issueWriter.isNewIssue(issue1)).isTrue()
        assertThat(issueWriter.isNewIssue(issue2)).isFalse()
    }

    @Test
    fun `Writes issue via command execution`() {
        // Configure mocks
        val commandSlot = slot<String>()
        every { commandExecutorMock.exec(capture(commandSlot)) } answers { ExecResult("", "", 0) }

        // Given: Issue
        val issue = MyIssue("KEY-1", "My title", "https://mytest/browse/KEY-1")

        // When: I write the issue
        issueWriter.writeIssue(issue)

        // Then: CommandExecutor has been called with `open` command
        assertThat(commandSlot.isCaptured).isTrue()
        assertThat(commandSlot.captured)
                .contains("open")
                .contains("twodo")
                .contains("add")
                .contains(issue.key)
    }

    @Test
    fun `Writes synced issues to data folder`() {
        // Configure mocks
        every { commandExecutorMock.exec(any()) } answers { ExecResult("", "", 0) }

        // Given: Issue
        val issue = MyIssue("KEY-3", "My title", "https://mytest/browse/KEY-3")

        // When: I write the issue
        issueWriter.writeIssue(issue)

        // Then: The issue key is in the synced-issues file
        assertThat(File(dataFolder, FILE_SYNCED_ISSUES).readText()).contains(issue.key)
    }
}