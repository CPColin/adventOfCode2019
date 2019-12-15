package day06

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals

@ParameterizedTestClass
class Day06Tests {
    val testInput =
        """
            COM)B
            B)C
            C)D
            D)E
            E)F
            B)G
            G)H
            D)I
            E)J
            J)K
            K)L
            """.trimIndent().lines()

    @Test
    fun graph() {
        val expected = mapOf(
            "B" to "COM",
            "C" to "B",
            "D" to "C",
            "E" to "D",
            "F" to "E",
            "G" to "B",
            "H" to "G",
            "I" to "D",
            "J" to "E",
            "K" to "J",
            "L" to "K"
        )

        assertEquals(expected, Day06.graph(testInput))
    }

    @TestParameters
    private fun orbitsParameters() = Stream.of(
        Arguments.of("D", 3),
        Arguments.of("L", 7),
        Arguments.of("COM", 0)
    )

    @ParameterizedTest
    @MethodSource("orbitsParameters")
    fun orbits(from: String, expected: Int) {
        assertEquals(expected, Day06.orbits(Day06.graph(testInput), from))
    }

    @TestParameters
    private fun pathToCenterParameters() = Stream.of(
        Arguments.of("COM", listOf("COM")),
        Arguments.of("B", listOf("B", "COM")),
        Arguments.of("H", listOf("H", "G", "B", "COM")),
        Arguments.of("L", listOf("L", "K", "J", "E", "D", "C", "B", "COM"))
    )

    @ParameterizedTest
    @MethodSource("pathToCenterParameters")
    fun pathToCenter(from: String, expected: List<String>) {
        assertEquals(expected, Day06.pathToCenter(Day06.graph(testInput), from))
    }

    @Test
    fun pivot() {
        val graph = Day06.graph(testInput)
        val path1 = Day06.pathFromCenter(graph, "K")
        val path2 = Day06.pathFromCenter(graph, "I")

        assertEquals("D", Day06.pivot(path1, path2))
    }

    @Test
    fun transfers() {
        val graph = Day06.graph(testInput)

        assertEquals(4, Day06.transfers(graph, "K", "I"))
    }
}