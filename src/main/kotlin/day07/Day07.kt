package day07

class Day07 {
    companion object {
        fun amplify(memory: String, phases: List<Int>): Int =
            phases.fold(0) { accumulator, phase ->
                Intcode(
                    inputs = listOf(phase, accumulator),
                    memory = memory,
                    pc = 0
                ).run().last().output
            }

        fun maximumPhases(memory: String): Pair<List<Int>, Int> =
            permutations((0..4).toList()).map { phases -> phases to amplify(memory, phases) }.maxBy { it.second }!!

        fun <T> permutations(of: List<T>): List<List<T>> {
            return if (of.size == 1) {
                listOf(listOf(of.single()))
            } else {
                of.flatMap { first -> permutations(of - first).map { listOf(first) + it } }
            }
        }
    }
}

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()
    val maximumSignal = Day07.maximumPhases(input).second

    println(maximumSignal)
}
