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

class DisjointSet(val numberOfVertices: Int) {
    val graphs = mutableMapOf<Vertex, Int>()
    private var maxGraphId = 0

    fun getGraphId(p: Vertex): Int {
        return graphs[p] ?: 0
    }

    fun findAllVerticesForGraph(graphId: Int): Set<Vertex> {
        return graphs.filterValues { it == graphId }.keys
    }

    fun mergeGraphs(id1: Int, id2: Int): Int {
        val vertices1 = findAllVerticesForGraph(id1)
        val vertices2 = findAllVerticesForGraph(id2)

        val combinedGraph = vertices1 + vertices2
        ++maxGraphId

        combinedGraph.forEach { vertex ->
            graphs[vertex] = maxGraphId
        }
        return maxGraphId
    }

    fun union(p: Vertex, q: Vertex): Long {
        val graphIdP = getGraphId(p)
        val graphIdQ = getGraphId(q)

        if (graphIdP == graphIdQ) {
            if (graphIdP == 0) {
                ++maxGraphId
                graphs[p] = maxGraphId
                graphs[q] = maxGraphId
            }
            return 0L
        }

        var newGraphId = 0

        if (graphIdP == 0) {
            graphs[p] = graphIdQ
            newGraphId = graphIdQ
        }
        else if (graphIdQ == 0) {
            graphs[q] = graphIdP
            newGraphId = graphIdP
        }
        else
            newGraphId = mergeGraphs(graphIdP, graphIdQ)

        if (findAllVerticesForGraph(newGraphId).size == numberOfVertices) {
            return (p.first * q.first).toLong()
        }
        return 0L
    }
}

val edgeComparator = Comparator<Edge> { x, y -> (x.euclideanDistance - y.euclideanDistance).toInt() }

class MinGraphSolver(
    val input: List<String>,
/*    val numberOfEdgesConnected: Int,
    val topResults: Int,*/
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

    fun minSpanningTree(): Long {
        val disjointSet = DisjointSet(numberOfLines)

        while (true) {
            val edge = edgeMinHeap.poll()
            val src = edge.firstVertex
            val dest = edge.secondVertex

            val unionResult = disjointSet.union(src, dest)
            if (unionResult > 0)
                return unionResult
        }
    }

}

fun main() {
    fun part1(input: List<String>, number: Int, topResults: Int): Int {
        /*val solver = MinGraphSolver(input, number, topResults)
        return solver.minSpanningTree()*/
        return 0
    }

    fun part2(input: List<String>): Long {
        val solver = MinGraphSolver(input)
        return solver.minSpanningTree()
    }
/*

    println("Part 1:")
    part1(readInput("test08"), 10, 3).println()
    check(part1(readInput("test08"), 10, 3) == 40)


    part1(input, 1000, 3).println()
*/
    val input = readInput("Day08")
    println("Part 2:")

    part2(readInput("test08")).println()
    check(part2(readInput("test08")) == 25272L)
    part2(input).println()
}