package oac.day11

import oac.util.getResourceAsText

val txtInput = getResourceAsText("/day11/input.txt")!!

val listInput = txtInput.split(" ").filter(String::isNotBlank).map(String::toLong)

fun countDigits(number: Long): Int {
    return number.toString().length
}

fun mapStone(stone: Long): List<Long> {
    return if (stone == 0L) {
        listOf(1)
    } else if (countDigits(stone) % 2 == 0) {
        val (left, right) = stone.toString().chunked(countDigits(stone) / 2).map(String::toLong)
        listOf(left, right)
    } else {
        listOf(stone * 2024)
    }
}

fun blink(amount: Long, listInput: List<Long>): List<Long> {
    var input = listInput
    for (i in 0 until amount) {
        input = input.flatMap { mapStone(it) }
    }
    return input
}

fun part1() {
    val output = blink(25, listInput)
    println(output)
    println(output.size)
}

// TODO: smarter way to calculate this, OOM currently
fun part2() {
    val output = blink(75, listInput)
    println(output)
    println(output.size)
}

fun main() {
    part1()
}