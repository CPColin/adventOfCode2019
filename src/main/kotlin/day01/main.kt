package day01

fun main() {
    val moduleMasses = ::main.javaClass.getResourceAsStream("input.txt")
        .bufferedReader().readLines()
        .map(Integer::parseInt)

    println(totalFuel(moduleMasses))
}