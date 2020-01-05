package day14

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.assertEquals

@ParameterizedTestClass
class Day14Tests {
    @TestParameters
    private fun reactantFromStringParameters() = Stream.of(
        Arguments.of("1 A", Reactant("A", 1)),
        Arguments.of("89 FUEL", Reactant("FUEL", 89))
    )

    @ParameterizedTest
    @MethodSource("reactantFromStringParameters")
    fun reactantFromString(string: String, expected: Reactant) {
        assertEquals(expected, Reactant.fromString(string))
    }

    @TestParameters
    private fun reactionFromStringParameters() = Stream.of(
        Arguments.of(
            "1 A => 1 B",
            Reaction(
                listOf(Reactant("A", 1)),
                listOf(Reactant("B", 1))
            )
        ),
        Arguments.of(
            "1 A, 2 B => 1 C, 3 D",
            Reaction(
                listOf(Reactant("A", 1), Reactant("B", 2)),
                listOf(Reactant("C", 1), Reactant("D", 3))
            )
        )
    )

    @ParameterizedTest
    @MethodSource("reactionFromStringParameters")
    fun reactionFromString(string: String, expected: Reaction) {
        assertEquals(expected, Reaction.fromString(string))
    }
}