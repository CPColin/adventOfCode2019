package day07

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class IntCodeTests {
    @Test
    fun `operand - immediate`() {
        val intcode = Intcode(
            input = 123,
            memory = mutableListOf(10, 20, 30, 40, 50, 60),
            pc = 0
        )

        assertEquals(40, intcode.operand(3, Intcode.ParameterMode.IMMEDIATE))
    }

    @Test
    fun `operand - position`() {
        val intcode = Intcode(
            input = 123,
            memory = mutableListOf(10, 20, 30, 4, 50, 60),
            pc = 0
        )

        assertEquals(50, intcode.operand(3, Intcode.ParameterMode.POSITION))
    }

    @TestParameters
    private fun parameterModesParameters() = Stream.of(
        Arguments.of("1", emptyMap<Int, Intcode.ParameterMode>()),
        Arguments.of("01", emptyMap<Int, Intcode.ParameterMode>()),
        Arguments.of("101", mapOf(0 to Intcode.ParameterMode.IMMEDIATE)),
        Arguments.of("1001", mapOf(0 to Intcode.ParameterMode.POSITION, 1 to Intcode.ParameterMode.IMMEDIATE)),
        Arguments.of("1101", mapOf(0 to Intcode.ParameterMode.IMMEDIATE, 1 to Intcode.ParameterMode.IMMEDIATE))
    )

    @ParameterizedTest
    @MethodSource("parameterModesParameters")
    fun parameterModes(operation: Int, expected: Map<Int, Intcode.ParameterMode>) {
        assertEquals(expected, Intcode.parameterModes(operation))
    }

    @Test
    fun peek() {
        val intcode = Intcode(
            input = 123,
            memory = mutableListOf(10, 20, 30, 40, 50, 60),
            pc = 0
        )

        assertEquals(30, intcode.peek(2))
    }

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
        val start = Intcode(
            input = input,
            memory = memory.split(",").map(Integer::parseInt).toMutableList(),
            pc = 0
        )
        val end = start.run().last()

        if (expectedMemory != null) {
            assertEquals(expectedMemory, end.memory.joinToString(separator = ",") { it.toString() })
        }

        if (expectedOutput != null) {
            assertEquals(expectedOutput, end.output)
        }
    }

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
        val start = Intcode(
            input = 0,
            memory = memory.split(",").map(Integer::parseInt).toMutableList(),
            pc = pc
        )
        val end = start.tick()

        assertEquals(expected, end?.memory?.joinToString(separator = ",") { it.toString() })
        if (expected != null) {
            assertEquals(pc + 4, end?.pc)
        }
    }
}