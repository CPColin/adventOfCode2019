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
        ),
        Arguments.of(
            "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5",
            listOf(9, 8, 7, 6, 5),
            139629729
        ),
        Arguments.of(
            "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10",
            listOf(9, 7, 8, 5, 6),
            18216
        )
    )

    @ParameterizedTest
    @MethodSource("amplifyParameters")
    fun amplify(memory: String, phases: List<Int>, expected: Int) {
        assertEquals(expected, Day07.amplify(memory, phases))
    }

    @TestParameters
    private fun maximumParameters() = Stream.of(
        Arguments.of(
            "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0",
            0..4,
            listOf(4, 3, 2, 1, 0),
            43210
        ),
        Arguments.of(
            "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0",
            0..4,
            listOf(0, 1, 2, 3, 4),
            54321
        ),
        Arguments.of(
            "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0",
            0..4,
            listOf(1, 0, 4, 3, 2),
            65210
        ),
        Arguments.of(
            "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5",
            5..9,
            listOf(9, 8, 7, 6, 5),
            139629729
        ),
        Arguments.of(
            "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10",
            5..9,
            listOf(9, 7, 8, 5, 6),
            18216
        )
    )

    @ParameterizedTest
    @MethodSource("maximumParameters")
    fun maximum(memory: String, phasePool: IntRange, expectedPhases: List<Int>, expectedOutput: Int) {
        val (phases, output) = Day07.maximum(memory, phasePool)

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