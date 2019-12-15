package day08

fun main() {
    val width = 25
    val height = 6

    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()
    val layers = input.chunked(width * height)
    val counts = layers.map { layer ->
        layer.groupingBy { it }.eachCount()
    }
    val fewestZeroes = counts.minBy { it['0'] ?: 0 }!!

    println("Part 1: ${fewestZeroes.getValue('1') * fewestZeroes.getValue('2')}")

    println("Part 2:")

    fun layerPixel(layer: String, x: Int, y: Int): Int = Integer.parseInt(layer[width * y + x].toString())

    fun pixel(x: Int, y: Int): Int = layers.map { layerPixel(it, x, y) }.dropWhile { it == 2 }.first()

    // These do the same thing, just in different styles.

    (0 until height).forEach { y ->
        (0 until width).forEach { x ->
            print(if (pixel(x, y) == 1) "█" else " ")
        }

        println()
    }

    println()

    println((0 until height).map { y ->
        (0 until width).map { x ->
            if (pixel(x, y) == 1) "█" else " "
        }.joinToString("")
    }.joinToString("\n"))
}