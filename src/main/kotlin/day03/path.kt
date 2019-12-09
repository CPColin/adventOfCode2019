package day03

fun path(codes: String): List<Point> {
    var origin = Point(0, 0)
    val points = mutableListOf<Point>()

    codes.split(",").map { LineCode(it) }.forEach {
        val line = line(origin.x, origin.y, it.direction, it.distance)

        points += line
        origin = line.last()
    }

    return points
}