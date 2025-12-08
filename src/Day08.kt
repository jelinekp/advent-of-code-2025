import java.util.PriorityQueue
import kotlin.math.sqrt

data class Vertex(
    val first: Double,
    val second: Double,
    val third: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex

        if (first != other.first) return false
        if (second != other.second) return false
        if (third != other.third) return false

        return true
    }

    override fun hashCode(): Int {
        var result = first.hashCode()
        result = 31 * result + second.hashCode()
        result = 31 * result + third.hashCode()
        return result
    }

    override fun toString(): String {
        return "<${first.toInt()},${second.toInt()},${third.toInt()}>"
    }


}

data class Edge(
    val firstVertex: Vertex,
    val secondVertex: Vertex,
    val euclideanDistance: Double,
) {
    override fun toString(): String {
        return "-$firstVertex |$euclideanDistance| $secondVertex-"
    }
}

class DisjointSet<T> {
    val graphs = mutableMapOf<T, Int>()
    private var maxGraphId = 0

    fun getGraphId(p: T): Int {
        return graphs[p] ?: 0
    }

    fun findAllVerticesForGraph(graphId: Int): Set<T> {
        return graphs.filterValues { it == graphId }.keys
    }

    fun mergeGraphs(id1: Int, id2: Int) {
        val vertices1 = findAllVerticesForGraph(id1)
        val vertices2 = findAllVerticesForGraph(id2)

        ++maxGraphId

        (vertices1 + vertices2).forEach { vertex ->
            graphs[vertex] = maxGraphId
        }

    }

    fun union(p: T, q: T) {
        val graphIdP = getGraphId(p)
        val graphIdQ = getGraphId(q)

        if (graphIdP == graphIdQ) {
            if (graphIdP == 0) {
                ++maxGraphId
                graphs[p] = maxGraphId
                graphs[q] = maxGraphId
            }
            return
        }

        if (graphIdP == 0)
            graphs[p] = graphIdQ
        else if (graphIdQ == 0)
            graphs[q] = graphIdP
        else
            mergeGraphs(graphIdP, graphIdQ)




        // find all vertices from given graph and look for connections


    }
}

val edgeComparator = Comparator<Edge> { x, y -> (x.euclideanDistance - y.euclideanDistance).toInt() }

class MinGraphSolver(
    val input: List<String>,
    val numberOfEdgesConnected: Int,
    val topResults: Int,
) {

    private val numberOfLines = input.size

    private val vertices = mutableListOf<Vertex>()

    private val edgeMinHeap: PriorityQueue<Edge> = PriorityQueue(
        /* initialCapacity = */ 1000,
        /* comparator = */ edgeComparator
    )

    init {
        readInputAndFillMinHeap()
    }

    private fun readInputAndFillMinHeap() {
        input.forEach { line ->
            val split = line.split(',')
            if (split.size == 3) {
                val vertex = Vertex(split[0].toDouble(), split[1].toDouble(), split[2].toDouble())
                vertices.add(vertex)
            } else {
                throw error("Invalid input")
            }
        }

        for (i in 0..<vertices.size) {
            val outerVertex = vertices[i]
            for (j in i + 1..<vertices.size) {
                val innerVertex = vertices[j]
                edgeMinHeap.add(Edge(outerVertex, innerVertex, getEuclideanDistance(outerVertex, innerVertex)))
            }
        }

        println("Loaded ${edgeMinHeap.size} edges")
        val minEdge = edgeMinHeap.first()
        println("First edge: $minEdge")
    }

    private fun getEuclideanDistance(a: Vertex, b: Vertex) =
        sqrt((a.first - b.first) * (a.first - b.first) + (a.second - b.second) * (a.second - b.second) + (a.third - b.third) * (a.third - b.third))

    fun minSpanningTree(): Int {
        val disjointSet = DisjointSet<Vertex>()

        repeat (numberOfEdgesConnected) {
            val edge = edgeMinHeap.poll()
            val src = edge.firstVertex
            val dest = edge.secondVertex

            disjointSet.union(src, dest)
        }

        println("-----------------SPANNING TREE-----------------")
        println("-----------------GRAPHS-----------------")

        val mapOfResults = mutableMapOf<Int, Int>()
        disjointSet.graphs.values.map {
            mapOfResults[it] = mapOfResults.getOrDefault(it, 0) + 1
        }

        val result = mapOfResults.values.sortedDescending()

        var numberToFind = result[0]
        for (i in 1..<topResults) {
            numberToFind *= result[i]
        }

        println(mapOfResults)

        println(numberToFind)
        println("----------------END------------------")

        return numberToFind
    }

}

fun main() {
    fun part1(input: List<String>, number: Int, topResults: Int): Int {
        val solver = MinGraphSolver(input, number, topResults)
        return solver.minSpanningTree()
    }

    fun part2(input: List<String>): Long {
        val solver = MinGraphSolver(input, 0, 0)
        return 0L
    }

    println("Part 1:")
    part1(readInput("test08"), 10, 3).println()
    check(part1(readInput("test08"), 10, 3) == 40)

    val input = readInput("Day08")
    part1(input, 1000, 3).println()

    println("Part 2:")

    part2(readInput("test08")).println()
    check(part2(readInput("test08")) == 40L)
    part2(input).println()
}