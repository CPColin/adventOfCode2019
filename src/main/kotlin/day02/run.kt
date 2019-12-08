package day02

fun run(state: State) = generateSequence(state) { tick(state) }