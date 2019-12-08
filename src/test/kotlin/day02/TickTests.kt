package day02

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class TickTests {
    @TestParameters
    private fun tickParameters() = Stream.of(
        Arguments.of(
            "1,9,10,3,2,3,11,0,99,30,40,50",
            0,
            "1,9,10,70,2,3,11,0,99,30,40,50"
        ),
        Arguments.of(
            "1,9,10,70,2,3,11,0,99,30,40,50",
            4,
            "3500,9,10,70,2,3,11,0,99,30,40,50"
        ),
        Arguments.of(
            "3500,9,10,70,2,3,11,0,99,30,40,50",
            8,
            null
        )
    )

    @ParameterizedTest
    @MethodSource("tickParameters")
    fun tick(memory: String, pc: Int, expected: String?) {
        val startState = State(
            memory.split(",").map(Integer::parseInt).toMutableList(),
            pc
        )
        val endState = tick(startState)

        assertEquals(expected, endState?.memory?.joinToString(separator = ",") { it.toString() })
        if (expected != null) {
            assertEquals(pc + 4, endState?.pc)
        }
    }
}