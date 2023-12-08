private fun log(message: Any?) {
    println(message)
}

private data class Hand(val s: String, val hand: List<Int>, val groupings: List<Int>, val bet: Int) {
    companion object {
        val pics = mapOf("A" to 14, "K" to 13, "Q" to 12, "J" to 11, "T" to 10)

        fun of(input: String): Hand {
            val (handStr, betStr) = input.split(" ")
            val handNums = handStr.map { (if (pics.containsKey(it.toString())) pics[it.toString()] else it.digitToInt()) ?: 0 }
            val groupings = handNums.groupBy { it }.map { it.value.size }.sorted().reversed()
            val hand = Hand(handStr, handNums, groupings, betStr.toInt())
            return hand
        }
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        input
            .map { Hand.of(it).also(::log) }
            .sortedBy { it.groupings.size } // TODO: yeah the secret sauce is gonna be here
            .reversed()
            .also { log("---") }
            .map { it.also(::log) }

        return -1
    }

    fun part2(input: List<String>): Int {
        return -2
    }

    verify("Test part 1", part1(readInput("test/Day07.txt")), 6440L)

//    verify("Winston part 1", part1(readInput("ww/Day07.txt")), 999)

//    verify("Max part 1", part1(readInput("mb/Day07.txt")), 999)

//    verify("Test part 2", part2(readInput("test/Day07.txt")) , 999)

//    verify("Winston part 2", part2(readInput("ww/Day07.txt")), 999)

//    verify("Max part 2", part2(readInput("mb/Day07.txt")), 999)
}
