import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
//fun readInput(name: String) = Path("src/$name.txt").readLines()
fun readInput(name: String) = File("res", name)
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
fun Any?.log() = println(this)

val COLOR_GREEN = "\u001B[0;32m"
val COLOR_RED = "\u001B[0;31m"
val COLOR_RESET = "\u001B[0m"
fun verify(msg: String, actual: Int, expected: Int):Unit {
    val output = if (actual == expected) "$msg\n    ${COLOR_GREEN}$actual == $expected — OK ${COLOR_RESET}"
    else "$msg\n    ${COLOR_RED}$actual != $expected — FAIL ${COLOR_RESET}"
    println(output)
}
