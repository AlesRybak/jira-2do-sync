package cz.alry.twodo.jirasync.cli

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class SyncCoreTest {

    @Test
    fun `Test core creates context`() {
        // Given: temp folder for configuration
        val tempFolder = createTempDir(prefix = "sync-core-test")
        tempFolder.mkdirs()

        // When: I create the config and core
        val config = SyncConfig("https://mytest", "test", "test", tempFolder)
        val core = SyncCore(config)

        // Then: The core is created without problem
        assertThat(core).isNotNull
        assertThat(core).hasFieldOrProperty("dataFolder")

        // After: delete the temp folder
        tempFolder.deleteRecursively()
    }

}