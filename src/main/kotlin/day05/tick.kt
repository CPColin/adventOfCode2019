package day05

fun tick(state: State): State? {
    val operation = state.peek(state.pc)
    val opcode = operation % 100
    val parameterModes = parameterModes(operation)

    fun parameterMode(operand: Int) = parameterModes[operand] ?: ParameterMode.POSITION

    return when (opcode) {
        1 -> with(state) {
            poke(peek(pc + 3), operand(pc + 1, parameterMode(0)) + operand(pc + 2, parameterMode(1)))
            advancePc(4)
        }
        2 -> with(state) {
            poke(peek(pc + 3), operand(pc + 1, parameterMode(0)) * operand(pc + 2, parameterMode(1)))
            advancePc(4)
        }
        3 -> with(state) {
            poke(peek(pc + 1), input)
            advancePc(2)
        }
        4 -> with(state) {
            output = operand(pc + 1, parameterMode(0))
            advancePc(2)
        }
        99 -> null
        else -> throw IllegalArgumentException("Unknown opcode: $opcode")
    }
}