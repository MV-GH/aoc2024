package oac.day1

import oac.util.getResourceAsText
import kotlin.math.abs

var inputTxt = getResourceAsText("/day1/input-day-1.txt")!!

fun main() {
    part2()
}


fun part1() {
    val listInput = inputTxtToInputList()
    val listLeft = listInput.map { it[0] }.sorted()
    val listRight = listInput.map { it[1] }.sorted()
    var sumDiff = 0
    for (i in listLeft.indices) {
        val left = listLeft[i]
        val right = listRight[i]
        sumDiff += abs(right - left)
    }
    println(sumDiff)
}

fun part2() {
    val listInput = inputTxtToInputList()
    val listLeft = listInput.map { it[0] }.sorted()
    val freqTblRight = listInput.map { it[1] }.groupingBy { it }.eachCount()
    var score = 0

    listLeft.forEach { el ->
        score += freqTblRight.getOrElse(el, { 0 }) * el
    }
    println(score)
}

fun inputTxtToInputList(): List<List<Int>> {
    return inputTxt.split("\n")
        .map(String::trim)
        .filter { it.isNotEmpty() }
        .map { line ->
            val (num1, num2) = line.split("   ").map { it.toInt() }
            listOf(num1, num2)
        }
}