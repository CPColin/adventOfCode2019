package day07

class Day07 {
    companion object {
        fun amplify(memory: String, phases: List<Int>): Int {
            val amplifiers = phases.map { phase ->
                Intcode(
                    inputs = listOf(phase),
                    memory = memory,
                    pc = 0
                )
            }
            var index = 0
            var signal = 0

            while (true) {
                val amplifier = amplifiers[index]

                if (amplifier.halted) {
                    return signal
                }

                amplifier.inputs.add(signal)
                amplifier.runUntilOutput()

                if (amplifier.output == null) {
                    return signal
                }

                signal = amplifier.output!!

                index = (index + 1) % amplifiers.size
            }
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
    println("Maximum with feedback: ${Day07.maximum(input, 5..9).second}")
}
