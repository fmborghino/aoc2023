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

    fun part1(input: List<String>): Int {
        return input
            .filter { it.trim().isNotEmpty() }
            .map {
                Hand.of(it)
            }.sortedWith(compareBy(
                { it.groupings[0] },
                { it.groupings[1] },
                { it.hand[0] },
                { it.hand[1] },
                { it.hand[2] },
                { it.hand[3] },
                { it.hand[4] },
            ))
//            .map { it.also(::log) }
            .mapIndexed { index, hand ->
//                log("${index+1} ${hand.bet} = ${(index+1)*hand.bet}")
                (index + 1) * hand.bet
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return -2
    }

    verify("Test part 1", part1(readInput("test/Day07.txt")), 6440)

//    verify("Winston part 1", part1(readInput("ww/Day07.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day07.txt")), 251927063)

//    verify("Test part 2", part2(readInput("test/Day07.txt")) , 5905)

//    verify("Winston part 2", part2(readInput("ww/Day07.txt")), 999)

//    verify("Max part 2", part2(readInput("mb/Day07.txt")), 999)
}
