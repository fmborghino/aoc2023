import java.lang.Character.isDigit
import java.lang.RuntimeException

fun main() {
    val _day_ = "01"
    fun log(message: Any?) {
        println(message)
    }

    fun part1(input: List<String>): Int {
//        var total = 0
//        input.forEach { line ->
//            val digitsOnly = line.filter { it.isDigit() }
//            total += "${digitsOnly.first()}${digitsOnly.last()}".toInt()
//        }
//        return total

        return input.fold(0) { acc, line ->
            val digitsOnly = line.filter { it.isDigit() }
            acc + "${digitsOnly.first()}${digitsOnly.last()}".toInt()
        }
    }

    fun firstDigit(line: String): Int {
        for (i in line.indices) {
            val sub = line.substring(i)
            val digit = when {
                sub.first().isDigit() -> sub.first().toString().toInt()
                sub.startsWith("one") -> 1
                sub.startsWith("two") -> 2
                sub.startsWith("three") -> 3
                sub.startsWith("four") -> 4
                sub.startsWith("five") -> 5
                sub.startsWith("six") -> 6
                sub.startsWith("seven") -> 7
                sub.startsWith("eight") -> 8
                sub.startsWith("nine") -> 9
                else -> null
            }
            if (digit != null) {
                return digit
            }
        }
        throw RuntimeException("yikes")
    }

    fun lastDigit(line: String): Int {
        for (i in line.indices.reversed()) {
            val sub = line.substring(i)
            val digit = when {
                sub.first().isDigit() -> sub.first().toString().toInt()
                sub.startsWith("one") -> 1
                sub.startsWith("two") -> 2
                sub.startsWith("three") -> 3
                sub.startsWith("four") -> 4
                sub.startsWith("five") -> 5
                sub.startsWith("six") -> 6
                sub.startsWith("seven") -> 7
                sub.startsWith("eight") -> 8
                sub.startsWith("nine") -> 9
                else -> null
            }
            if (digit != null) {
                return digit
            }
        }
        throw RuntimeException("yikes")
    }

    fun part2(input: List<String>): Int {
//        input.forEach { line ->
//            log(firstDigit(line))
//            log(lastDigit(line))
//            log("---")
//        }

        return input.fold(0) { acc, line ->
            acc + "${firstDigit(line)}${lastDigit(line)}".toInt()
        }
    }

    // test inputs
    val testInput = readInput("Day${_day_}_test.txt")

    // test part 1
    val test1 = part1(testInput)
    check(test1 == 142) { "!!! test part 1 failed with: $test1" }

    // game inputs
    val gameInput = readInput("Day${_day_}.txt")

    // game part 1
    val game1 = part1(gameInput)
    println("*** game part 1: $game1")
    check(game1 == 56049) { "!!! game part 1 failed with: $game1" }

    // test part 2
    val testInput2 = readInput("Day${_day_}_part2_test.txt")
    val test2 = part2(testInput2)
    check(test2 == 281) { "!!! test part 2 failed with: $test2" }

    // game part 2
    val game2 = part2(gameInput)
    println("*** game part 2: $game2")
    check(game2 == 54530) { "!!! game part 2 failed with: $game2" }
}
