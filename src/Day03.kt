import java.lang.RuntimeException
class Schematic(val cells: List<String>)
data class Rect(val left: Int, val top: Int, val right:Int, val bottom:Int)

val digits = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

// Returns corners of the rectangle surrounding part number at (x,y)
fun boundingBox(schematic: Schematic, x: Int, y:Int): Rect {
    val row = schematic.cells[y]
    val lastX = row.indexOfAny(digits, x)
    log("lastX = $lastX")
    return Rect(x-1,y-1,lastX+1,y+1)
}

// Returns part number at 'rect' if it is a part, otherwise 0
fun partNumberAtBox(schematic: List<List<Char>>, rect: Rect) { throw Error("not impleted yet")}
fun main() {
    fun logSchematic(schematic: Schematic) {
        schematic.cells.forEach(::println)
    }

    fun log(message: Any?) {
        println(message)
    }

    fun part1(input: List<String>): Int {
//        val schematic: List<List<Char>> = input.map { it.toList() }
        val schematic = Schematic(input)
        logSchematic(schematic)
        val r = boundingBox(schematic, 6, 2)
        log("r=$r")
        return -1
    }

    fun part2(input: List<String>): Int {
        return -2
    }

    verify("Test part 1", part1(readInput("test/Day03.txt")), 999)

    verify("Winston part 1", part1(readInput("ww/Day03.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day03.txt")), 999)

    verify("Test part 2", part2(readInput("test/Day03.txt")) , 999)

    verify("Winston part 2", part2(readInput("ww/Day03.txt")), 999)

    verify("Max part 2", part2(readInput("mb/Day03.txt")), 999)
}
