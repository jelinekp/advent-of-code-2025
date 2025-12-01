
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

fun rotate(position: Int, dialInstruction: DialInstruction): Int {
    var newPosition = 0

    if (dialInstruction.direction == Direction.LEFT) {
        newPosition = position - dialInstruction.amount
    } else if (dialInstruction.direction == Direction.RIGHT) {
        newPosition = position + dialInstruction.amount
    }

    newPosition %= 100
    if (newPosition < 0)
        newPosition += 100

    return newPosition
}

fun main() {
    fun part1(input: List<String>): Int {

        var intermediatePosition = 50
        var numberOfTimesAtZero = 0

        input.forEach { row ->
            val row = parseRow(row)

            row?.let {
                intermediatePosition = rotate(intermediatePosition, row)
                if (intermediatePosition == 0)
                    numberOfTimesAtZero++
            }
        }

        return numberOfTimesAtZero
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println("Task one output:")
    part1(input).println()

    println("Task two output:")
    part2(input).println()
}
