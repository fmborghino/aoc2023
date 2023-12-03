/*
 * Hindsight...
 * - use \d+ regex to scan lines for all numbers, that would give the start and end for each with no fuss
 * - too many off-by-one opportunities here, consider other approaches
 *   - including just padding the whole schematic with one extra . all the way around
 * - heh, sometimes the approach emerges as terrible BUT it's easier to push to the end than redo everything!
 */

import kotlin.system.exitProcess

class Schematic(val cells: List<String>) {
    fun width() = cells[0].length

    fun length() = cells.size

    fun borderHasSymbol(border: BorderedNumber): Boolean =
        border.border.any {
//            log("cells[${it.first}][${it.second}]=${cells[it.first][it.second]}")
            cells[it.first][it.second].toString() in symbols
        }

    fun borderStarPosition(border: BorderedNumber): Pair<Int, Int>? {
        return border.border.firstOrNull {
//            log("cells[${it.first}][${it.second}]=${cells[it.first][it.second]}")
            cells[it.first][it.second].toString() in onlyStar
        }
    }

    // all the start locations of numbers
    fun listNumberLocations(): List<Pair<Int, Int>> {
        val numberLocations = mutableListOf<Pair<Int, Int>>()
        for (row in 0 until length()) {
            var col = 0
            while (col < width()) { // yikes
                if (cells[row][col].isDigit()) {
                    numberLocations.add(Pair(row, col))
                    // skip past the number
                    col = cells[row].indexOfAny(nonDigits, col)
                    if (col == -1) col = width()
                }
                else col += 1
            }
        }
        return numberLocations
    }
}

data class Rect(val top: Int, val left: Int, val bottom:Int, val right:Int, val number: Int)
data class BorderedNumber(val number: Int, val border: List<Pair<Int, Int>>)

// kinda cheated to find all the non numeric and non dot chars in the input...
// cat data/mb/Day03.txt | grep -o . | sort -u | egrep -v '\d|\.' | tr -d '\n' && echo
// #$%&*+-/=@
val symbols = "#$%&*+-/=@".map { it.toString() }
val nonDigits = symbols + "."
val onlyStar = listOf("*")

// find corners of the rectangle surrounding part number which starts at (row, col)
fun Rect(schematic: Schematic, row: Int, col:Int): Rect {
    val rowStr = schematic.cells[row]
    var lastCol = rowStr.indexOfAny(nonDigits, col)
    if (lastCol == -1) lastCol = schematic.width()
    val number = rowStr.substring(col, lastCol).toInt()
    val rect = Rect(row-1,col-1,row+1, lastCol, number)
//    log("Rect for char[$row][$col]=${schematic.cells[row][col]}: $rect")
    return rect
}

// find the number which starts at (row, col) and the list of bordering coordinates
// omit coords that are outside the schematic boundaries
fun BorderedNumber(schematic: Schematic, row: Int, col: Int): BorderedNumber {
    val rowStr = schematic.cells[row]
    var lastCol = rowStr.indexOfAny(nonDigits, col)
    if (lastCol == -1) lastCol = schematic.width()
    val number = rowStr.substring(col, lastCol).toInt()
    val border: MutableList<Pair<Int, Int>> = mutableListOf()
    // top and bottom borders
    (col-1..lastCol).forEach { c ->
        // top border
        if (row-1 >= 0 && c >= 0 && c < schematic.width() ) {
            border.add(Pair(row-1, c))
        }
        // bottom border
        if (row+1 < schematic.length() && c >= 0 && c < schematic.width() ) {
            border.add(Pair(row+1, c))
        }
    }
    // add left and right of number row
    if (col-1 >= 0) border.add(Pair(row, col-1))
    if (lastCol < schematic.width()) border.add(Pair(row, lastCol))
    val borderedNumber = BorderedNumber(number, border)
//    log("BorderedNumber for char[$row][$col]=${schematic.cells[row][col]}: $borderedNumber")
    return borderedNumber
}

fun main() {
    fun logSchematic(schematic: Schematic) {
        schematic.cells.forEach(::println)
    }

    fun log(message: Any?) {
        println(message)
    }

    fun part1(input: List<String>): Int {
        val schematic = Schematic(input)
//        logSchematic(schematic)
//        log(Rect(schematic, 6, 2))
//        log("borderHasSymbol (6,2)=${schematic.borderHasSymbol(BorderedNumber(schematic, 6, 2))}")
//        log("borderHasSymbol (0,5)=${schematic.borderHasSymbol(BorderedNumber(schematic, 0, 5))}")
//        log("numberLocations=${schematic.listNumberLocations()}")

        val hits: List<Int> = schematic.listNumberLocations().map { numberLocation ->
            val borderedNumber = BorderedNumber(schematic, numberLocation.first, numberLocation.second)
            if (schematic.borderHasSymbol(borderedNumber)) borderedNumber.number else 0
        }

//        log("numbers touching symbols=${hits}")
//        exitProcess(0xdead)
        return hits.sum()
    }

    // re-use the number finder + border scanner, but look for * only in the border
    // add the number to a Map<List<Int>> where key is the position of the *
    // for anything in the map with a list of 2 numbers... done
    // this will only work if a number is not touching more than one star... let's see
    fun part2(input: List<String>): Int {
        val schematic = Schematic(input)
        // map of star locations -> list of int touching that star
        val mapOfHits = buildMap<Pair<Int, Int>, MutableList<Int>> {
            schematic.listNumberLocations().map { numberLocation ->
                val borderedNumber = BorderedNumber(schematic, numberLocation.first, numberLocation.second)
                val starPosition = schematic.borderStarPosition(borderedNumber)
                if ( starPosition != null) {
                    val entry = get(starPosition)
                    if (entry.isNullOrEmpty()) {
                        put(starPosition, mutableListOf(borderedNumber.number))
                    } else {
                        entry.add(borderedNumber.number)
                    }
                }
            }
        }
//        log("mapOfHits=${mapOfHits}")
        return mapOfHits
            .filter { it.value.size == 2 }
            .map { it.value[0] * it.value[1] } // get the "gear ratio"
            .sum()
    }

    verify("Test part 1", part1(readInput("test/Day03.txt")), 4361)

//    verify("Winston part 1", part1(readInput("ww/Day03.txt")), 999)

    verify("Max part 1", part1(readInput("mb/Day03.txt")), 527364)

    verify("Test part 2", part2(readInput("test/Day03.txt")) , 467835)

//    exitProcess(0xdead)

//    verify("Winston part 2", part2(readInput("ww/Day03.txt")), 999)

    verify("Max part 2", part2(readInput("mb/Day03.txt")), 79026871)
}
