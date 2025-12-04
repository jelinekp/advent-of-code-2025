// we need to find the highest value and its index,
// then try to find another highest value after the highest value
// or before the highest value if the highest value is at the last index

fun findHighestJoltage(bank: String): Int {
    val lastIndex = bank.length - 1
    val joltageArray = bank.toCharArray()

    val maxIdx = joltageArray.indices.maxBy { joltageArray[it] }

    // println("Last Index: $lastIndex, Max index position: ${maxIdx}, Max idx value: ${joltageArray[maxIdx]}")

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
    /*
        println(newJoltageArray)

        println("Result $result")*/

    return result
}

fun buildIntFromArrayAndIndexes(joltageArray: CharArray, indexes: List<Int>): Long {
    var resultString = ""

    indexes.forEach { index ->
        resultString = "$resultString${joltageArray[index]}"
    }

    println("Intermediate result: $resultString")

    return resultString.toLong()
}

// the idea is to get the 12 highest numbers index by index and if there are no other number, then propagate back
fun findHighestJoltagePart2FirstTry(bank: String): Long {
    val lastIndex = bank.length - 1
    val originalArray = bank.toCharArray()
    val joltageArray: MutableList<Char> = bank.toList().toMutableList()

    val indexes = mutableSetOf<Int>()

    for (i in 0..11) {
        val maxIdx = joltageArray.indices.maxBy { joltageArray[it] }

        indexes.add(maxIdx)

        joltageArray[maxIdx] = (0).toChar()

        if (maxIdx == lastIndex)
            break

        if (i == 11)
            return buildIntFromArrayAndIndexes(originalArray, indexes.sorted())
    }

    for (i in lastIndex downTo 0) {
        if (joltageArray[i] == 0.toChar())
            continue
        indexes.add(i)
        if (indexes.size == 12)
            break
    }

    return buildIntFromArrayAndIndexes(originalArray, indexes.sorted())
}

fun findHighestJoltagePart2(bank: String): Long {
    println("Bank: $bank")
    val length = bank.length

    val groupedDigits = bank.withIndex()
        .groupBy({ it.value.digitToInt() }, { it.index })

    val resultDigits = MutableList(length) { 0 }

    var added = 0

    var lastUsedIndex = -1

    for (digit in 9 downTo 1) {
        val indices = groupedDigits[digit] ?: emptyList()
        println("Digits list for $digit: $indices")

        // first we put the max values wherever, then trying to put the lower value behind it, if not found
        // if there is not enough space, try prepending the lower ciphers one by one


        for (i in 0..<indices.size) {
            if (indices[i] > lastUsedIndex) {
                resultDigits.add(indices[i], digit)
                lastUsedIndex = indices[i]
                ++added
            }
            // the problem is with this statement - when true, it goes from the front, but we want to try to put the numbers after the last
            else if (length - lastUsedIndex < 12) {



                resultDigits.add(indices[i], digit)
                lastUsedIndex = indices[i]
                ++added
            }

            if (added == 12) break
        }

        if (added == 12) break
    }

    var resultString = ""

    resultDigits
        .filter { it != 0 }
        .forEach { resultString += "$it" }

    println("Result string: $resultString")

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
            sum += findHighestJoltagePart2(bank)
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    part1(readInput("test03")).println()
    part2(readInput("test03")).println()

    // Read the input from the `src/Day01.txt` file.
    /*val input = readInput("Day03")
    part1(input).println()
    part2(input).println()*/
}
