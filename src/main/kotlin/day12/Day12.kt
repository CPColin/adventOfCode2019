package day12

import kotlin.math.abs
import kotlin.math.sign

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

        return copy(velocity = velocity + Velocity(deltaX, deltaY, deltaZ))
    }

    fun applyVelocity() = copy(position = position + velocity)

    fun equalsX(other: Moon) = position.x == other.position.x && velocity.x == other.velocity.x

    fun equalsY(other: Moon) = position.y == other.position.y && velocity.y == other.velocity.y

    fun equalsZ(other: Moon) = position.z == other.position.z && velocity.z == other.velocity.z

    val kineticEnergy = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

    val potentialEnergy = abs(position.x) + abs(position.y) + abs(position.z)

    val totalEnergy = potentialEnergy * kineticEnergy

    companion object {
        fun gravitySign(position1: Int, position2: Int) = (position2 - position1).sign
    }
}

data class System(val moons: List<Moon>) {
    private fun equalsX(other: System) = moons.zip(other.moons).all { (moon1, moon2) -> moon1.equalsX(moon2) }

    private fun equalsY(other: System) = moons.zip(other.moons).all { (moon1, moon2) -> moon1.equalsY(moon2) }

    private fun equalsZ(other: System) = moons.zip(other.moons).all { (moon1, moon2) -> moon1.equalsZ(moon2) }

    fun findRepeat() = lcm(findRepeatX(), findRepeatY(), findRepeatZ())

    private fun findRepeatX() = ticks.indexOfFirst { equalsX(it) } + 1L

    private fun findRepeatY() = ticks.indexOfFirst { equalsY(it) } + 1L

    private fun findRepeatZ() = ticks.indexOfFirst { equalsZ(it) } + 1L

    fun tick() = copy(
        moons = moons.map { it.applyGravity(moons - it) }.map { it.applyVelocity() }
    )

    fun ticks(count: Int) = ticks.take(count).last()

    private val ticks = generateSequence(this, System::tick).drop(1)

    val totalEnergy = moons.map(Moon::totalEnergy).sum()

    companion object {
        tailrec fun gcd(a: Long, b: Long): Long =
            if (b == 0L) {
                a
            } else {
                gcd(b, a % b)
            }

        fun lcm(vararg values: Long): Long =
            when (values.size) {
                1 -> values[0]
                else -> {
                    val first = values[0]
                    val second = lcm(*values.drop(1).toLongArray())

                    first * second / gcd(first, second)
                }
            }
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