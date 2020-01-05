package day13

import day11.Intcode

data class Point(val x: Int, val y: Int)

enum class Tile {
    EMPTY,
    WALL,
    BLOCK,
    PADDLE,
    BALL
}

fun main() {
    val memory = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()
    val intcode = Intcode(
        inputs = emptyList(),
        memory = memory
    )
    val outputs = generateSequence {
        intcode.runUntilOutput()
        intcode.output
    }
    val tiles = outputs.chunked(3) { (x, y, tile) ->
        Point(x.toInt(), y.toInt()) to Tile.values()[tile.toInt()]
    }.toMap()

    println(tiles.count { it.value == Tile.BLOCK })
}