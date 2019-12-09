package day03

import day02.main

fun main() {
    fun signalDelay(path: List<Point>, point: Point) = path.indexOf(point) + 1

    val (path1, path2) = ::main.javaClass.getResourceAsStream("input.txt").bufferedReader().readLines().map(::path)
    val intersection = path1.intersect(path2)
    val closestManhattan = intersection.minBy(Point::manhattanDistance)?.manhattanDistance()
    val shortestDelay = intersection.map {
        signalDelay(path1, it) + signalDelay(path2, it)
    }.min()

    println("Manhattan: $closestManhattan")
    println("Shortest delay: $shortestDelay")
}