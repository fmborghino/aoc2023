fun main() {
    fun log(message: Any?) {
        println(message)
    }

    fun part1(input: List<String>): Int {
        return -1
    }

    fun part2(input: List<String>): Int {
        return -2
    }

    verify("Test part 1", part1(readInput("test/DayNN.txt")), 999)


    verify("Winston part 1", part1(readInput("ww/DayNN.txt")), 999)

    verify("Max part 1", part1(readInput("mb/DayNN.txt")), 999)

    verify("Test part 2", part2(readInput("test/DayNN.txt")) , 999)

    verify("Winston part 2", part2(readInput("ww/DayNN.txt")), 999)

    verify("Max part 2", part2(readInput("mb/DayNN.txt")), 999)
}
