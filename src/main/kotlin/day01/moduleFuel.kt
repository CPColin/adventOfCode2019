package day01

fun moduleFuel(moduleMass: Int): Int = generateSequence(moduleMass) {
    if (it > 0) {
        massFuel(it)
    } else {
        null
    }
}
    .drop(1)
    .also { println(it.toList()) }
    .sum()