package cz.alry.twodo.jirasync.cli.client.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class JiraIssueFields (
        val summary: String
)