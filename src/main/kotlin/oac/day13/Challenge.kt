package oac.day13

import oac.util.VectorL
import oac.util.getResourceAsText


val inputTxt = getResourceAsText("/day13/input.txt")!!
val rgx = Regex("""A: X\+(\d+), Y\+(\d+)\s+Button B: X\+(\d+), Y\+(\d+)\s+Prize: X=(\d+), Y=(\d+)""")

data class Config(val buttonA: VectorL, val buttonB: VectorL, val prize: VectorL)
private data class Solution(val buttonA: Long, val buttonB: Long)

private fun parseInput(input: String): List<Config> {
    return rgx.findAll(input).map {
        val (aX, aY, bX, bY, pX, pY) = it.destructured
        Config(VectorL(aX.toLong(), aY.toLong()), VectorL(bX.toLong(), bY.toLong()), VectorL(pX.toLong(), pY.toLong()))
    }.toList()
}

val inputList = parseInput(inputTxt)

private fun hasFractionalPart(number: Double): Boolean {
    return number % 1.0 != 0.0
}

// Cramer's rule
private fun solveConfig(config: Config, part2: Boolean = false): Solution? {
    val (aX, aY) = config.buttonA
    val (bX, bY) = config.buttonB
    val (pX, pY) = if (part2) config.prize + VectorL(10000000000000L, 10000000000000L) else config.prize

    val determinant = aX * bY - aY * bX
    if (determinant == 0L) return null // No solution or infinite solutions

    val a = (pX * bY - pY * bX) / determinant.toDouble()
    val b = (aX * pY - aY * pX) / determinant.toDouble()

    return if (a >= 0 && b >= 0 && !hasFractionalPart(a) && !hasFractionalPart(b))
        Solution(a.toLong(), b.toLong())
    else
        null
}

fun part1() {
    val chips = inputList
        .map(::solveConfig)
        .filter { it != null }
        .sumOf { it!!.buttonA * 3 + it.buttonB }
    println(chips)
}

fun part2() {
    val chips = inputList
        .map {solveConfig(it, true)}
        .filter { it != null }
        .sumOf { it!!.buttonA * 3 + it.buttonB }
    println(chips)
}

fun main() {
    part2()
}