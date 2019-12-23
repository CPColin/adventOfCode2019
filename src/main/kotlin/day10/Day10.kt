package day10

import kotlin.math.abs
import kotlin.math.sign

class Day10 {
    data class Grid(val points: Set<Point>, val width: Int, val height: Int)

    data class Point(val x: Int, val y: Int) {
        operator fun minus(other: Point) = Step(x - other.x, y - other.y)

        operator fun plus(step: Step) = Point(x + step.x, y + step.y)
    }

    data class Step(val x: Int, val y: Int) {
        operator fun div(divisor: Int) = Step(x / divisor, y / divisor)
    }

    companion object {
        fun best(grid: Grid) =
            grid.points
                .map { it to visible(grid, it) }
                .maxBy { it.second }!!

        tailrec fun gcd(a: Int, b: Int): Int =
            if (b == 0) {
                a
            } else {
                gcd(b, a % b)
            }

        fun grid(input: String) = input.lines().let { lines ->
            Grid(
                points = lines.asSequence()
                    .mapIndexed { y, line ->
                        line.mapIndexed { x, char -> Point(x, y) to char }
                    }
                    .flatten()
                    .filter { (_, char) -> char == '#' }
                    .map { (point, _) -> point }
                    .toSet(),
                width = lines.first().length,
                height = lines.size
            )
        }

        fun gridStep(from: Point, to: Point): Step {
            val step = to - from

            return when {
                step.x == 0 -> Step(0, step.y.sign)
                step.y == 0 -> Step(step.x.sign, 0)
                else -> step / abs(gcd(step.x, step.y))
            }
        }

        fun pathToEdge(grid: Grid, from: Point, step: Step) =
            generateSequence(from) {
                (it + step).let { to ->
                    if (to.x in 0 until grid.width && to.y in 0 until grid.height)
                        to
                    else
                        null
                }
            }.drop(1).toSet()

        fun visible(grid: Grid, from: Point): Int {
            val exceptFrom = grid.points - from
            val visible = exceptFrom.toMutableSet()

            exceptFrom.forEach { to ->
                val step = gridStep(from, to)

                visible.removeAll(pathToEdge(grid, to, step))
            }

            return visible.size
        }
    }
}

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readText()
    val grid = Day10.grid(input)

    println("Best: ${Day10.best(grid)}")
}
