package day03

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class LineTests {
    @TestParameters
    private fun lineParameters() = Stream.of(
        Arguments.of(0, 0, Direction.U, 3, listOf(Point(0, 1), Point(0, 2), Point(0, 3))),
        Arguments.of(0, 0, Direction.D, 3, listOf(Point(0, -1), Point(0, -2), Point(0, -3))),
        Arguments.of(0, 0, Direction.L, 3, listOf(Point(-1, 0), Point(-2, 0), Point(-3, 0))),
        Arguments.of(0, 0, Direction.R, 3, listOf(Point(1, 0), Point(2, 0), Point(3, 0))),
        Arguments.of(3, 5, Direction.U, 3, listOf(Point(3, 6), Point(3, 7), Point(3, 8)))
    )

    @ParameterizedTest
    @MethodSource("lineParameters")
    fun line(x: Int, y: Int, direction: Direction, distance: Int, expected: Iterable<Point>) {
        assertEquals(expected, line(x, y, direction, distance))
    }
}