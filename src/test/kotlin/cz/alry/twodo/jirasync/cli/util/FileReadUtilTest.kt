package cz.alry.twodo.jirasync.cli.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
internal class FileReadUtilTest {

    @Test
    fun `Test readFileIntoString`() {
        // Given: some content
        val expectedResult = "My little test"

        // When: I Create temp file with the content
        val tempFile = createTempFile()
        tempFile.writeText(expectedResult)

        // When: I read the content from the temp file
        val actualResult = FileReadUtil.readFileIntoString(tempFile.path)

        // Then: the content read from file must match
        assertThat(actualResult).isEqualTo(expectedResult)

        // After: Remove the temp file
        tempFile.delete()
    }

    @Test
    fun readResourceIntoString() {
        val fileContent = FileReadUtil.readResourceIntoString("json/jira_search_my-open-issues_result.json")

        assertThat(fileContent)
                .isNotNull()
                .isNotBlank()
                .contains("schema,names")
    }
}