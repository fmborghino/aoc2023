fun part1(lines: List<String>):Int {
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

   return 0
}
fun main() {
    verify("Test part 1", part1(readInput("test/Day02.txt")), 8)
}
