package day12

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import util.ParameterizedTestClass
import util.TestParameters
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals

@ParameterizedTestClass
class Day12Tests {
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
    fun gcd(a: Long, b: Long, expected: Long) {
        assertEquals(expected, System.gcd(a, b))
    }

    @TestParameters
    private fun lcmParameters() = Stream.of(
        Arguments.of(listOf(7), 7),
        Arguments.of(listOf(2, 7), 14),
        Arguments.of(listOf(1, 2, 3), 6),
        Arguments.of(listOf(2, 3, 4), 12),
        Arguments.of(listOf(3, 4, 5), 60),
        Arguments.of(listOf(1, 2, 4), 4)
    )

    @ParameterizedTest
    @MethodSource("lcmParameters")
    fun lcm(values: List<Long>, expected: Long) {
        assertEquals(expected, System.lcm(*values.toLongArray()))
    }

    @TestParameters
    private fun moonApplyGravityParameters() = Stream.of(
        Arguments.of(
            Moon(Position(1, 2, 3), Velocity(5, 5, 5)),
            listOf(Moon(Position(3, 2, 1))),
            Moon(Position(1, 2, 3), Velocity(6, 5, 4))
        ),
        Arguments.of(
            Moon(Position(1, 2, 3), Velocity(5, 5, 5)),
            listOf(Moon(Position(3, 2, 1)), Moon(Position(5, 5, 0))),
            Moon(Position(1, 2, 3), Velocity(7, 6, 3))
        )
    )

    @ParameterizedTest
    @MethodSource("moonApplyGravityParameters")
    fun moonApplyGravity(moon: Moon, otherMoons: List<Moon>, expected: Moon) {
        assertEquals(expected, moon.applyGravity(otherMoons))
    }

    @Test
    fun moonApplyVelocity() {
        val initialMoon = Moon(Position(1, 2, 3), Velocity(-2, 0, 3))
        val expectedMoon = Moon(Position(-1, 2, 6), Velocity(-2, 0, 3))

        assertEquals(expectedMoon, initialMoon.applyVelocity())
    }

    @TestParameters
    private fun moonGravitySignParameters() = Stream.of(
        Arguments.of(3, 5, +1),
        Arguments.of(5, 3, -1),
        Arguments.of(3, 3, 0)
    )

    @ParameterizedTest
    @MethodSource("moonGravitySignParameters")
    fun moonGravitySign(position1: Int, position2: Int, expected: Int) {
        assertEquals(expected, Moon.gravitySign(position1, position2))
    }

    @TestParameters
    private fun moonKineticEnergyParameters() = Stream.of(
        Arguments.of(Moon(Position(2, 1, -3), Velocity(-3, -2, 1)), 6),
        Arguments.of(Moon(Position(1, -8, 0), Velocity(-1, 1, 3)), 5),
        Arguments.of(Moon(Position(3, -6, 1), Velocity(3, 2, -3)), 8),
        Arguments.of(Moon(Position(2, 0, 4), Velocity(1, -1, -1)), 3)
    )

    @ParameterizedTest
    @MethodSource("moonKineticEnergyParameters")
    fun moonKineticEnergy(moon: Moon, expected: Int) {
        assertEquals(expected, moon.kineticEnergy)
    }

    @TestParameters
    private fun moonPotentialEnergyParameters() = Stream.of(
        Arguments.of(Moon(Position(2, 1, -3), Velocity(-3, -2, 1)), 6),
        Arguments.of(Moon(Position(1, -8, 0), Velocity(-1, 1, 3)), 9),
        Arguments.of(Moon(Position(3, -6, 1), Velocity(3, 2, -3)), 10),
        Arguments.of(Moon(Position(2, 0, 4), Velocity(1, -1, -1)), 6)
    )

    @ParameterizedTest
    @MethodSource("moonPotentialEnergyParameters")
    fun moonPotentialEnergy(moon: Moon, expected: Int) {
        assertEquals(expected, moon.potentialEnergy)
    }

    @TestParameters
    private fun moonTotalEnergyParameters() = Stream.of(
        Arguments.of(Moon(Position(2, 1, -3), Velocity(-3, -2, 1)), 36),
        Arguments.of(Moon(Position(1, -8, 0), Velocity(-1, 1, 3)), 45),
        Arguments.of(Moon(Position(3, -6, 1), Velocity(3, 2, -3)), 80),
        Arguments.of(Moon(Position(2, 0, 4), Velocity(1, -1, -1)), 18)
    )

    @ParameterizedTest
    @MethodSource("moonTotalEnergyParameters")
    fun moonTotalEnergy(moon: Moon, expected: Int) {
        assertEquals(expected, moon.totalEnergy)
    }

    @Test
    fun positionPlusVelocity() {
        assertEquals(Moon(Position(-1, 2, 6)), Moon(Position(1, 2, 3) + Velocity(-2, 0, 3)))
    }

    @TestParameters
    private fun systemFindRepeatParameters() = Stream.of(
        Arguments.of(
            listOf(
                Moon(Position(-1, 0, 2)),
                Moon(Position(2, -10, -7)),
                Moon(Position(4, -8, 8)),
                Moon(Position(3, 5, -1))
            ),
            2772
        ),
        Arguments.of(
            listOf(
                Moon(Position(-8, -10, 0)),
                Moon(Position(5, 5, 10)),
                Moon(Position(2, -7, 3)),
                Moon(Position(9, -8, -3))
            ),
            4686774924
        )
    )

    @ParameterizedTest
    @MethodSource("systemFindRepeatParameters")
    fun systemFindRepeat(moons: List<Moon>, expected: Long) {
        val system = System(moons)

        assertEquals(expected, system.findRepeat())
    }

    private val systemTicks = listOf(
        System(
            listOf(
                Moon(Position(-1, 0, 2), Velocity(0, 0, 0)),
                Moon(Position(2, -10, -7), Velocity(0, 0, 0)),
                Moon(Position(4, -8, 8), Velocity(0, 0, 0)),
                Moon(Position(3, 5, -1), Velocity(0, 0, 0))
            )
        ),
        System(
            listOf(
                Moon(Position(2, -1, 1), Velocity(3, -1, -1)),
                Moon(Position(3, -7, -4), Velocity(1, 3, 3)),
                Moon(Position(1, -7, 5), Velocity(-3, 1, -3)),
                Moon(Position(2, 2, 0), Velocity(-1, -3, 1))
            )
        ),
        System(
            listOf(
                Moon(Position(5, -3, -1), Velocity(3, -2, -2)),
                Moon(Position(1, -2, 2), Velocity(-2, 5, 6)),
                Moon(Position(1, -4, -1), Velocity(0, 3, -6)),
                Moon(Position(1, -4, 2), Velocity(-1, -6, 2))
            )
        ),
        System(
            listOf(
                Moon(Position(5, -6, -1), Velocity(0, -3, 0)),
                Moon(Position(0, 0, 6), Velocity(-1, 2, 4)),
                Moon(Position(2, 1, -5), Velocity(1, 5, -4)),
                Moon(Position(1, -8, 2), Velocity(0, -4, 0))
            )
        ),
        System(
            listOf(
                Moon(Position(2, -8, 0), Velocity(-3, -2, 1)),
                Moon(Position(2, 1, 7), Velocity(2, 1, 1)),
                Moon(Position(2, 3, -6), Velocity(0, 2, -1)),
                Moon(Position(2, -9, 1), Velocity(1, -1, -1))
            )
        ),
        System(
            listOf(
                Moon(Position(-1, -9, 2), Velocity(-3, -1, 2)),
                Moon(Position(4, 1, 5), Velocity(2, 0, -2)),
                Moon(Position(2, 2, -4), Velocity(0, -1, 2)),
                Moon(Position(3, -7, -1), Velocity(1, 2, -2))
            )
        ),
        System(
            listOf(
                Moon(Position(-1, -7, 3), Velocity(0, 2, 1)),
                Moon(Position(3, 0, 0), Velocity(-1, -1, -5)),
                Moon(Position(3, -2, 1), Velocity(1, -4, 5)),
                Moon(Position(3, -4, -2), Velocity(0, 3, -1))
            )
        ),
        System(
            listOf(
                Moon(Position(2, -2, 1), Velocity(3, 5, -2)),
                Moon(Position(1, -4, -4), Velocity(-2, -4, -4)),
                Moon(Position(3, -7, 5), Velocity(0, -5, 4)),
                Moon(Position(2, 0, 0), Velocity(-1, 4, 2))
            )
        ),
        System(
            listOf(
                Moon(Position(5, 2, -2), Velocity(3, 4, -3)),
                Moon(Position(2, -7, -5), Velocity(1, -3, -1)),
                Moon(Position(0, -9, 6), Velocity(-3, -2, 1)),
                Moon(Position(1, 1, 3), Velocity(-1, 1, 3))
            )
        ),
        System(
            listOf(
                Moon(Position(5, 3, -4), Velocity(0, 1, -2)),
                Moon(Position(2, -9, -3), Velocity(0, -2, 2)),
                Moon(Position(0, -8, 4), Velocity(0, 1, -2)),
                Moon(Position(1, 1, 5), Velocity(0, 0, 2))
            )
        ),
        System(
            listOf(
                Moon(Position(2, 1, -3), Velocity(-3, -2, 1)),
                Moon(Position(1, -8, 0), Velocity(-1, 1, 3)),
                Moon(Position(3, -6, 1), Velocity(3, 2, -3)),
                Moon(Position(2, 0, 4), Velocity(1, -1, -1))
            )
        )
    )

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
    fun systemTick(index: Int) {
        val initialSystem = systemTicks[index - 1]
        val expectedSystem = systemTicks[index]

        assertEquals(expectedSystem, initialSystem.tick())
    }

    @Test
    fun systemTotalEnergy() {
        val system = System(
            listOf(
                Moon(Position(2, 1, -3), Velocity(-3, -2, 1)),
                Moon(Position(1, -8, 0), Velocity(-1, 1, 3)),
                Moon(Position(3, -6, 1), Velocity(3, 2, -3)),
                Moon(Position(2, 0, 4), Velocity(1, -1, -1))
            )
        )

        assertEquals(179, system.totalEnergy)
    }

    @Test
    fun systemTotalEnergy2() {
        val system = System(
            listOf(
                Moon(Position(-8, -10, 0)),
                Moon(Position(5, 5, 10)),
                Moon(Position(2, -7, 3)),
                Moon(Position(9, -8, -3))
            )
        )

        assertEquals(1940, system.ticks(100).totalEnergy)
    }
}