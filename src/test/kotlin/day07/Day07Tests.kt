package day07

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class Day07Tests {
    @TestParameters
    private fun amplifyParameters() = Stream.of(
        Arguments.of(
            "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0",
            listOf(4, 3, 2, 1, 0),
            43210
        ),
        Arguments.of(
            "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0",
            listOf(0, 1, 2, 3, 4),
            54321
        ),
        Arguments.of(
            "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0",
            listOf(1, 0, 4, 3, 2),
            65210
        )
    )

    @ParameterizedTest
    @MethodSource("amplifyParameters")
    fun amplify(memory: String, phases: List<Int>, expected: Int) {
        assertEquals(expected, Day07.amplify(memory, phases))
    }

    @TestParameters
    private fun maximumPhasesParameters() = Stream.of(
        Arguments.of(
            "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0",
            listOf(4, 3, 2, 1, 0)
        ),
        Arguments.of(
            "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0",
            listOf(0, 1, 2, 3, 4)
        ),
        Arguments.of(
            "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0",
            listOf(1, 0, 4, 3, 2)
        )
    )

    @ParameterizedTest
    @MethodSource("amplifyParameters")
    fun maximumPhases(memory: String, expectedPhases: List<Int>, expectedOutput: Int) {
        val (phases, output) = Day07.maximumPhases(memory)

        assertEquals(expectedPhases, phases)
        assertEquals(expectedOutput, output)
    }

    @TestParameters
    private fun permutationsParameters() = Stream.of(
        Arguments.of(
            listOf(1),
            listOf(
                listOf(1)
            )
        ),
        Arguments.of(
            listOf(1, 2),
            listOf(
                listOf(1, 2),
                listOf(2, 1)
            )
        ),
        Arguments.of(
            listOf("A", "B", "C"),
            listOf(
                listOf("A", "B", "C"),
                listOf("A", "C", "B"),
                listOf("B", "A", "C"),
                listOf("B", "C", "A"),
                listOf("C", "A", "B"),
                listOf("C", "B", "A")
            )
        )
    )

    @ParameterizedTest
    @MethodSource("permutationsParameters")
    fun <T> permutations(of: List<T>, expected: List<List<T>>) {
        assertEquals(expected, Day07.permutations(of))
    }
}