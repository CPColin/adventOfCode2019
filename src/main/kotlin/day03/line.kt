package day03

fun line(x: Int, y: Int, direction: Direction, distance: Int) =
    generateSequence(Point(x, y)) { it.neighbor(direction) }.drop(1).take(distance).toList()