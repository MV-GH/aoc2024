package oac.day7

import oac.util.getResourceAsText

val txtInput = getResourceAsText("/day7/input.txt")!!

val inputList = txtInput.split("\n")
    .map(String::trim)
    .filter { it.isNotEmpty() }
    .map {
        val (total, rest) = it.split(": ")
        Pair(total.toLong(), rest.split(" ").map(String::trim).map(String::toLong))
    }

fun generateCombinations(number: Int, operations: List<Char>): Sequence<List<Char>> = sequence {
    if (number == 0) {
        yield(emptyList())
    } else {
        val previousCombinations = generateCombinations(number - 1, operations)
        for (combination in previousCombinations) {
            for (operation in operations) {
                yield(combination + operation)
            }
        }
    }
}

fun isValidSequence(sequence: Pair<Long, List<Long>>, operators: List<Char>) : Boolean {
    val total = sequence.first
    val numbers = sequence.second
    val operations = numbers.size - 1

    return generateCombinations(operations, operators)
        .map { combination ->
            var result = numbers[0]
            for (i in 0 until operations) {
                val operation = combination[i]
                val number = numbers[i + 1]

                when (operation) {
                    '|' -> {
                        result = "$result$number".toLong()
                    }
                    '+' -> {
                        result += number
                    }
                    '*' -> {
                        result *= number
                    }
                }
            }
            result
        }
        .any { it == total }
}

fun part1() {
    val operators = listOf('+', '*')
    val total = inputList
        .filter { isValidSequence(it, operators) }
        .sumOf { it.first }

    println(total)
}

fun part2() {
    val operators = listOf('+', '*', '|')
    val total = inputList
        .filter { isValidSequence(it, operators) }
        .sumOf { it.first }

    println(total)
}

fun main() {
    part2()
}