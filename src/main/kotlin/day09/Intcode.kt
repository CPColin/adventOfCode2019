package day09

class Intcode(
    var halted: Boolean = false,

    val inputs: MutableList<Long>,

    val memory: MutableMap<Long, Long>,

    var output: Long? = null,

    var pc: Long,

    var relativeBase: Long
) {
    constructor(
        inputs: List<Long>,
        memory: String,
        output: Long? = null,
        pc: Long = 0,
        relativeBase: Long = 0
    ) : this(
        inputs = inputs.toMutableList(),
        memory = memory.split(",").mapIndexed { address, value ->
            address.toLong() to java.lang.Long.parseLong(value)
        }.toMap().toMutableMap(),
        output = output,
        pc = pc,
        relativeBase = relativeBase
    )

    constructor(
        input: Long = 0,
        memory: String,
        output: Long? = null,
        pc: Long = 0,
        relativeBase: Long = 0
    ) : this(
        inputs = mutableListOf(input),
        memory = memory.split(",").mapIndexed { address, value ->
            address.toLong() to java.lang.Long.parseLong(value)
        }.toMap().toMutableMap(),
        output = output,
        pc = pc,
        relativeBase = relativeBase
    )

    enum class ParameterMode {
        POSITION,
        IMMEDIATE,
        RELATIVE
    }

    fun advancePc(amount: Int) = apply {
        pc += amount
    }

    fun peek(address: Long) = memory[address] ?: 0

    fun peek(operandAddress: Long, parameterMode: ParameterMode) =
        when (parameterMode) {
            ParameterMode.POSITION -> peek(operandAddress)
            ParameterMode.IMMEDIATE -> operandAddress
            ParameterMode.RELATIVE -> peek(operandAddress) + relativeBase
        }.let { address -> peek(address) }

    fun poke(operandAddress: Long, parameterMode: ParameterMode, value: Long) =
        when (parameterMode) {
            ParameterMode.POSITION, ParameterMode.IMMEDIATE -> peek(operandAddress)
            ParameterMode.RELATIVE -> peek(operandAddress) + relativeBase
        }.let { address -> memory[address] = value }

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

        fun parameterMode(operand: Int) = parameterModes[operand - 1] ?: ParameterMode.POSITION
        fun operand(operand: Int) = peek(pc + operand, parameterMode(operand))

        when (opcode.toInt()) {
            1 -> { // add
                poke(
                    operandAddress = pc + 3,
                    parameterMode = parameterMode(3),
                    value = operand(1) + operand(2)
                )
                advancePc(4)
            }
            2 -> { // multiply
                poke(
                    operandAddress = pc + 3,
                    parameterMode = parameterMode(3),
                    value = operand(1) * operand(2)
                )
                advancePc(4)
            }
            3 -> { // input
                poke(
                    operandAddress = pc + 1,
                    parameterMode = parameterMode(1),
                    value = inputs.removeAt(0)
                )
                advancePc(2)
            }
            4 -> { // output
                output = operand(1)
                advancePc(2)
            }
            5 -> { // jump if true
                if (operand(1) != 0L) {
                    pc = operand(2)
                } else {
                    advancePc(3)
                }
            }
            6 -> { // jump if false
                if (operand(1) == 0L) {
                    pc = operand(2)
                } else {
                    advancePc(3)
                }
            }
            7 -> { // less than
                poke(
                    operandAddress = pc + 3,
                    parameterMode = parameterMode(3),
                    value = if (operand(1) < operand(2)) 1 else 0
                )
                advancePc(4)
            }
            8 -> { // equals
                poke(
                    operandAddress = pc + 3,
                    parameterMode = parameterMode(3),
                    value = if (operand(1) == operand(2)) 1 else 0
                )
                advancePc(4)
            }
            9 -> { // relative base offset
                relativeBase += operand(1)
                advancePc(2)
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