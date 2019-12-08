package day01

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class ModuleFuelTests {
    @TestParameters
    private fun moduleFuelParameters() = Stream.of(
        Arguments.of(14, 2),
        Arguments.of(1969, 966),
        Arguments.of(100756, 50346)
    )

    @ParameterizedTest
    @MethodSource("moduleFuelParameters")
    fun moduleFuel(moduleMass: Int, expected: Int) {
        assertEquals(expected, moduleFuel(moduleMass))
    }
}