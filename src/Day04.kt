data class Point(val x: Int, val y: Int) {
    fun neighbors(): List<Point> =
        (-1..1).flatMap { dx ->
            (-1..1).map { dy -> Point(x + dx, y + dy) }
        }.filter { it != this } // Exclude (0,0) - ourselves
}

class RollsWallSolver(input: List<String>) {

    // We store only @ in the set
    private val initialRolls: Set<Point>

    init {
        val rolls = mutableSetOf<Point>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char == '@') {
                    rolls.add(Point(row, col))
                }
            }
        }
        initialRolls = rolls
    }

    private fun isLoose(point: Point, currentRolls: Set<Point>): Boolean {
        // Count how many neighbors are there
        val neighborCount = point.neighbors().count { it in currentRolls }
        return neighborCount < 4
    }

    fun solvePart1(): Int {
        return initialRolls.count { isLoose(it, initialRolls) }
    }

    fun solvePart2(): Int {
        // We make a mutable copy to update every iteration
        val currentRolls = initialRolls.toMutableSet()
        var totalRemoved = 0

        while (true) {
            // 1. Find all rolls that need to be removed in this round
            val toRemove = currentRolls.filter { isLoose(it, currentRolls) }.toSet()

            // 2. If nothing to remove, we are done
            if (toRemove.isEmpty()) break

            // 3. Update state
            currentRolls.removeAll(toRemove)
            totalRemoved += toRemove.size
        }

        return totalRemoved
    }
}

fun main() {
    val input = readInput("Day04")

    val solver = RollsWallSolver(input)

    println("Part 1: ${solver.solvePart1()}")
    println("Part 2: ${solver.solvePart2()}")
}