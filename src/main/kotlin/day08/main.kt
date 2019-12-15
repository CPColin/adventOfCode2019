package day08

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()
    val layers = input.chunked(25 * 6)
    val counts = layers.map { layer ->
        layer.groupingBy { it }.eachCount()
    }
    val fewestZeroes = counts.minBy { it['0'] ?: 0 }!!

    println("Part 1: ${fewestZeroes.getValue('1') * fewestZeroes.getValue('2')}")
}