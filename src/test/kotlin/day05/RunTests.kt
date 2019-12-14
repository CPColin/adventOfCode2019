package day05

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
            0,
            "1,9,10,3,2,3,11,0,99,30,40,50",
            "3500,9,10,70,2,3,11,0,99,30,40,50",
            0
        ),
        Arguments.of(
            0,
            "1,0,0,0,99",
            "2,0,0,0,99",
            0
        ),
        Arguments.of(
            0,
            "2,3,0,3,99",
            "2,3,0,6,99",
            0
        ),
        Arguments.of(
            0,
            "2,4,4,5,99,0",
            "2,4,4,5,99,9801",
            0
        ),
        Arguments.of(
            0,
            "1,1,1,4,99,5,6,0,99",
            "30,1,1,4,2,5,6,0,99",
            0
        ),
        Arguments.of(
            123,
            "3,0,4,0,99",
            "123,0,4,0,99",
            123
        ),
        Arguments.of(
            123,
            "1002,4,3,4,33",
            "1002,4,3,4,99",
            0
        ),
        Arguments.of(
            123,
            "1101,100,-1,4,0",
            "1101,100,-1,4,99",
            0
        )
    )

    @ParameterizedTest
    @MethodSource("runParameters")
    fun run(input: Int, memory: String, expectedMemory: String, expectedOutput: Int) {
        val startState = State(
            input = input,
            memory = memory.split(",").map(Integer::parseInt).toMutableList(),
            pc = 0
        )
        val endState = run(startState).last()

        assertEquals(expectedMemory, endState.memory.joinToString(separator = ",") { it.toString() })
        assertEquals(expectedOutput, endState.output)
    }
}