class TachyonBeamSolver2(
    val input: List<String>
) {

    private val maxLineIdx = input.lastIndex

    fun startPropagation(): Int {
        val positionOfS = input.first().indexOfFirst { it == 'S' }

        val lineIndexList = List(maxLineIdx + 1) { mutableSetOf<Int>() }
        lineIndexList[1].add(positionOfS)

        var splits = 0
        for (i in 2..maxLineIdx) {
            lineIndexList[i - 1].forEach { index ->
                if (input[i][index] == '^') {
                    splits++
                    lineIndexList[i].add(index - 1)
                    lineIndexList[i].add(index + 1)
                    lineIndexList[i].remove(index)
                } else {
                    lineIndexList[i].add(index)
                }
            }
        }
        return splits
    }

}

fun main() {
    fun part1(input: List<String>): Int {
        val solver = TachyonBeamSolver2(input)
        return solver.startPropagation()
    }

    fun part2(input: List<String>): Int {
        val solver = TachyonBeamSolver2(input)
        return 0 // solver.startPropagationPartTwo()
    }

    println("Part 1:")
    part1(readInput("test07")).println()

    // Test if implementation meets criteria from the description:
    check(part1(readInput("test07")) == 21)

    // Read the input from the `src/Day01.txt` file.

    val input = readInput("Day07")
    part1(input).println()

    println("Part 2:")

    part2(readInput("test07")).println()
    check(part2(readInput("test07")) == 40)
    part2(input).println()
}