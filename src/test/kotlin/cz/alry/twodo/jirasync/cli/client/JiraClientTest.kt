package cz.alry.twodo.jirasync.cli.client

import cz.alry.twodo.jirasync.cli.MY_OPEN_ISSUES_JQL
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class JiraClientTest {

    val jiraClient: JiraClient = JiraClientMock()

    @Test
    fun testJiraClientMockSearch() {
        val searchResult = jiraClient.search(MY_OPEN_ISSUES_JQL, "key,summary")

        assertThat(searchResult).isNotNull
        assertThat(searchResult.issues)
                .isNotNull
                .isNotEmpty
                .hasSize(15)
    }

}