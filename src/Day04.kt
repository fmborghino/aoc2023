import kotlin.math.pow
import kotlin.system.exitProcess

fun main() {
    fun log(message: Any?) {
//        println(message)
    }

    fun parseToSets(line: String): List<Set<String>> = line
        .substringAfter(":")
        .split("|")
        .map {
            it.trim()
                .split(Regex("\\s{1,2}"))
                .toSet()
        }

    fun part1(input: List<String>): Long {
        var result = 0L
        input.forEachIndexed() { i, line ->
            val (winning, have) = parseToSets(line)
            val intersect = winning.intersect(have)
            val score = (2.0).pow(intersect.size.toDouble() - 1.0).toLong()
            log("$i: winning=$winning have=$have intersect=$intersect size=${intersect.size} score=$score")
            result += score // ie size->score: 1->1, 2->2, 3->4, 4->8
        }
        return result
    }

    fun part2(inputInit: List<String>): Long {
        val input = inputInit.toMutableList()
        var index = 0;
        while (index < input.size) {
            val line = input[index]
            val card = line.substringBefore(":").split(Regex("\\s{1,3}"))[1].toInt()
            val (winning, have) = parseToSets(line)
            val intersectSize = winning.intersect(have).size
            if (intersectSize > 0) {
                val subList = input.subList(card, card + intersectSize)
                log("$index: Card $card: winning=$winning have=$have intersectSize=$intersectSize")
                log("  add: ${subList.map { it.substringBefore(":") }}")
                input.addAll(ArrayList(subList))
            }
            index++
        }
        return input.size.toLong()
    }

    verify("Test part 1", part1(readInput("test/Day04.txt")), 13L)

//    verify("Winston part 1", part1(readInput("ww/Day04.txt")), 999L)

    verify("Max part 1", part1(readInput("mb/Day04.txt")), 21105L)

    verify("Test part 2", part2(readInput("test/Day04.txt")) , 30L)

//    verify("Winston part 2", part2(readInput("ww/Day04.txt")), 999L)

    verify("Max part 2", part2(readInput("mb/Day04.txt")), 5329815L)
}
