package day05

import kotlin.test.Test
import kotlin.test.assertEquals

class StateTests {
    @Test
    fun `operand - immediate`() {
        val state = State(
            input = 123,
            memory = mutableListOf(10, 20, 30, 40, 50, 60),
            pc = 0
        )

        assertEquals(40, state.operand(3, ParameterMode.IMMEDIDATE))
    }

    @Test
    fun `operand - position`() {
        val state = State(
            input = 123,
            memory = mutableListOf(10, 20, 30, 4, 50, 60),
            pc = 0
        )

        assertEquals(50, state.operand(3, ParameterMode.POSITION))
    }

    @Test
    fun peek() {
        val state = State(
            input = 123,
            memory = mutableListOf(10, 20, 30, 40, 50, 60),
            pc = 0
        )

        assertEquals(30, state.peek(2))
    }
}