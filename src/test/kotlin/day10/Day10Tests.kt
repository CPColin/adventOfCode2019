package day10

import day10.Day10.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals

@ParameterizedTestClass
class Day10Tests {
    @TestParameters
    private fun bestParameters() = Stream.of(
        Arguments.of(
            """
                ......#.#.
                #..#.#....
                ..#######.
                .#.#.###..
                .#..#.....
                ..#....#.#
                #..#....#.
                .##.#..###
                ##...#..#.
                .#....####
            """.trimIndent(),
            Point(5, 8),
            33
        ),
        Arguments.of(
            """
                #.#...#.#.
                .###....#.
                .#....#...
                ##.#.#.#.#
                ....#.#.#.
                .##..###.#
                ..#...##..
                ..##....##
                ......#...
                .####.###.
            """.trimIndent(),
            Point(1, 2),
            35
        ),
        Arguments.of(
            """
                .#..#..###
                ####.###.#
                ....###.#.
                ..###.##.#
                ##.##.#.#.
                ....###..#
                ..#.#..#.#
                #..#.#.###
                .##...##.#
                .....#.#..
            """.trimIndent(),
            Point(6, 3),
            41
        ),
        Arguments.of(
            """
                .#..##.###...#######
                ##.############..##.
                .#.######.########.#
                .###.#######.####.#.
                #####.##.#.##.###.##
                ..#####..#.#########
                ####################
                #.####....###.#.#.##
                ##.#################
                #####.##.###..####..
                ..######..##.#######
                ####.##.####...##..#
                .#####..#.######.###
                ##...#.##########...
                #.##########.#######
                .####.#.###.###.#.##
                ....##.##.###..#####
                .#.#.###########.###
                #.#.#.#####.####.###
                ###.##.####.##.#..##
            """.trimIndent(),
            Point(11, 13),
            210
        )
    )

    @ParameterizedTest
    @MethodSource("bestParameters")
    fun best(input: String, expectedPoint: Point, expectedCount: Int) {
        val grid = Day10.grid(input)
        val (bestPoint, bestCount) = Day10.best(grid)

        assertEquals(expectedPoint, bestPoint)
        assertEquals(expectedCount, bestCount)
    }

    @TestParameters
    private fun gcdParameters() = Stream.of(
        Arguments.of(1, 2, 1),
        Arguments.of(2, 4, 2),
        Arguments.of(3, 12, 3),
        Arguments.of(12, 15, 3),
        Arguments.of(100, 40, 20)
    )

    @ParameterizedTest
    @MethodSource("gcdParameters")
    fun gcd(a: Int, b: Int, expected: Int) {
        assertEquals(expected, Day10.gcd(a, b))
    }

    @Test
    fun grid() {
        val input = """
            .#..#
            .....
            #####
            ....#
            ...##
        """.trimIndent()
        val expected = Grid(
            points = setOf(
                Point(1, 0),
                Point(4, 0),
                Point(0, 2),
                Point(1, 2),
                Point(2, 2),
                Point(3, 2),
                Point(4, 2),
                Point(4, 3),
                Point(3, 4),
                Point(4, 4)
            ),
            width = 5,
            height = 5
        )

        assertEquals(expected, Day10.grid(input))
    }

    @TestParameters
    private fun gridStepParameters() = Stream.of(
        Arguments.of(0, 0, 1, 1, 1, 1),
        Arguments.of(0, 0, 0, 1, 0, 1),
        Arguments.of(0, 0, 1, 0, 1, 0),
        Arguments.of(0, 0, 0, 2, 0, 1),
        Arguments.of(0, 0, 2, 0, 1, 0),
        Arguments.of(0, 0, 2, 2, 1, 1),
        Arguments.of(1, 1, 2, 3, 1, 2),
        Arguments.of(1, 1, 3, 5, 1, 2),
        Arguments.of(1, 1, 0, 0, -1, -1),
        Arguments.of(1, 1, 0, 1, -1, 0),
        Arguments.of(1, 1, 1, 0, 0, -1),
        Arguments.of(3, 3, 1, 1, -1, -1)
    )

    @ParameterizedTest
    @MethodSource("gridStepParameters")
    fun gridStep(fromX: Int, fromY: Int, toX: Int, toY: Int, expectedX: Int, expectedY: Int) {
        assertEquals(Step(expectedX, expectedY), Day10.gridStep(Point(fromX, fromY), Point(toX, toY)))
    }

    @TestParameters
    private fun pathToEdgeParameters() = Stream.of(
        Arguments.of(Point(0, 0), Step(1, 0), setOf(Point(1, 0), Point(2, 0), Point(3, 0), Point(4, 0))),
        Arguments.of(Point(0, 0), Step(0, 1), setOf(Point(0, 1), Point(0, 2), Point(0, 3), Point(0, 4))),
        Arguments.of(Point(2, 3), Step(-1, -1), setOf(Point(1, 2), Point(0, 1))),
        Arguments.of(Point(1, 2), Step(2, 1), setOf(Point(3, 3)))
    )

    @ParameterizedTest
    @MethodSource("pathToEdgeParameters")
    fun pathToEdge(from: Point, step: Step, expected: Set<Point>) {
        val grid = Grid(points = emptySet(), width = 5, height = 5)

        assertEquals(expected, Day10.pathToEdge(grid, from, step))
    }
    
    @ParameterizedTest
    private fun visibleParameters() = Stream.of(
        Arguments.of(1, 0, 7),
        Arguments.of(4, 0, 7),
        Arguments.of(0, 2, 6),
        Arguments.of(1, 2, 7),
        Arguments.of(2, 2, 7),
        Arguments.of(3, 2, 7),
        Arguments.of(4, 2, 5),
        Arguments.of(4, 3, 7),
        Arguments.of(3, 4, 8),
        Arguments.of(4, 4, 7)
    )
    
    @ParameterizedTest
    @MethodSource("visibleParameters")
    fun visible(fromX: Int, fromY: Int, expected: Int) {
        val input = """
            .#..#
            .....
            #####
            ....#
            ...##
        """.trimIndent()
        val grid = Day10.grid(input)

        assertEquals(expected, Day10.visible(grid, Point(fromX, fromY)))
    }
}
