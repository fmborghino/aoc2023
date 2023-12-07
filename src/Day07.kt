private fun sortHand(hand: String): String {
    val ordering = "AKQJT98765432"

    return hand.toCharArray()
        .sortedWith(compareBy { ordering.indexOf(it) })
        .joinToString("")
}

// type of hand, along with the factor that type multiplies the card scores to keep each type's score range separated
// figured out by hand based on the lowest of a type needing to beat the highest of the previous type
private enum class HAND_KIND(val factor: Long) {
    HIGH(1),
    PAIR(4),
    TWOPAIR(19),
    THREE(97),
    FULLHOUSE(542),
    FOUR(3351),
    FIVE(23122),
}

// lol
val cardValues = mapOf("A" to 14L, "K" to 13L, "Q" to 12L, "J" to 11L, "T" to 10L,
    "9" to 9L, "8" to 8L, "7" to 7L, "6" to 6L, "5" to 5L, "4" to 4L, "3" to 3L, "2" to 2L)

// more code for the same thing anyone?
private data class Card(val card: String)
private fun Card.value(): Long {
    val c = card.substring(0, 1) // just in case
    val pictures = mapOf("A" to 14L, "K" to 13L, "Q" to 12L, "J" to 11L, "T" to 10L)
    return when {
        c[0].isDigit() -> c.toLong()
        c in "AKQJT" -> pictures.getOrDefault(c, 0)
        else -> 0
    }
}

private data class Hand(val hand: String) {
    companion object {
        const val ORDERING = "AKQJT98765432"

        // instantiates Hand with sorted hand
        fun of(hand: String): Hand = Hand(
            hand.toCharArray()
                .sortedWith(compareBy { ORDERING.indexOf(it) })
                .joinToString("")
        )
    }
}

private fun Hand.score(): Long {
    // Hand already has the cards sorted from high to low
    // we can slurp in chars from left to right, and we'll encounter them in groups of 5 or 4 or 3 or 2
    // in the various legit combos, from there we can tell which enum HAND_TYPE we have to get the multiplying score factor
    // then score each card, multiply by the type factor...
    // and we'll have a sortable integer to order all the hands, because the factors are worked out that way
    // ... in a handy spreadsheet, trust me

    // TODO: a lotta work here

    return 1L
}

private data class HandBet(val hand: Hand, val bet: Long)

private fun HandBet.score(): Long = this.hand.score() * this .bet

fun main() {
    fun log(message: Any?) {
        println(message)
    }

    log(listOf(Hand.of("A23KAT"), Hand.of("23456"), Hand.of("65432"), Hand.of("4K7J2Q")))

    fun part1(input: List<String>): Long {
        return input.map { line ->
            val (handStr, betStr) = line.split(" ")
            val handBet = HandBet(Hand.of(handStr), betStr.toLong())
            handBet.score()
        }.fold(0) { acc, score -> acc + score}
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
