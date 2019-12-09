package day03

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class PathTests {
    @TestParameters
    private fun pathParameters() = Stream.of(
        Arguments.of(
            "U3,R4",
            listOf(
                Point(0, 1), Point(0, 2), Point(0, 3),
                Point(1, 3), Point(2, 3), Point(3, 3), Point(4, 3)
            )
        )
    )

    @ParameterizedTest
    @MethodSource("pathParameters")
    fun path(codes: String, expected: List<Point>) {
        assertEquals(expected, path(codes))
    }
}