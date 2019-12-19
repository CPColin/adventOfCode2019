package day09

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ParameterizedTestClass
class IntCodeTests {
    @TestParameters
    private fun digitsParameters() = Stream.of(
        Arguments.of(7, listOf(7)),
        Arguments.of(1234, listOf(1, 2, 3, 4)),
        Arguments.of(3210, listOf(3, 2, 1, 0))
    )

    @ParameterizedTest
    @MethodSource("digitsParameters")
    fun digits(value: Long, expected: List<Int>) {
        assertEquals(expected, Intcode.digits(value))
    }

    @Test
    fun `operand - immediate`() {
        val intcode = Intcode(
            memory = "10,20,30,40,50,60"
        )

        assertEquals(40, intcode.operand(3, Intcode.ParameterMode.IMMEDIATE))
    }

    @Test
    fun `operand - position`() {
        val intcode = Intcode(
            memory = "10,20,30,4,50,60"
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
    fun parameterModes(operation: Long, expected: Map<Int, Intcode.ParameterMode>) {
        assertEquals(expected, Intcode.parameterModes(operation))
    }

    @Test
    fun peek() {
        val intcode = Intcode(
            memory = "10,20,30,40,50,60"
        )

        assertEquals(30, intcode.peek(2))
    }

    @TestParameters
    private fun runUntilHaltParameters() = Stream.of(
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
    @MethodSource("runUntilHaltParameters")
    fun runUntilHalt(input: Int, memory: String, expectedMemory: String?, expectedOutput: Int?) {
        val intcode = Intcode(
            input = input.toLong(),
            memory = memory
        )

        intcode.runUntilHalt()

        if (expectedMemory != null) {
            assertEquals(expectedMemory, intcode.memory.joinToString(separator = ",") { it.toString() })
        }

        if (expectedOutput != null) {
            assertEquals(expectedOutput.toLong(), intcode.output)
        }
    }

    @TestParameters
    private fun runUntilOutputParameters() = Stream.of(
        Arguments.of(
            "104,1,1,0,0,0,104,2,99",
            listOf(1, 2)
        ),
        Arguments.of(
            "1102,34915192,34915192,7,4,7,99,0",
            listOf(1219070632396864) // Above values multiplied together
        ),
        Arguments.of(
            "104,1125899906842624,99",
            listOf(1125899906842624)
        )
    )

    @ParameterizedTest
    @MethodSource("runUntilOutputParameters")
    fun runUntilOutput(memory: String, expectedOutput: List<Long>) {
        val intcode = Intcode(
            memory = memory
        )

        expectedOutput.forEach {
            intcode.runUntilOutput()
            assertEquals(it, intcode.output)
        }

        intcode.runUntilOutput()
        assertEquals(null, intcode.output)
        assertTrue(intcode.halted)
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
        )
    )

    @ParameterizedTest
    @MethodSource("tickParameters")
    fun tick(memory: String, pc: Long, expected: String) {
        val intcode = Intcode(
            input = 0,
            memory = memory,
            pc = pc
        )

        intcode.tick()

        assertEquals(expected, intcode.memory.joinToString(separator = ",") { it.toString() })
        assertEquals(pc + 4, intcode.pc)
    }
}