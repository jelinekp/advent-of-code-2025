
fun findHighestJoltage(bank: String): Int {
    val lastIndex = bank.length - 1
    val joltageArray = bank.toCharArray()

    val maxIdx = joltageArray.indices.maxBy { joltageArray[it] }

    var newJoltageArray: List<Char> = joltageArray.toList()
    var result = 0

    if (maxIdx == lastIndex) {
        newJoltageArray = newJoltageArray.dropLast(1)
        // the first max value will be the second
        val secondHighestValue = newJoltageArray.max()
        result = String(charArrayOf(secondHighestValue, joltageArray[maxIdx])).toInt()
    } else {
        newJoltageArray = newJoltageArray.drop(maxIdx + 1)
        // the first max value stays first
        val secondHighestValue = newJoltageArray.max()
        result = String(charArrayOf(joltageArray[maxIdx], secondHighestValue)).toInt()
    }

    return result
}

// get the first highest number with i numbers remaining, simple, right?
fun findHighestJoltageTwelve(bank: String): Long {
    val n = bank.length
    var lastFoundIndex = 0
    var resultString = ""

    for (i in 12 downTo 1) {
        val boundedString = bank.substring(lastFoundIndex, n - i + 1)

        val joltageArray = boundedString.toCharArray()

        // find the first max number in the bounded string
        val maxIdx = joltageArray.indices.maxBy { joltageArray[it] }

        lastFoundIndex += (maxIdx + 1)
        resultString += joltageArray[maxIdx]
    }

    return resultString.toLong()
}

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        input.forEach { bank ->
            sum += findHighestJoltage(bank)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L

        input.forEach { bank ->
            sum += findHighestJoltageTwelve(bank)
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //part1(readInput("test03")).println()
    part2(readInput("test03")).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
