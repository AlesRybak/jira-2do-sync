package cz.alry.twodo.jirasync.cli.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import cz.alry.twodo.jirasync.cli.client.dto.JiraIssuesList
import cz.alry.twodo.jirasync.cli.util.FileReadUtil.readResourceIntoString

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class JiraClientMock : JiraClient {

    override fun search(jql: String, fields: String): JiraIssuesList {
        val jacksonMapper = ObjectMapper().registerModule(KotlinModule())
        val fileContent = readResourceIntoString("json/jira_search_my-open-issues_result.json")
        return jacksonMapper.readValue(fileContent)
    }

}