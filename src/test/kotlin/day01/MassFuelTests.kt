package day01

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class MassFuelTests {
    @TestParameters
    private fun massFuelParameters() = Stream.of(
        Arguments.of(9, 1),
        Arguments.of(6, 0),
        Arguments.of(3, 0),
        Arguments.of(12, 2),
        Arguments.of(14, 2),
        Arguments.of(1969, 654),
        Arguments.of(100756, 33583)
    )

    @ParameterizedTest
    @MethodSource("massFuelParameters")
    fun moduleFuel(mass: Int, expected: Int) {
        assertEquals(expected, massFuel(mass))
    }
}