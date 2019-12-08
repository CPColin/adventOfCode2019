package day02

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class RunTests {
    @TestParameters
    private fun runParameters() = Stream.of(
        Arguments.of(
            "1,9,10,3,2,3,11,0,99,30,40,50",
            "3500,9,10,70,2,3,11,0,99,30,40,50"
        ),
        Arguments.of(
            "1,0,0,0,99",
            "2,0,0,0,99"
        ),
        Arguments.of(
            "2,3,0,3,99",
            "2,3,0,6,99"
        ),
        Arguments.of(
            "2,4,4,5,99,0",
            "2,4,4,5,99,9801"
        ),
        Arguments.of(
            "1,1,1,4,99,5,6,0,99",
            "30,1,1,4,2,5,6,0,99"
        )
    )

    @ParameterizedTest
    @MethodSource("runParameters")
    fun run(input: String, expected: String) {
        val startState = State(
            input.split(",").map(Integer::parseInt).toMutableList(),
            0
        )
        val endState = run(startState).last()

        assertEquals(expected, endState.memory.joinToString(separator = ",") { it.toString() })
    }
}