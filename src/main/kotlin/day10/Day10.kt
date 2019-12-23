package day10

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sign

class Day10 {
    data class Grid(val points: Set<Point>, val width: Int, val height: Int)

    data class Point(val x: Int, val y: Int) {
        operator fun minus(other: Point) = Step(x - other.x, y - other.y)

        operator fun plus(step: Step) = Point(x + step.x, y + step.y)
    }

    data class Step(val x: Int, val y: Int) {
        fun angle() = atan2(y.toDouble(), x.toDouble())

        operator fun div(divisor: Int) = Step(x / divisor, y / divisor)
    }

    companion object {
        fun best(grid: Grid) =
            grid.points
                .map { it to visible(grid, it).size }
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

        fun sortByStepAngle(from: Point, points: Set<Point>) =
            points.sortedBy { to ->
                (to - from).angle().let {
                    if (it < -PI / 2)
                        it + 2 * PI
                    else
                        it
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

        fun visible(grid: Grid, from: Point): Set<Point> {
            val exceptFrom = grid.points - from
            val visible = exceptFrom.toMutableSet()

            exceptFrom.forEach { to ->
                val step = gridStep(from, to)

                visible.removeAll(pathToEdge(grid, to, step))
            }

            return visible
        }

        fun zap(grid: Grid, from: Point): List<Point> {
            val points = (grid.points - from).toMutableSet()
            val zapped = mutableListOf<Point>()

            while (points.isNotEmpty()) {
                val visible = visible(grid.copy(points = points), from)

                zapped.addAll(sortByStepAngle(from, visible))
                points.removeAll(visible)
            }

            return zapped
        }
    }
}

fun main() {
    val input = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readText()
    val grid = Day10.grid(input)
    val best = Day10.best(grid)

    println("Best: $best")

    val zapped = Day10.zap(grid, best.first)

    println("200th: ${zapped[199]}")
}
