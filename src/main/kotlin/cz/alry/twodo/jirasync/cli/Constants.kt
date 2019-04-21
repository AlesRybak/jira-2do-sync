package cz.alry.twodo.jirasync.cli

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */

const val MY_OPEN_ISSUES_JQL: String = "status in (Unstarted, \"In Progress\", Reopened, \"To Do\", \"To Be Reviewed\", Backlog, Open) AND assignee in (currentUser()) order by updated DESC"

const val FILE_SYNCED_ISSUES: String = "synced-issues.txt"
