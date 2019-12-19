package day09

class Intcode(
    var halted: Boolean = false,

    val inputs: MutableList<Long>,

    val memory: MutableList<Long>,

    var output: Long? = null,

    var pc: Long
) {
    constructor(
        inputs: List<Long>,
        memory: String,
        output: Long? = null,
        pc: Long
    ) : this(
        inputs = inputs.toMutableList(),
        memory = memory.split(",").map(java.lang.Long::parseLong).toMutableList(),
        output = output,
        pc = pc
    )

    constructor(
        input: Long = 0L,
        memory: String,
        output: Long? = null,
        pc: Long
    ) : this(
        inputs = mutableListOf(input),
        memory = memory.split(",").map(java.lang.Long::parseLong).toMutableList(),
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

    fun operand(address: Long, parameterMode: ParameterMode) =
        when (parameterMode) {
            ParameterMode.IMMEDIATE -> peek(address)
            ParameterMode.POSITION -> peek(peek(address))
        }

    fun peek(address: Long) = memory[address.toInt()]

    fun poke(address: Long, value: Long) = apply {
        memory[address.toInt()] = value
    }

    fun runUntilHalt() {
        do {
            tick()
        } while (!halted)
    }

    fun runUntilOutput() {
        output = null

        do {
            tick()
        } while (!halted && output == null)
    }

    fun tick() {
        val operation = peek(pc)
        val opcode = operation % 100
        val parameterModes = parameterModes(operation)

        fun parameterMode(operand: Int) = parameterModes[operand] ?: ParameterMode.POSITION

        when (opcode.toInt()) {
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
                if (operand(pc + 1, parameterMode(0)) != 0L) {
                    pc = operand(pc + 2, parameterMode(1))
                } else {
                    advancePc(3)
                }
            }
            6 -> { // jump if false
                if (operand(pc + 1, parameterMode(0)) == 0L) {
                    pc = operand(pc + 2, parameterMode(1))
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
            99 -> {
                halted = true
            }
            else -> throw IllegalArgumentException("Unknown opcode: $opcode")
        }
    }

    companion object {
        fun digits(value: Long): List<Int> = value.toString().map(Char::toString).map(Integer::parseInt)

        fun parameterModes(operation: Long) =
            digits(operation)
                .reversed()
                .drop(2)
                .mapIndexed { index, digit -> index to ParameterMode.values()[digit] }
                .toMap()
    }
}