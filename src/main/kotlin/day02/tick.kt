package day02

fun tick(state: State): State? {
    val opcode = state.memory[state.pc]

    return when (opcode) {
        1 -> with(state) {
            poke(peek(pc + 3), peek(peek(pc + 1)) + peek(peek(pc + 2)))
            advancePc()
        }
        2 -> with(state) {
            poke(peek(pc + 3), peek(peek(pc + 1)) * peek(peek(pc + 2)))
            advancePc()
        }
        99 -> null
        else -> throw IllegalArgumentException("Unknown opcode; $opcode")
    }
}