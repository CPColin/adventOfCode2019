package day04

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class Day04Tests {
    @TestParameters
    private fun digitsParameters() = Stream.of(
        Arguments.of(7, listOf(7)),
        Arguments.of(1234, listOf(1, 2, 3, 4)),
        Arguments.of(3210, listOf(3, 2, 1, 0))
    )

    @ParameterizedTest
    @MethodSource("digitsParameters")
    fun digits(value: Int, expected: List<Int>) {
        assertEquals(expected, Day04.digits(value))
    }

    @TestParameters
    private fun hasRepeatParameters() = Stream.of(
        Arguments.of(11111, true),
        Arguments.of(12345, false),
        Arguments.of(12321, false),
        Arguments.of(11234, true),
        Arguments.of(11234, true),
        Arguments.of(21134, true),
        Arguments.of(23114, true),
        Arguments.of(23411, true)
    )

    @ParameterizedTest
    @MethodSource("hasRepeatParameters")
    fun hasRepeat(value: Int, expected: Boolean) {
        assertEquals(expected, Day04.hasRepeat(Day04.digits(value)))
    }

    @TestParameters
    private fun neverDecreasesParameters() = Stream.of(
        Arguments.of(11111, true),
        Arguments.of(12345, true),
        Arguments.of(11110, false),
        Arguments.of(12340, false),
        Arguments.of(11223, true)
    )

    @ParameterizedTest
    @MethodSource("neverDecreasesParameters")
    fun neverDecreases(value: Int, expected: Boolean) {
        assertEquals(expected, Day04.neverDecreases(Day04.digits(value)))
    }

    @TestParameters
    private fun isValidParameters() = Stream.of(
        Arguments.of(12345, false),
        Arguments.of(11345, true),
        Arguments.of(11343, false)
    )

    @ParameterizedTest
    @MethodSource("isValidParameters")
    fun isValid(value: Int, expected: Boolean) {
        assertEquals(expected, Day04.isValid(value))
    }

    @TestParameters
    private fun hasRepeatStrictParameters() = Stream.of(
        Arguments.of(112233, true),
        Arguments.of(123444, false),
        Arguments.of(111122, true)
    )

    @ParameterizedTest
    @MethodSource("hasRepeatStrictParameters")
    fun hasRepeatStrict(value: Int, expected: Boolean) {
        assertEquals(expected, Day04.hasRepeatStrict(Day04.digits(value)))
    }
}