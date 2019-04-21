package cz.alry.twodo.jirasync.cli

import cz.alry.twodo.jirasync.cli.client.JiraClient
import cz.alry.twodo.jirasync.cli.client.JiraClientMock
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SyncCommandTest {

    val dataFolder: File = createTempDir("test-data-dir")
    val jiraClientMock: JiraClient = JiraClientMock()
    val issueWriterMock: IssueWriter = mockk()
    val syncConfig: SyncConfig = SyncConfig("testHost", "testUser", "pass", dataFolder)
    val syncCommand: SyncCommand = SyncCommand(syncConfig, jiraClientMock, issueWriterMock)

    @BeforeEach
    fun beforeEachTest() {
        clearMocks(issueWriterMock)
        every { issueWriterMock.isNewIssue(any()) } answers { true }
        every { issueWriterMock.writeIssue(any()) } answers {  }
    }

    @Test
    fun `Single run would sync all tasks`() {
        // When: I run the sync
        syncCommand.run()

        // Than: I get 15 issues created
        verify(exactly = 15) { issueWriterMock.isNewIssue(allAny()) }
        verify(exactly = 15) { issueWriterMock.writeIssue(allAny()) }
    }

}