package day14

data class Reactant(val chemical: String, val quantity: Int) {
    companion object {
        fun fromString(string: String): Reactant {
            val (quantity, chemical) = string.split(" ")

            return Reactant(chemical, Integer.parseInt(quantity))
        }
    }
}

data class Reaction(val inputs: List<Reactant>, val outputs: List<Reactant>) {
    companion object {
        fun fromString(string: String): Reaction {
            val (inputString, outputString) = string.split("=>")
            val inputs = inputString.split(",").map { Reactant.fromString(it.trim()) }
            val outputs = outputString.split(",").map { Reactant.fromString(it.trim()) }

            return Reaction(inputs, outputs)
        }
    }
}
