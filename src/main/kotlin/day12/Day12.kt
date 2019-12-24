package day12

import kotlin.math.sign

class Day12 {
    data class Position(val x: Int, val y: Int, val z: Int) {
        operator fun plus(velocity: Velocity) = Position(x + velocity.x, y + velocity.y, z + velocity.z)
    }

    data class Velocity(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Velocity) = Velocity(x + other.x, y + other.y, z + other.z)
    }

    data class Moon(val label: String, val position: Position, val velocity: Velocity = Velocity(0, 0, 0))

    data class System(val io: Moon, val europa: Moon, val callisto: Moon, val ganymede: Moon)

    companion object {
        fun gravitySign(position1: Int, position2: Int) = (position2 - position1).sign
    }
}