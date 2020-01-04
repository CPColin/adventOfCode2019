package day12

import day12.Day12.Moon
import day12.Day12.Position
import day12.Day12.System
import kotlin.math.abs
import kotlin.math.sign

class Day12 {
    data class Position(val x: Int, val y: Int, val z: Int) {
        operator fun plus(velocity: Velocity) = Position(x + velocity.x, y + velocity.y, z + velocity.z)
    }

    data class Velocity(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Velocity) = Velocity(x + other.x, y + other.y, z + other.z)
    }

    data class Moon(val position: Position, val velocity: Velocity = Velocity(0, 0, 0)) {
        fun applyGravity(otherMoons: List<Moon>): Moon {
            val deltaX = otherMoons.map { gravitySign(position.x, it.position.x) }.sum()
            val deltaY = otherMoons.map { gravitySign(position.y, it.position.y) }.sum()
            val deltaZ = otherMoons.map { gravitySign(position.z, it.position.z) }.sum()

            return copy(velocity = Velocity(velocity.x + deltaX, velocity.y + deltaY, velocity.z + deltaZ))
        }

        fun applyVelocity() = copy(position = position + velocity)

        val kineticEnergy = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

        val potentialEnergy = abs(position.x) + abs(position.y) + abs(position.z)

        val totalEnergy = potentialEnergy * kineticEnergy

        companion object {
            fun gravitySign(position1: Int, position2: Int) = (position2 - position1).sign
        }
    }

    data class System(val moons: List<Moon>) {
        fun findRepeat() = ticks.indexOfFirst { this == it } + 1

        fun tick() = copy(
            moons = moons.map { it.applyGravity(moons - it) }.map { it.applyVelocity() }
        )

        fun ticks(count: Int) = ticks.take(count).last()

        private val ticks = generateSequence(this, System::tick).drop(1)

        val totalEnergy = moons.map(Moon::totalEnergy).sum()
    }
}

fun main() {
    val system = System(
        listOf(
            Moon(Position(4, 12, 13)),
            Moon(Position(-9, 14, -3)),
            Moon(Position(-7, -1, 2)),
            Moon(Position(-11, 17, -1))
        )
    )

    println("Energy after 1000 ticks: ${system.ticks(1000).totalEnergy}")

    println("Repeats after ${system.findRepeat()} ticks")
}