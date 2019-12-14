package day05

fun run(state: State) = generateSequence(state) { tick(state) }