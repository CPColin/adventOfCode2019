package day02

class State(
    val memory: MutableList<Int>,

    var pc: Int
) {
    fun advancePc() = apply {
        pc += 4
    }

    fun peek(address: Int) = memory[address]

    fun poke(address: Int, value: Int) = apply {
        memory[address] = value
    }
}