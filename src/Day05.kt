import kotlin.math.max

class Solver(
    val input: List<String>
) {
    private var ranges: MutableList<Pair<Long, Long>> = mutableListOf()

    private var values: MutableList<Long> = mutableListOf()

    fun readInput() {
        val inputLength = input.size
        var isFirstPartLoading = true

        for (i in 0..<inputLength) {
            if (isFirstPartLoading) {
                if (input[i].isBlank()) {
                    isFirstPartLoading = false
                    continue
                }

                val parts = input[i].split('-')
                val first = parts.first()
                val second = parts[1]
                ranges.add(first.toLong() to second.toLong())
            } else {
                values.add(input[i].toLong())
            }
        }
    }

    private fun isValueInAnyOfTheRanges(value: Long): Boolean {
        ranges.forEach { pair ->
            if (value >= pair.first && value <= pair.second)
                return true
        }
        return false
    }

    fun doTheBruteForce(): Int {
        var validIds = 0

        values.forEach {
            if (isValueInAnyOfTheRanges(it))
                validIds++
        }
        return validIds
    }

    private fun cleanRanges(): List<Pair<Long, Long>> {
        ranges.sortWith(Comparator { itemA, itemB ->
            itemA.first.compareTo(itemB.first)
        })

        val mergedRanges = mutableListOf<Pair<Long, Long>>()
        mergedRanges.add(ranges.first())

        ranges.forEach { currentRange ->
            val last = mergedRanges[mergedRanges.size - 1]

            if (currentRange.first <= last.second) {
                mergedRanges[mergedRanges.size - 1] = last.copy(second = max(last.second, currentRange.second))
            } else {
                mergedRanges.add(currentRange)
            }
        }

        return mergedRanges
    }

    fun countDistinctValidIds(): Long {
        val mergedRanges = cleanRanges()
        var sum = 0L

        mergedRanges.forEach {
            sum += (it.second - it.first + 1)
        }

        return sum
    }

    fun printMergedRanges() {
        println("--Original ranges--")
        println(ranges)
        println("--New ranges--")
        println(cleanRanges())
    }

}

fun main() {
    fun part1(input: List<String>): Int {

        val solver = Solver(input)
        solver.readInput()

        return solver.doTheBruteForce()
    }

    fun part2(input: List<String>): Long {

        val solver = Solver(input)
        solver.readInput()

        solver.printMergedRanges()

        return solver.countDistinctValidIds()
    }

    println("Part 1:")
    part1(readInput("test05")).println()

    // Test if implementation meets criteria from the description:
    check(part1(readInput("test05")) == 3)

    // Read the input from the `src/Day01.txt` file.

    val input = readInput("Day05")
    part1(input).println()

    println("Part 2:")

    check(part2(readInput("test05")) == 14L)
    part2(readInput("test05")).println()
    part2(input).println()
}