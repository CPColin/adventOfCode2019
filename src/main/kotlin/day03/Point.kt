package day03

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    fun manhattanDistance() = abs(x) + abs(y)

    fun neighbor(direction: Direction) = when (direction) {
        Direction.U -> copy(y = y + 1)
        Direction.D -> copy(y = y - 1)
        Direction.L -> copy(x = x - 1)
        Direction.R -> copy(x = x + 1)
    }
}