package cz.alry.twodo.jirasync.cli.client.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class JiraIssuesList(
        val issues: List<JiraIssue>,
        val startAt: Int = 10,
        val total: Int = 1
)