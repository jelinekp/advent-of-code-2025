import kotlin.collections.partition

class Solver(
    val input: List<String>
) {
    private var ranges: MutableList<Pair<Long, Long>> = mutableListOf()

    private var values: MutableList<Long> = mutableListOf()

    private var height: Int = 0
    private var width: Int = 0

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

    fun printParsedInput() {
        println(ranges)
        println(values)
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

    fun getAllValidIds(): Int {
        val setOfAllValidIds = mutableSetOf<Long>()

        ranges.forEach { range ->
            for (i in range.first..range.second)
                setOfAllValidIds.add(i)
        }

        return setOfAllValidIds.size
    }

}

fun main() {
    fun part1(input: List<String>): Int {

        val solver = Solver(input)
        solver.readInput()
        // solver.printParsedInput()
        return solver.doTheBruteForce()
    }

    fun part2(input: List<String>): Int {

        val solver = Solver(input)
        solver.readInput()
        // solver.printParsedInput()
        return solver.getAllValidIds()
    }

    part1(readInput("test05")).println()
    //part2(readInput("test05")).println()

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("test05")) == 3)

    // Read the input from the `src/Day01.txt` file.
    println("Part 1:")
    val input = readInput("Day05")
    part1(input).println()

    check(part2(readInput("test05")) == 14)
    println("Part 2:")
    part2(input).println()
}