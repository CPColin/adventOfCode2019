package day03

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class PointTests {
    @TestParameters
    private fun manhattanDistanceParameters() = Stream.of(
        Arguments.of(0, 0, 0),
        Arguments.of(0, 1, 1),
        Arguments.of(0, 5, 5),
        Arguments.of(1, 0, 1),
        Arguments.of(5, 0, 5),
        Arguments.of(3, 4, 7),
        Arguments.of(-3, -4, 7)
    )

    @ParameterizedTest
    @MethodSource("manhattanDistanceParameters")
    fun manhattanDistance(x: Int, y: Int, expected: Int) {
        assertEquals(expected, Point(x, y).manhattanDistance())
    }

    @TestParameters
    private fun neighborParameters() = Stream.of(
        Arguments.of(0, 0, Direction.U, 0, 1),
        Arguments.of(0, 0, Direction.D, 0, -1),
        Arguments.of(0, 0, Direction.L, -1, 0),
        Arguments.of(0, 0, Direction.R, 1, 0),
        Arguments.of(5, 3, Direction.U, 5, 4),
        Arguments.of(5, 3, Direction.D, 5, 2),
        Arguments.of(5, 3, Direction.L, 4, 3),
        Arguments.of(5, 3, Direction.R, 6, 3)
    )

    @ParameterizedTest
    @MethodSource("neighborParameters")
    fun neighbor(x: Int, y: Int, direction: Direction, expectedX: Int, expectedY: Int) {
        assertEquals(Point(expectedX, expectedY), Point(x, y).neighbor(direction))
    }
}