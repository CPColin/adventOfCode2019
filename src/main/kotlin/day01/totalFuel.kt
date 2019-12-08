package day01

fun totalFuel(moduleMasses: Iterable<Int>): Int = moduleMasses.map(::moduleFuel).sum()