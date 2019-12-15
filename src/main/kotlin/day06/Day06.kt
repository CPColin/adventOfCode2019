package day06

typealias Graph = Map<String, String>

typealias Path = List<String>

class Day06 {
    companion object {
        fun graph(lines: List<String>) =
            lines.map { it.split(")") }
                .map { (parent, child) -> child to parent }
                .toMap()

        fun orbits(graph: Graph, from: String): Int {
            val parent = graph[from]

            return if (parent == null) {
                0
            } else {
                1 + orbits(graph, parent)
            }
        }

        fun pathFromCenter(graph: Graph, to: String) = pathToCenter(graph, to).reversed()

        fun pathToCenter(graph: Graph, from: String) = generateSequence(from) { graph[it] }.toList()

        fun pivot(path1: Path, path2: Path): String? {
            val zip = path1.zip(path2)

            println(zip)

            zip.forEachIndexed { index, pair ->
                println("$index, $pair")
                if (pair.first != pair.second) {
                    return path1[index - 1]
                }
            }

            return path1.last()
        }

        fun transfers(graph: Graph, from: String, to: String): Int {
            val path1 = pathFromCenter(graph, from)
            val path2 = pathFromCenter(graph, to)
            val pivot = pivot(path1, path2)

            return path1.indexOf(from) - path1.indexOf(pivot) + path2.indexOf(to) - path2.indexOf(pivot)
        }

    }
}

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLines()
    val graph = Day06.graph(input)
    val allOrbits = graph.keys.sumBy { Day06.orbits(graph, it) }

    println("All orbits: $allOrbits")

    println("YOU -> SAN: ${Day06.transfers(graph, graph["YOU"]!!, graph["SAN"]!!)}")
}
