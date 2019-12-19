package day09

fun main() {
    val memory = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()
    val intcode = Intcode(
        input = 2,
        memory = memory
    )

    val output = generateSequence {
        intcode.runUntilOutput()
        intcode.output
    }.toList()

    println("output: $output")
}