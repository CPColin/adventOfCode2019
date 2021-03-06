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
            null
        ),
        Arguments.of(
            0,
            "1,0,0,0,99",
            "2,0,0,0,99",
            null
        ),
        Arguments.of(
            0,
            "2,3,0,3,99",
            "2,3,0,6,99",
            null
        ),
        Arguments.of(
            0,
            "2,4,4,5,99,0",
            "2,4,4,5,99,9801",
            null
        ),
        Arguments.of(
            0,
            "1,1,1,4,99,5,6,0,99",
            "30,1,1,4,2,5,6,0,99",
            null
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
            null
        ),
        Arguments.of(
            123,
            "1101,100,-1,4,0",
            "1101,100,-1,4,99",
            null
        ),
        Arguments.of(
            7,
            "3,9,8,9,10,9,4,9,99,-1,8",
            null,
            0
        ),
        Arguments.of(
            8,
            "3,9,8,9,10,9,4,9,99,-1,8",
            null,
            1
        ),
        Arguments.of(
            7,
            "3,9,7,9,10,9,4,9,99,-1,8",
            null,
            1
        ),
        Arguments.of(
            8,
            "3,9,7,9,10,9,4,9,99,-1,8",
            null,
            0
        ),
        Arguments.of(
            7,
            "3,3,1108,-1,8,3,4,3,99",
            null,
            0
        ),
        Arguments.of(
            8,
            "3,3,1108,-1,8,3,4,3,99",
            null,
            1
        ),
        Arguments.of(
            7,
            "3,3,1107,-1,8,3,4,3,99",
            null,
            1
        ),
        Arguments.of(
            8,
            "3,3,1107,-1,8,3,4,3,99",
            null,
            0
        ),
        Arguments.of(
            0,
            "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9",
            null,
            0
        ),
        Arguments.of(
            2,
            "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9",
            null,
            1
        ),
        Arguments.of(
            0,
            "3,3,1105,-1,9,1101,0,0,12,4,12,99,1",
            null,
            0
        ),
        Arguments.of(
            2,
            "3,3,1105,-1,9,1101,0,0,12,4,12,99,1",
            null,
            1
        ),
        Arguments.of(
            7,
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99",
            null,
            999
        ),
        Arguments.of(
            8,
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99",
            null,
            1000
        ),
        Arguments.of(
            9,
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99",
            null,
            1001
        )
    )

    @ParameterizedTest
    @MethodSource("runParameters")
    fun run(input: Int, memory: String, expectedMemory: String?, expectedOutput: Int?) {
        val startState = State(
            input = input,
            memory = memory.split(",").map(Integer::parseInt).toMutableList(),
            pc = 0
        )
        val endState = run(startState).last()

        if (expectedMemory != null) {
            assertEquals(expectedMemory, endState.memory.joinToString(separator = ",") { it.toString() })
        }

        if (expectedOutput != null) {
            assertEquals(expectedOutput, endState.output)
        }
    }
}