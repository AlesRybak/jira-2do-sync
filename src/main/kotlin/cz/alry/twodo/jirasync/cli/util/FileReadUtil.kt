package cz.alry.twodo.jirasync.cli.util

import java.io.File

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
object FileReadUtil {

    fun readFileIntoString(fileName: String): String {
        return File(fileName).bufferedReader().use { it.readText() }
    }

    fun readResourceIntoString(resourceName: String): String {
        val fileName = ClassLoader.getSystemResource(resourceName).file
        return readFileIntoString(fileName)
    }

}