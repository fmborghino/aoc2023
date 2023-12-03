import java.lang.RuntimeException

fun main() {
    fun log(message: Any?) {
        println(message)
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val digitsOnly = line.filter { it.isDigit() }
            acc + "${digitsOnly.first()}${digitsOnly.last()}".toInt()
        }
    }

    fun firstDigit(line: String, indices: IntProgression): Int {
        for (i in indices) {
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
        return input.fold(0) { acc, line ->
            val indices = line.indices
            acc + "${firstDigit(line, indices)}${firstDigit(line, indices.reversed())}".toInt()
        }
    }

    verify("Test part 1", part1(readInput("test/Day01_part1.txt")), 142)

    verify("Winston part 1", part1(readInput("ww/Day01.txt")), 55208)

    verify("Max part 1", part1(readInput("mb/Day01.txt")), 56049)

    verify("Test part 2", part2(readInput("test/Day01_part2.txt")) , 281)

    verify("Winston part 2", part2(readInput("ww/Day01.txt")), 54578)

    verify("Max part 2", part2(readInput("mb/Day01.txt")), 54530)
}
