package day04

class Day04 {
    companion object {
        fun digits(value: Int): List<Int> = value.toString().map(Char::toString).map(Integer::parseInt)

        fun hasRepeat(digits: List<Int>) = digits.windowed(2).find { it.first() == it.last() } != null

        fun neverDecreases(digits: List<Int>) = digits.windowed(2).all { it.last() >= it.first() }

        fun isValid(value: Int) = digits(value).let { hasRepeat(it) && neverDecreases(it) }

        fun hasRepeatStrict(digits: List<Int>) =
            digits.windowed(2).find { (first, last) ->
                first == last && digits.windowed(3).find { (innerFirst, innerMiddle, innerLast) ->
                    first == innerFirst && first == innerMiddle && first == innerLast
                } == null
            } != null

        fun isValidStrict(value: Int) = digits(value).let { hasRepeatStrict(it) && neverDecreases(it) }
    }
}

fun main() {
    val range = 124075..580769

    println(range.count(Day04.Companion::isValid))
    println(range.count(Day04.Companion::isValidStrict))
}
