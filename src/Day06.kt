import kotlin.text.trim

enum class Operation {
    ADDITION, MULTIPLICATION;

    override fun toString(): String {
        return when (this) {
            MULTIPLICATION -> "*"
            ADDITION -> "+"
        }
    }
}

fun String.toOperation(): Operation {
    return when (this.trim()) {
        "*" -> Operation.MULTIPLICATION
        "+" -> Operation.ADDITION
        else -> Operation.ADDITION
    }
}

class MathHomeworkSolver(
    val input: List<String>
) {
    private var numberColumns: MutableList<MutableList<String>> = mutableListOf()

    private var operations: List<Operation> = emptyList()

    fun readInput() {
        val inputLength = input.size

        // get a list of positions of first characters

        val lastLine = input[inputLength - 1]
        val listOfFirstPositions = mutableListOf<Int>()

        lastLine.toCharArray().forEachIndexed { index, ch ->
            if (!ch.isWhitespace())
                listOfFirstPositions.add(index)
        }

        println("listOfFirstPositions: $listOfFirstPositions")

        operations = lastLine.trim().split("\\s+".toRegex()).map { it.toOperation() }

        numberColumns = MutableList(operations.size) { mutableListOf() }

        for (i in 0..<inputLength - 1) {
            val line = input[i]

            // we need the split with leading whitespaces

            val lineStringList = mutableListOf<String>()

            listOfFirstPositions.forEachIndexed { i, firstIndex ->
                val lastIndex = if (i < listOfFirstPositions.lastIndex) listOfFirstPositions[i + 1] else line.length

                val sb = StringBuilder()

                for (j in firstIndex..<lastIndex) {
                    sb.append(line[j])
                }
                lineStringList.add(sb.toString())
            }

            lineStringList.forEachIndexed { index, item ->
                numberColumns[index].add(item)
            }
        }
    }

    private fun solveOne(operation: Operation, numbers: List<String>): Long {

        if (operation == Operation.ADDITION)
            return numbers.sumOf { string -> string.filterNot { it == '-' }.trim().toLong() }
        else if (operation == Operation.MULTIPLICATION) {
            var result = 1L
            numbers.forEach { string ->
                result *= string.filterNot { it == '-' }.trim().toLong()
            }
            return result
        }
        return 0L
    }

    private fun solveOneCephalopod(operation: Operation, numbers: List<String>): Long {
        val numbersCount = numbers.first().length
        val ciphersCount = numbers.size

        val rawNumbers = MutableList(numbersCount) { mutableListOf<Char>() }

        // then iterate from the first cipher to last one (if exists)

        for (i in 0..<numbersCount) {
            for (j in 0..<ciphersCount) {
                if (!numbers[j][i].isWhitespace())
                    rawNumbers[i].add(numbers[j][i])
            }
        }

        val cephalopodNumbers = rawNumbers.filterNot { it.isEmpty() }.map { number ->
            val sb = StringBuilder()
            number.forEach {
                sb.append(it)
            }
            sb.toString()
        }

        println("raw numbers $rawNumbers")
        println("cephalopod Numbers: $cephalopodNumbers")

        return solveOne(operation, cephalopodNumbers)
    }

    fun solveAll(): Long {
        var grandTotal = 0L

        numberColumns.forEachIndexed { index, column ->
            grandTotal += solveOne(operations[index], column)
        }

        return grandTotal
    }

    fun solveAllCephalopod(): Long {
        var grandTotal = 0L

        numberColumns.forEachIndexed { index, column ->
            grandTotal += solveOneCephalopod(operations[index], column)
        }

        return grandTotal
    }

    fun printColumnsAndOperations() {
        println(numberColumns)
        println(operations.map { it.toString() })
    }
}

fun main() {
    fun part1(input: List<String>): Long {

        val solver = MathHomeworkSolver(input)
        solver.readInput()
        solver.printColumnsAndOperations()

        return solver.solveAll()
    }

    fun part2(input: List<String>): Long {

        val solver = MathHomeworkSolver(input)
        solver.readInput()
        solver.printColumnsAndOperations()

        return solver.solveAllCephalopod()
    }

    println("Part 1:")
    part1(readInput("test06")).println()

    // Test if implementation meets criteria from the description:
    check(part1(readInput("test06")) == 4277556L)

    // Read the input from the `src/Day01.txt` file.

    val input = readInput("Day06")
    part1(input).println()

    println("Part 2:")

    part2(readInput("test06")).println()
    check(part2(readInput("test06")) == 3263827L)
    part2(readInput("test06")).println()
    part2(input).println()
}