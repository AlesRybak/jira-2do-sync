package cz.alry.twodo.jirasync.cli.client

import cz.alry.twodo.jirasync.cli.client.dto.JiraIssuesList
import feign.CollectionFormat
import feign.Param
import feign.RequestLine

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
interface JiraClient {

    @RequestLine(
            "GET /rest/api/2/search?jql={jql}&fields={fields}",
            collectionFormat = CollectionFormat.CSV)
    fun search(
        @Param("jql") jql: String,
        @Param("fields") fields: String
    ): JiraIssuesList

}
