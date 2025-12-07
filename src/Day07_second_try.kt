class TachyonBeamSolver2(
    val input: List<String>
) {

    private val numberOfLines = input.size
    private val lineWidth = input.first().lastIndex

    private val numberHexFormat = HexFormat {
        upperCase = true
        number {
            removeLeadingZeros = true
        }
    }

    fun getNumberOfSplits(): Int {
        val positionOfS = input.first().indexOfFirst { it == 'S' }

        val lineIndexList = List(numberOfLines) { mutableSetOf<Int>() }
        lineIndexList[1].add(positionOfS)

        var splits = 0
        for (i in 2..<numberOfLines) {
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

    fun getNumberOfWays(): Long {
        val positionOfS = input.first().indexOfFirst { it == 'S' }

        val lineIndexList = MutableList(numberOfLines) { mutableMapOf<Int, Long>() }
        lineIndexList[1][positionOfS] = 1

        // we can skip lines without meaningful content with step(2)
        for (i in 3..numberOfLines step(2)) {

            val previousLine = lineIndexList[i - 2]

            previousLine.forEach { (index, value) ->

                if (input[i - 1][index] == '^') {
                    val newLeftVal = lineIndexList[i].getOrDefault(index - 1, 0)
                    val newRightVal = lineIndexList[i].getOrDefault(index + 1, 0)

                    lineIndexList[i][index - 1] = newLeftVal + value

                    lineIndexList[i][index + 1] = newRightVal + value

                    lineIndexList[i].remove(index)
                } else {
                    val currentVal = lineIndexList[i].getOrDefault(index, 0)
                    lineIndexList[i][index] = value + currentVal
                }
            }

            // to reconstruct the tree visually :)
            printRowFromLineIndexList(previousLine)
            println(input[i - 1])
        }

        printRowFromLineIndexList(lineIndexList.last())

        val tachyonWays = lineIndexList.last().values.sum()
        println("Tachyon ways: $tachyonWays")
        return tachyonWays
    }

    private fun printRowFromLineIndexList(lineIndexMap: Map<Int, Long>) {
        for (i in 0..lineWidth) {
            if (lineIndexMap.containsKey(i)) {
                print(lineIndexMap[i]?.toHexString(numberHexFormat))
            } else {
                print('.')
            }
        }
        println()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val solver = TachyonBeamSolver2(input)
        return solver.getNumberOfSplits()
    }

    fun part2(input: List<String>): Long {
        val solver = TachyonBeamSolver2(input)
        return solver.getNumberOfWays()
    }

    println("Part 1:")
    part1(readInput("test07")).println()
    check(part1(readInput("test07")) == 21)

    val input = readInput("Day07")
    part1(input).println()

    println("Part 2:")

    part2(readInput("test07")).println()
    check(part2(readInput("test07")) == 40L)
    part2(input).println()
}