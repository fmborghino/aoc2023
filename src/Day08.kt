fun main() {
    fun log(message: Any?) {
        println(message)
    }

    // returns the directions from line 0, and
    // map of key->(left, right) from for example: BXH = (PGR, NDC)
    fun parse(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val directions = input[0]
        val map = buildMap {
            input.drop(2).map { line ->
//                log(line)
                val (key, left, right) = "\\b\\w{3}\\b".toRegex().findAll(line).map { it.value }.toList()
                put(key, Pair(left, right))
            }
        }
        return Pair(directions, map)
    }

    fun followDirections(
        directions: String,
        map: Map<String, Pair<String, String>>,
        keyInit: String,
        isDone: (String) -> Boolean
    ): Long {
        var step = 0L
        var key = keyInit
//        log("start key $key")
        while (!isDone(key)) {
            val direction = directions[step.toInt() % directions.length]
            key = map[key]!!.let { if (direction == 'L') it.first else it.second }
//            log("-> $key")
            step++
        }
        return step
    }

    fun part1(input: List<String>): Long {
        val (directions, map) = parse(input)
        return followDirections(directions, map, "AAA") { s -> s != "ZZZ"}
    }

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long) = a / gcd(a, b) * b

    fun part2(input: List<String>): Long {
        val (directions, map) = parse(input)
        val keys: List<String> = map.keys.filter { it.endsWith("A") }
        log("there are ${keys.size} starting keys $keys")
        val results = keys.map { key ->
            followDirections(directions, map, key) { s -> s.endsWith("Z") }
        }
//        log(results)
        return results.reduce { acc, v -> lcm(acc, v) }
    }

    verify("Test part 1 a", part1(readInput("test/Day08_1a.txt")), 2)
    verify("Test part 1 b", part1(readInput("test/Day08_1b.txt")), 6)

//    verify("Winston part 1", part1(readInput("ww/Day08.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day08.txt")), 24253)

    verify("Test part 2", part2(readInput("test/Day08_2.txt")) , 6)

//    verify("Winston part 2", part2(readInput("ww/Day08.txt")), 999)

    verify("Max part 2", part2(readInput("mb/Day08.txt")), 999)
}
