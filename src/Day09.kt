fun main() {
    fun log(message: Any?) {
        println(message)
    }

    fun List<Int>.nextInt(): Int {4
        var seq = this
        val lasts = mutableListOf<Int>(seq.last())
        while (! seq.all { it == 0 }) {
            seq = seq.zipWithNext().map { (a, b) -> b - a }
            lasts.add(seq.last())
        }
        return lasts.sum()
    }

    fun part1(input: List<String>): Int {
        return input.map {
            it.split(" ").map(String::toInt).nextInt()
        }.sum()
    }

    fun List<Int>.precedingInt(): Int {
        var seq = this
        val firsts = mutableListOf<Int>(seq.first())
        while (!seq.all { it == 0 }) {
            seq = seq.zipWithNext().map { (a, b) -> b - a }
            firsts.add(seq.first())
        }
        return firsts.reversed().reduce { acc, v -> v - acc }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            it.split(" ").map(String::toInt).precedingInt()
        }.sum()
    }

    verify("Test part 1", part1(readInput("test/Day09.txt")), 114)

//    verify("Winston part 1", part1(readInput("ww/Day09.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day09.txt")), 1887980197)

    verify("Test part 2", part2(readInput("test/Day09.txt")) , 2)

//    verify("Winston part 2", part2(readInput("ww/Day09.txt")), 999)

    verify("Max part 2", part2(readInput("mb/Day09.txt")), 990)
}
