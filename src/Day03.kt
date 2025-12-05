fun String.findMaxSubsequence(length: Int): Long {
    val sb = StringBuilder()
    var searchIndex = 0
    val n = this.length

    // We need to fill k slots
    for (itemsNeeded in length downTo 1) {
        // We can only search up to the point where we leave enough items for the subsequent iterations
        // e.g. if we need 2 more items, we can't pick the very last item now
        val searchLimit = n - itemsNeeded

        var maxDigit = '0' - 1
        var maxIdx = -1

        // Scan the valid window for the largest digit
        for (i in searchIndex..searchLimit) {
            val current = this[i]
            if (current > maxDigit) {
                maxDigit = current
                maxIdx = i
                // If we found 9, we can't go higher
                if (maxDigit == '9') break
            }
        }

        sb.append(maxDigit)
        searchIndex = maxIdx + 1
    }

    return sb.toString().toLong()
}

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { it.findMaxSubsequence(2) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { it.findMaxSubsequence(12) }
    }

    val input = readInput("Day03")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}