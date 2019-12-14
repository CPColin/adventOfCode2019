package day05

fun tick(state: State): State? {
    val operation = state.peek(state.pc)
    val opcode = operation % 100
    val parameterModes = parameterModes(operation)

    fun parameterMode(operand: Int) = parameterModes[operand] ?: ParameterMode.POSITION

    return when (opcode) {
        1 -> with(state) { // add
            poke(peek(pc + 3), operand(pc + 1, parameterMode(0)) + operand(pc + 2, parameterMode(1)))
            advancePc(4)
        }
        2 -> with(state) { // multiply
            poke(peek(pc + 3), operand(pc + 1, parameterMode(0)) * operand(pc + 2, parameterMode(1)))
            advancePc(4)
        }
        3 -> with(state) { // input
            poke(peek(pc + 1), input)
            advancePc(2)
        }
        4 -> with(state) { // output
            output = operand(pc + 1, parameterMode(0))
            advancePc(2)
        }
        5 -> with(state) { // jump if true
            if (operand(pc + 1, parameterMode(0)) != 0) {
                pc = operand(pc + 2, parameterMode(1))
                state
            } else {
                advancePc(3)
            }
        }
        6 -> with(state) { // jump if false
            if (operand(pc + 1, parameterMode(0)) == 0) {
                pc = operand(pc + 2, parameterMode(1))
                state
            } else {
                advancePc(3)
            }
        }
        7 -> with(state) { // less than
            poke(peek(pc + 3), if (operand(pc + 1, parameterMode(0)) < operand(pc + 2, parameterMode(1))) 1 else 0)
            advancePc(4)
        }
        8 -> with(state) { // equals
            poke(peek(pc + 3), if (operand(pc + 1, parameterMode(0)) == operand(pc + 2, parameterMode(1))) 1 else 0)
            advancePc(4)
        }
        99 -> null
        else -> throw IllegalArgumentException("Unknown opcode: $opcode")
    }
}