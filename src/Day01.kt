import kotlin.math.abs

enum class Direction(val multiplier: Int) {
    LEFT(-1), RIGHT(1);

    companion object {
        fun fromChar(char: Char): Direction? = when (char) {
            'L' -> LEFT
            'R' -> RIGHT
            else -> null
        }
    }
}

data class DialInstruction(val direction: Direction, val amount: Int)

fun parseRow(line: String): DialInstruction? {
    val direction = Direction.fromChar(line.firstOrNull() ?: return null) ?: return null
    val amount = line.substring(1).toIntOrNull() ?: return null
    return DialInstruction(direction, amount)
}

fun rotate(currentPosition: Int, instruction: DialInstruction): Pair<Int, Int> {

    val rawPosition = currentPosition + (instruction.amount * instruction.direction.multiplier)

    var clicks = abs(rawPosition / 100)
    val remainder = rawPosition % 100

    if (instruction.direction == Direction.LEFT) {
        // If we wrapped into negatives or landed exactly on 0
        if (remainder < 0 && currentPosition != 0) {
            clicks++
        } else if (remainder == 0) {
            clicks++
        }
    }

    val finalPosition = rawPosition.mod(100)

    return finalPosition to clicks
}

fun main() {
    // is now solving part2 - I have rewritten part1 instead of adding part2
    fun part2(input: List<String>): Int {

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
    part2(input).println()
}
