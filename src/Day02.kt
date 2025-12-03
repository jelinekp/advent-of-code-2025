data class Range(
    val first: Long,
    val second: Long,
)

fun loadRanges(input: String): List<Range> {

    val stringRangeList = input.split(',')

    val ranges = stringRangeList.map { stringRange ->
        val intList = stringRange.split('-')
        Range(intList.first().toLong(), intList[1].toLong())
    }
    return ranges
}

fun String.splitAtIndex(index: Int) = take(index) to substring(index)

fun getInvalidIdsFromRange(range: Range): List<Long> {
    val listOfInvalidIds = mutableListOf<Long>()

    for (i in range.first..range.second) {
        val digitsString = i.toString()
        val numberOfDigits = digitsString.length

        if (numberOfDigits % 2 == 1)
            continue

        val pairOfHalves = digitsString.splitAtIndex(numberOfDigits / 2)

        if (pairOfHalves.first == pairOfHalves.second)
            listOfInvalidIds.add(i)
    }

    return listOfInvalidIds
}

fun getInvalidIdsFromRangePart2(range: Range): Set<Long> {
    val setOfInvalidIds = mutableSetOf<Long>()

    for (i in range.first..range.second) {
        val digitsString = i.toString()
        val numberOfDigits = digitsString.length

        for (j in 1..numberOfDigits / 2) {
            val firstDigitsPart = digitsString.take(j)
            for (k in j..numberOfDigits - j step j) {

                if (firstDigitsPart != digitsString.substring(k, k + j))
                    break

                if (k == numberOfDigits - j)
                    setOfInvalidIds.add(i)
            }
        }
    }

    return setOfInvalidIds
}

fun main() {
    fun part1(input: String): Long {
        val ranges = loadRanges(input)

        val invalidIdsList = mutableListOf<Long>()

        ranges.forEach { range ->
            invalidIdsList.addAll(getInvalidIdsFromRange(range))
        }

        return invalidIdsList.sum()
    }

    fun part2(input: String): Long {
        val ranges = loadRanges(input)

        val invalidIdsSet = mutableSetOf<Long>()

        ranges.forEach { range ->
            invalidIdsSet.addAll(getInvalidIdsFromRangePart2(range))
        }

        println()
        return invalidIdsSet.sum()
    }

    val testInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124"

    val input = "199617-254904,7682367-7856444,17408-29412,963327-1033194,938910234-938964425,3207382-3304990," +
            "41-84,61624-105999,1767652-1918117,492-749,85-138,140-312,2134671254-2134761843,2-23,3173-5046," +
            "16114461-16235585,3333262094-3333392446,779370-814446,26-40,322284296-322362264,6841-12127," +
            "290497-323377,33360-53373,823429-900127,17753097-17904108,841813413-841862326,518858-577234," +
            "654979-674741,773-1229,2981707238-2981748769,383534-468118,587535-654644,1531-2363"


    check(part1(testInput) == 1227775554L)
    part1(input).println()

    println()
    println(part2(testInput))
    check(part2(testInput) == 4174379265L)

    println("main test:")
    part2(input).println()
}
