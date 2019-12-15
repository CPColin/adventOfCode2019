package day05

class State(
    val input: Int,

    val memory: MutableList<Int>,

    var output: Int = 0,

    var pc: Int
) {
    fun advancePc(amount: Int) = apply {
        pc += amount
    }

    fun operand(address: Int, parameterMode: ParameterMode) =
        when (parameterMode) {
            ParameterMode.IMMEDIATE -> peek(address)
            ParameterMode.POSITION -> peek(peek(address))
        }

    fun peek(address: Int) = memory[address]

    fun poke(address: Int, value: Int) = apply {
        memory[address] = value
    }
}