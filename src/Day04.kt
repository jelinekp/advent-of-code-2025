import java.lang.Exception

class RollsWallSolver(
    val input: List<String>
) {
    private var rollsWall: MutableList<MutableList<Int>> = mutableListOf()
    private var height: Int = 0
    private var width: Int = 0

    fun readInput() {
        rollsWall = mutableListOf()

        input.forEach { line ->

            val lineValues = MutableList(line.length) { 0 }

            line.toCharArray().forEachIndexed { index, char ->
                if (char == '@')
                    lineValues[index] = 1
            }

            rollsWall.add(lineValues)
        }

        height = rollsWall.size
        width = rollsWall.first().size
    }

    fun printRollsWall() =
        rollsWall.forEach { row ->
            println(row)
        }

    fun getNumberOfAdjacentRolls(lineIndex: Int, columnIndex: Int): Int {
        var rolls = -1 // we don't want to count ourselves, so we start from -1

        for (i in lineIndex - 1 .. lineIndex + 1) {
            for (j in columnIndex - 1 .. columnIndex + 1) {
                try {
                    if (rollsWall[i][j] == 1)
                        ++rolls
                } catch (_: Exception) { // index out of bound exception
                    continue
                }
            }
        }

        return rolls
    }

    fun findNumberOfLooseRolls(): Int {
        var sumOfLooseRolls = 0

        for (i in 0..<height)
            for (j in 0..<width)
                if (rollsWall[i][j] == 1)
                    if (getNumberOfAdjacentRolls(i, j) < 4 )
                        sumOfLooseRolls +=  1

        return sumOfLooseRolls
    }

    fun findNumberOfLooseRollsForPartTwo(): Int {
        var sumOfLooseRolls = 0

        for (i in 0..<height)
            for (j in 0..<width)
                if (rollsWall[i][j] == 1)
                    if (getNumberOfAdjacentRolls(i, j) < 4 ) {
                        sumOfLooseRolls +=  1
                        rollsWall[i][j] = 0
                    }

        return sumOfLooseRolls
    }

    fun findNumberOfLooseRollsRepeatedly(): Int {
        var allRoundsSum = 0

        do {
            val roundSum = findNumberOfLooseRollsForPartTwo()
            allRoundsSum += roundSum
        } while (roundSum > 0)

        return allRoundsSum
    }
}

fun main() {
    fun part1(input: List<String>): Int {

        val solver = RollsWallSolver(input)
        solver.readInput()
        // solver.printRollsWall()
        return solver.findNumberOfLooseRolls()
    }

    fun part2(input: List<String>): Int {

        val solver = RollsWallSolver(input)
        solver.readInput()
        // solver.printRollsWall()
        return solver.findNumberOfLooseRollsRepeatedly()
    }

    part1(readInput("test04")).println()
    part2(readInput("test04")).println()

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("test04")) == 13)
    check(part2(readInput("test04")) == 43)
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}