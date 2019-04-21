package cz.alry.twodo.jirasync.cli

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
interface IssueWriter {

    fun isNewIssue(issue: MyIssue): Boolean

    fun writeIssue(issue: MyIssue)

}