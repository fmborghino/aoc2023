private fun log(message: Any?) {
    println(message)
}

private data class Mapper(val ranges: MutableList<Range>, var name: String)
private fun Mapper.map(n: Long): Long {
    val result = ranges.firstNotNullOfOrNull { range ->
        range.translate(n)
    } ?: n
//    log("> $n -> $result")
    return result
}

private data class Range(val src: Long, val dst: Long, val size: Long)
private fun Range.translate(n: Long): Long? {
    return if (n >= src && n <= src + size) (n - src) + dst
    else null
}

fun main() {
    fun findMinLocation(seeds: List<Long>, mappers: MutableList<Mapper>): Long {
        val locations = mutableListOf<Long>()
        seeds.forEach { seed ->
            var r = seed
//            log("seed $seed")
            mappers.forEach { mapper ->
//                log("mapper ${mapper.name}")
                r = mapper.map(r)
            }
            locations.add(r)
        }

        return locations.minOrNull() ?: -1L
    }

    fun findMinLocationFromRanges(seedRanges: List<LongRange>, mappers: MutableList<Mapper>): Long {
        var minLocation: Long = Long.MAX_VALUE
        seedRanges.forEach { range ->
            range.forEach { seed ->
                var r = seed
//            log("seed $seed")
                mappers.forEach { mapper ->
//                log("mapper ${mapper.name}")
                    r = mapper.map(r)
                }
                if (r < minLocation) minLocation = r
            }
        }
        return minLocation
    }

    fun parseInput(input: List<String>): Pair<List<Long>, MutableList<Mapper>> {
        var seeds: List<Long> = emptyList()
        var stepNum = -1
        val mappers = MutableList(7) { Mapper(mutableListOf<Range>(), "n/a") }

        input.forEach { line ->
            when {
                line.startsWith("seeds:") -> {
                    seeds = input.first().substringAfter(": ").split(" ").map { it.toLong() }
                }
                line.contains(" map:") -> {
                    stepNum++
                    mappers[stepNum].name = line.substringBefore(" ")
                }
                line.contains(Regex("^\\d")) -> {
                    val v = line.split(" ").map { it.toLong() }
                    mappers[stepNum].ranges.add(Range(v[1], v[0], v[2]))
                }
            }
        }
        return Pair(seeds, mappers)
    }

    // hm yeah this leads to an OOME with game data
    // change List of range-y pairs into the expanded list... eek
    fun expandRanges(seeds: List<Long>): List<Long> {
        return buildList<Long> {
            seeds.chunked(2) { r -> addAll(r[0].rangeTo(r[0]+r[1])) }
        }.toList()
    }

    // make a list of Range, don't expand the values
    fun assembleRanges(seeds: List<Long>): List<LongRange> {
        return buildList<LongRange> {
            seeds.chunked(2) { r -> add(r[0].rangeTo(r[0]+r[1])) }
        }.toList()
    }

    fun part1(input: List<String>): Long {
        val (seeds: List<Long>, mappers) = parseInput(input)
        return findMinLocation(seeds, mappers)
    }

    fun part2(input: List<String>): Long {
        val (seeds: List<Long>, mappers) = parseInput(input)
        val seedRanges = assembleRanges(seeds)
        log("seedRanges $seedRanges")
        return findMinLocationFromRanges(seedRanges, mappers)
    }

    verify("Test part 1", part1(readInput("test/Day05.txt")), 35)

//    verify("Winston part 1", part1(readInput("ww/Day05.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day05.txt")), 227653707)

    verify("Test part 2", part2(readInput("test/Day05.txt")) , 46)

//    verify("Winston part 2", part2(readInput("ww/Day05.txt")), 999)

    // long run time, on my Mac around 10 min
    // probably should calculate and exclude duplicated sub-ranges
    verify("Max part 2", part2(readInput("mb/Day05.txt")), 78775051)
}
