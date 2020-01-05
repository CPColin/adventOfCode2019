package day13

import day11.Intcode
import kotlin.math.sign

data class Point(val x: Long, val y: Long)

enum class Tile {
    EMPTY,
    WALL,
    BLOCK,
    PADDLE,
    BALL
}

fun countBlocks(memory: String) {
    val intcode = Intcode(
        inputs = emptyList(),
        memory = memory
    )

    val outputs = generateSequence {
        intcode.runUntilOutput()
        intcode.output
    }
    val tiles = outputs.chunked(3) { (x, y, value) ->
        Point(x, y) to value.toInt()
    }.toMap()

    println("Blocks: ${tiles.count { it.value == Tile.BLOCK.ordinal }}")
}

fun playGame(memory: String) {
    val intcode = Intcode(
        inputs = emptyList(),
        memory = memory
    )

    intcode.memory[0] = 2 // Insert coin

    var ball: Point? = null
    var paddle: Point? = null
    var score = 0L
    val output = mutableListOf<Long>()
    val tiles = mutableMapOf<Point, Tile>()

    while (true) {
        val joystick = if (ball == null || paddle == null) 0 else (ball.x - paddle.x).sign
        
        intcode.inputs.clear()
        intcode.inputs.add(joystick.toLong())
        
        intcode.runUntilOutput()
        
        if (intcode.output == null) {
            break
        } else {
            output.add(intcode.output!!)
        }
        
        if (output.size == 3) {
            val (x, y, value) = output
            
            output.clear()
            
            if (x == -1L && y == 0L) {
                score = value
            } else {
                val point = Point(x, y)
                val tile = Tile.values()[value.toInt()]
                
                if (tile == Tile.BALL) {
                    ball = point
                } else if (tile == Tile.PADDLE) {
                    paddle = point
                }
                
                tiles[point] = tile
            }
        }
    }

    println("Final score: $score")
}

fun main() {
    val memory = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()

    countBlocks(memory)
    
    playGame(memory)
}