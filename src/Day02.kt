data class Pick(val red: Int, val green: Int, val blue: Int) // this could just be a map
data class Game(val id: Int, val picks: List<Pick>)

fun log(message: Any?) {
    println(message)
}

/*
    (1) Parse file of Games that looks like:
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    into structure like:
    [
        { game: 1, clues: [
            { blue: 3, red: 4, green: 2 },
            ...]
        }.
        { game: 2, clues: [...]}
    ]


    (2) Given Bag: { red: 12, green: 13, blue: 14 }
        filter games where Bag has red > game.red
        filter games where Bag has green > game.green
        filter games where Bag has blue > game.blue

    (3) sum the game IDs.
*/
fun part1(lines: List<String>, given: Pick):Int {
    val games: List<Game> = lines.map { Game(it) }
    val filteredGames = games.filter { game ->
        game.picks.all { pick -> pick.red <= given.red } &&
                game.picks.all { pick -> pick.green <= given.green } &&
                game.picks.all { pick -> pick.blue <= given.blue }
    }
    return filteredGames.sumOf { it.id }
}

fun Game(line: String): Game {
    // parse a line like this to get a Game
    // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    val parts = line.split(":")
    val gameId = parts[0].trim().substring(5).toInt()
    val picks = parts[1].split(";").map { it.trim() }.map { Pick(it) }
    return Game(gameId, picks)
}

fun Pick(pick: String): Pick {
    // parse line like this to get a Pick
    // 1 red, 2 green, 6 blue
    // missing color means 0
    val colors: List<String> = pick.split(",").map { it.trim() }
    var red = 0 // yuk, vars
    var green = 0
    var blue = 0
    colors.forEach {
        val (count, color) = it.split(" ")
        when (color) {
            "red" -> red = count.toInt()
            "green" -> green = count.toInt()
            "blue" -> blue = count.toInt()
        }
    }

    return Pick(red, green, blue)
}

/*
 * for each Game(val id: Int, val picks: List<Pick>)
 * find the maximum red, green, blue from the list of Pick
 * then do the math for the result
 */
fun part2(lines: List<String>): Int {
    val games: List<Game> = lines.map { Game(it) }
    val maxPicks = games.map { game ->
        val maxRed = game.picks.maxByOrNull { it.red }
        val maxGreen = game.picks.maxByOrNull { it.green }
        val maxBlue = game.picks.maxByOrNull { it.blue }
        Pick(maxRed?.red ?: 1, maxGreen?.green ?: 1, maxBlue?.blue ?: 1)
    }
    val powers = maxPicks.map { it.red * it.green * it.blue }
    return powers.sum()
}

fun main() {
    val given = Pick(12, 13, 14)
    verify("Test part 1", part1(readInput("test/Day02.txt"), given), 8)

//    verify("Winston part 1", part1(readInput("ww/Day02.txt"), given), 999)

    verify("Max part 1", part1(readInput("mb/Day02.txt"), given), 1853)

    verify("Test part 2", part2(readInput("test/Day02.txt")) , 2286)

//    verify("Winston part 2", part2(readInput("ww/Day02.txt")), 999)

    verify("Max part 2", part2(readInput("mb/Day02.txt")), 72706)
}
