private data class Race(val time: Long, val distance: Long) {
    companion object {
        fun of(pair: Pair<Long, Long>): Race = Race(pair.first, pair.second)
    }
}


fun main() {
    fun log(message: Any?) {
        println(message)
    }

    //    Time:      7  15   30
    //    Distance:  9  40  200
    // -> listOf(Range(7, 9), Range(15, 40), Range(30, 200))
    fun parse1(input: List<String>): List<Race> {
        val (times, distances) = input
            .take(2)
            .map { line ->
                line.substringAfter(":")
                .trim()
                .split(Regex("""\s+"""))
                .map {
                    numStr -> numStr.toLong()
                }
            }

        return times.zip(distances).map { Race.of(it) }
    }

    //    Time:      7  15   30
    //    Distance:  9  40  200
    // -> listOf(Range(71530, 940200))
    fun parse2(input: List<String>): List<Race> {
        val (time, distance) = input
            .take(2)
            .map { line ->
                line.substringAfter(":")
                    .trim()
                    .replace(" ", "")
                    .toLong()
            }
        return listOf(Race(time, distance))
    }

    // key mechanic: every ms you hold the button the speed will increase by 1ms/mm, but you lose that 1 ms to race
    fun waysToWin(race: Race): Int = (1..race.time).count { (race.time - it) * it > race.distance }

    fun calculateTotalWins(races: List<Race>): Int {
        val winsPerRace = buildList<Int> {
            races.forEach { race ->
                add(waysToWin(race))
            }
        }.toList()

        // multiply all the Int in winsPerRace
        return winsPerRace.fold(1) { acc, value -> acc * value }
    }

    fun part1(input: List<String>): Int {
        return calculateTotalWins(parse1(input))
    }

    fun part2(input: List<String>): Int {
        return calculateTotalWins(parse2(input))
    }

    verify("Test part 1", part1(readInput("test/Day06.txt")), 288)

//    verify("Winston part 1", part1(readInput("ww/Day06.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day06.txt")), 500346)

    verify("Test part 2", part2(readInput("test/Day06.txt")) , 71503)

//    verify("Winston part 2", part2(readInput("ww/Day06.txt")), 999)

    verify("Max part 2", part2(readInput("mb/Day06.txt")), 42515755)
}
