import kotlin.collections.partition

class Solver(
    val input: List<String>
) {
    private var ranges: MutableMap<Long, Long> = mutableMapOf()

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
                ranges[first.toLong()] = second.toLong()
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
            if (value >= pair.key && value <= pair.value)
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
            for (i in range.key..range.value)
                setOfAllValidIds.add(i)
        }

        return setOfAllValidIds.size
    }

    fun mergeRanges(ranges: Map<Long, Long>): Map<Long, Long> {
        if (ranges.isEmpty())
            return emptyMap()

        val sortedMap = ranges.toSortedMap()
        val mapOfEntriesToRemove: MutableMap<Long, Boolean> = sortedMap.keys.associateWith { false }.toMutableMap()

        sortedMap.forEach { originalRange ->

            val originalLowerBound = originalRange.key

            if (!(mapOfEntriesToRemove[originalLowerBound] ?: false)) {
                val originalUpperBound = originalRange.value

                sortedMap
                    .filterKeys {
                        it >= originalLowerBound && !(mapOfEntriesToRemove[it] ?: false)
                    }
                    .forEach { range ->

                        val otherLowerBound = range.key
                        val otherUpperBound = range.value

                        if (otherLowerBound <= originalUpperBound) {
                            if (otherUpperBound <= originalUpperBound) {
                                // completely stash the other map
                                mapOfEntriesToRemove[range.key] = true
                            } else {
                                // set a new upper bound to the original map and stash the other map
                                sortedMap[originalLowerBound] = otherUpperBound
                                mapOfEntriesToRemove[originalLowerBound] = false
                                mapOfEntriesToRemove[otherLowerBound] = true
                            }
                        }
                    }
            }
        }

        // println(mapOfEntriesToRemove)

        val cleanMap = sortedMap.filter { range ->
            !mapOfEntriesToRemove[range.key]!!
        }

        return cleanMap
    }

    fun cleanRanges

    fun countDistinctValidIds(): Long {
        val mergedRanges = mergeRanges(ranges)
        var sum = 0L

        mergedRanges.forEach {
            sum += (it.value - it.key)
        }

        return sum
    }

    fun printMergedRanges() {
        println("--Original ranges--")
        println(ranges)
        println("--New ranges--")
        println(mergeRanges(ranges))
    }

}

fun main() {
    fun part1(input: List<String>): Int {

        val solver = Solver(input)
        solver.readInput()
        // solver.printParsedInput()
        return solver.doTheBruteForce()
    }

    fun part2(input: List<String>): Long {

        val solver = Solver(input)
        solver.readInput()
        // solver.printParsedInput()
        solver.printMergedRanges()
        //return solver.getAllValidIds()
        return solver.countDistinctValidIds()
    }

    part1(readInput("test05")).println()
    //part2(readInput("test05")).println()

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("test05")) == 3)

    // Read the input from the `src/Day01.txt` file.
    println("Part 1:")
    val input = readInput("Day05")
    part1(input).println()

    // check(part2(readInput("test05")) == 14)
    println("Part 2:")
    part2(input).println()
}