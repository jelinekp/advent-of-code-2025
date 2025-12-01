import kotlin.math.abs

enum class Direction {
    LEFT, RIGHT
}

data class DialInstruction(val direction: Direction, val amount: Int)

fun parseRow(line: String): DialInstruction? {
    val direction =
        if (line[0] == 'L')
            Direction.LEFT
        else if (line[0] == 'R')
            Direction.RIGHT
        else
            return null
    val moves = line.drop(1).toInt()

    return DialInstruction(direction, moves)
}

fun rotate(position: Int, dialInstruction: DialInstruction): Pair<Int, Int> {
    var newPosition = 0

    if (dialInstruction.direction == Direction.LEFT) {
        newPosition = position - dialInstruction.amount
    } else if (dialInstruction.direction == Direction.RIGHT) {
        newPosition = position + dialInstruction.amount
    }

    var clicks = abs(newPosition / 100)

    newPosition %= 100

    if (dialInstruction.direction == Direction.LEFT) {
        if (newPosition < 0) {
            newPosition += 100
            if (position != 0)
                clicks++
        }
        else if (newPosition == 0)
            clicks++
    }

    return Pair(newPosition, clicks)
}

fun main() {
    fun part1(input: List<String>): Int {

        var intermediatePosition = 50
        var numberOfTimesAtZero = 0

        input.forEach { row ->
            val row = parseRow(row)

            row?.let {

                //print("Start $intermediatePosition")

                val result = rotate(intermediatePosition, row)

                intermediatePosition = result.first
                val clicks = result.second

                numberOfTimesAtZero += clicks
            }
        }

        return numberOfTimesAtZero
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println("Task output:")
    part1(input).println()
}
