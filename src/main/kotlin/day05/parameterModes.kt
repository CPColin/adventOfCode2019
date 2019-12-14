package day05

import day04.Day04

fun parameterModes(operation: Int) =
    Day04.digits(operation)
        .reversed()
        .drop(2)
        .mapIndexed { index, digit -> index to ParameterMode.values()[digit] }
        .toMap()