package day07

import day04.Day04

class Intcode(
    val inputs: MutableList<Int>,

    val memory: MutableList<Int>,

    var output: Int = 0,

    var pc: Int
) {
    constructor(
        inputs: List<Int>,
        memory: String,
        output: Int = 5432, // useless by default so we don't rely on it being zero
        pc: Int
    ) : this(
        inputs = inputs.toMutableList(),
        memory = memory.split(",").map(Integer::parseInt).toMutableList(),
        output = output,
        pc = pc
    )

    constructor(
        input: Int = 9876, // useless by default so we don't rely on it being zero
        memory: String,
        output: Int = 5432, // useless by default so we don't rely on it being zero
        pc: Int
    ) : this(
        inputs = mutableListOf(input),
        memory = memory.split(",").map(Integer::parseInt).toMutableList(),
        output = output,
        pc = pc
    )

    enum class ParameterMode {
        POSITION,
        IMMEDIATE
    }

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

    fun run() = generateSequence(this) { tick() }

    fun tick(): Intcode? {
        val operation = peek(pc)
        val opcode = operation % 100
        val parameterModes = parameterModes(operation)

        fun parameterMode(operand: Int) = parameterModes[operand] ?: ParameterMode.POSITION

        return when (opcode) {
            1 -> { // add
                poke(peek(pc + 3), operand(pc + 1, parameterMode(0)) + operand(pc + 2, parameterMode(1)))
                advancePc(4)
            }
            2 -> { // multiply
                poke(peek(pc + 3), operand(pc + 1, parameterMode(0)) * operand(pc + 2, parameterMode(1)))
                advancePc(4)
            }
            3 -> { // input
                poke(peek(pc + 1), inputs.removeAt(0))
                advancePc(2)
            }
            4 -> { // output
                output = operand(pc + 1, parameterMode(0))
                advancePc(2)
            }
            5 -> { // jump if true
                if (operand(pc + 1, parameterMode(0)) != 0) {
                    pc = operand(pc + 2, parameterMode(1))
                    this
                } else {
                    advancePc(3)
                }
            }
            6 -> { // jump if false
                if (operand(pc + 1, parameterMode(0)) == 0) {
                    pc = operand(pc + 2, parameterMode(1))
                    this
                } else {
                    advancePc(3)
                }
            }
            7 -> { // less than
                poke(peek(pc + 3), if (operand(pc + 1, parameterMode(0)) < operand(pc + 2, parameterMode(1))) 1 else 0)
                advancePc(4)
            }
            8 -> { // equals
                poke(peek(pc + 3), if (operand(pc + 1, parameterMode(0)) == operand(pc + 2, parameterMode(1))) 1 else 0)
                advancePc(4)
            }
            99 -> null
            else -> throw IllegalArgumentException("Unknown opcode: $opcode")
        }
    }

    companion object {
        fun parameterModes(operation: Int) =
            Day04.digits(operation)
                .reversed()
                .drop(2)
                .mapIndexed { index, digit -> index to ParameterMode.values()[digit] }
                .toMap()
    }
}