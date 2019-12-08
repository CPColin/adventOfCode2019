package day01

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class TotalFuelTests {
    @TestParameters
    private fun totalFuelParameters() = Stream.of(
        Arguments.of(listOf(12), 2),
        Arguments.of(listOf(14), 2),
        Arguments.of(listOf(12, 14), 4),
        Arguments.of(listOf(26), 6)
    )

    @ParameterizedTest
    @MethodSource("totalFuelParameters")
    fun totalFuel(moduleMasses: Iterable<Int>, expected: Int) {
        assertEquals(expected, totalFuel(moduleMasses))
    }
}