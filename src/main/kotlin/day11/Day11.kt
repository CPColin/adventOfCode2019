package day11

import day11.Day11.Color
import day11.Day11.Companion.runRobot
import day11.Day11.Point

class Day11 {
    enum class Color {
        BLACK,
        WHITE;

        fun glyph() = when(this) {
            BLACK -> " "
            WHITE -> "█"
        }
    }

    enum class Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        fun turnLeft() = when(this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }

        fun turnRight() = when(this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    data class Point(val x: Int, val y: Int) {
        fun move(direction: Direction) = when (direction) {
            Direction.UP -> copy(y = y - 1)
            Direction.RIGHT -> copy(x = x + 1)
            Direction.DOWN -> copy(y = y + 1)
            Direction.LEFT -> copy(x = x - 1)
        }
    }

    companion object {
        fun runRobot(initialPanelColor: Color): Map<Point, Color> {
            val memory = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLine()
            val intcode = Intcode(
                inputs = emptyList(),
                memory = memory
            )
            var direction = Direction.UP
            var location = Point(0, 0)
            val paintedPanels = mutableMapOf(
                location to initialPanelColor
            )

            fun input() = (paintedPanels[location] ?: Color.BLACK).ordinal

            while (!intcode.halted) {
                intcode.inputs.add(input().toLong())
                intcode.runUntilOutput()

                if (intcode.halted) {
                    break
                }

                paintedPanels[location] = Color.values()[intcode.output!!.toInt()]

                intcode.runUntilOutput()

                direction = if (intcode.output == 0L) {
                    direction.turnLeft()
                } else {
                    direction.turnRight()
                }

                location = location.move(direction)
            }

            return paintedPanels
        }
    }
}

fun main() {
    println("Part one: ${runRobot(Color.BLACK).size}")

    val partTwo = runRobot(Color.WHITE)
    val minX = partTwo.keys.map { it.x }.min()!!
    val maxX = partTwo.keys.map { it.x }.max()!!
    val minY = partTwo.keys.map { it.y }.min()!!
    val maxY = partTwo.keys.map { it.y }.max()!!
    fun pixel(x: Int, y: Int) = partTwo[Point(x, y)] ?: Color.BLACK

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            print(pixel(x, y).glyph())
        }
        println()
    }
}