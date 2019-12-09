package day03

data class LineCode(val direction: Direction, val distance: Int) {
    constructor(code: String) : this(Direction.valueOf(code.substring(0, 1)), Integer.parseInt(code.substring(1)))
}