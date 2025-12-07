class TachyonBeamSolver(
    val input: List<String>
) {
    var splits = 0
        private set

    private val maxLineIdx = input.lastIndex
    private val rowLastIndex = input.first().lastIndex

    private val mutableTree = input.toMutableList()

    fun startRecursion() {
        val positionOfS = input.first().indexOfFirst { it == 'S' }
        println("Starting position $positionOfS")


        splitRecursively(
            lineIdx = 1,
            position = positionOfS,
        )
    }

    fun splitRecursively(lineIdx: Int, position: Int) {
        if (lineIdx == maxLineIdx || position < 0 || position > rowLastIndex)
            return

        if (mutableTree[lineIdx][position] == '^') {
            splits++
            val lineStringList = mutableTree[lineIdx].toMutableList()
            lineStringList[position] = '.'
            mutableTree[lineIdx] = lineStringList.joinToString("")
            println("updated row: ${mutableTree[lineIdx]}")
            println("Splitting position: $lineIdx:$position")
            splitRecursively(lineIdx + 1, position - 1)
            splitRecursively(lineIdx + 1, position + 1)
        } else {
            val lineStringList = mutableTree[lineIdx].toMutableList()
            lineStringList[position] = '|'
            mutableTree[lineIdx] = lineStringList.joinToString("")
            splitRecursively(lineIdx + 1, position)
        }
    }

    fun printCompletedTree() {
        mutableTree.forEach {
            println(it)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val solver = TachyonBeamSolver(input)
        solver.startRecursion()
        solver.printCompletedTree()
        return solver.splits
    }

    fun part2(input: List<String>): Int {
        return 0
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
    check(part2(readInput("test07")) == 214)
    part2(input).println()
}