package day05

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class ParameterModesTests {
    @TestParameters
    private fun parameterModesParameters() = Stream.of(
        Arguments.of("1", emptyMap<Int, ParameterMode>()),
        Arguments.of("01", emptyMap<Int, ParameterMode>()),
        Arguments.of("101", mapOf(0 to ParameterMode.IMMEDIDATE)),
        Arguments.of("1001", mapOf(0 to ParameterMode.POSITION, 1 to ParameterMode.IMMEDIDATE)),
        Arguments.of("1101", mapOf(0 to ParameterMode.IMMEDIDATE, 1 to ParameterMode.IMMEDIDATE))
    )

    @ParameterizedTest
    @MethodSource("parameterModesParameters")
    fun parameterModes(operation: Int, expected: Map<Int, ParameterMode>) {
        assertEquals(expected, parameterModes(operation))
    }
}