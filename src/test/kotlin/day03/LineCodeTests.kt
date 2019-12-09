package day03

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class LineCodeTests {
    @TestParameters
    private fun codeConstructorParameters() = Stream.of(
        Arguments.of("U3", LineCode(Direction.U, 3)),
        Arguments.of("D55", LineCode(Direction.D, 55)),
        Arguments.of("L42", LineCode(Direction.L, 42)),
        Arguments.of("R765", LineCode(Direction.R, 765))
    )

    @ParameterizedTest
    @MethodSource("codeConstructorParameters")
    fun codeConstructor(code: String, expected: LineCode) {
        assertEquals(expected, LineCode(code))
    }
}