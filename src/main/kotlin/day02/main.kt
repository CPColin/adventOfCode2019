package day02

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()

    for (noun in 0..99) {
        for (verb in 0..99) {
            val initialState = State(
                memory = input.split(",").map(Integer::parseInt).toMutableList(),
                pc = 0
            ).apply {
                poke(1, noun)
                poke(2, verb)
            }
            val finalState = run(initialState)
            val output = finalState.last().memory[0]

            if (output == 19690720) {
                println(noun * 100 + verb)

                return
            }
        }
    }

    println("Failed!")
}