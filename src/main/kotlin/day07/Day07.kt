package day07

class Day07 {
    companion object {
        fun amplify(memory: String, phases: List<Int>): Int =
            phases.fold(0) { accumulator, phase ->
                Intcode(
                    inputs = listOf(phase, accumulator),
                    memory = memory,
                    pc = 0
                ).apply { runUntilOutput() }.output!!
            }

        fun maximum(memory: String, phasePool: IntRange): Pair<List<Int>, Int> =
            permutations(phasePool.toList()).map { phases -> phases to amplify(memory, phases) }.maxBy { it.second }!!

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

    println("Maximum without feedback: ${Day07.maximum(input, 0..4).second}")
}
