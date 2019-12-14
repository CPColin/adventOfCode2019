package day05

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()

    val initialState = State(
        input = 1,
        memory = input.split(",").map(Integer::parseInt).toMutableList(),
        pc = 0
    )
    val finalState = run(initialState)

    println("Output: ${finalState.last().output}")
}