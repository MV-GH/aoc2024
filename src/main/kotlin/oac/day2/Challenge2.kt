package oac.day2

import oac.util.getResourceAsText
import kotlin.math.abs

val inputTxt = getResourceAsText("/day2/input.txt")!!


fun main() {
    part2()
}

fun part2() {
    val alrSafe = inputTxtToList()
        .map(::isSafeP1)
        .count { it }


    val alsoSafe = inputTxtToList()
        .filter { !isSafeP1(it) }
        .map(::explodeToOptions)
        .map { it.map(::isSafeP1).count { it } }
        .count { it > 0 }

    println(alrSafe + alsoSafe)
}

fun part1() {
    inputTxtToList()
        .map(::isSafeP1)
        .count { it }
        .let(::println)
}


fun inputTxtToList(): List<List<Int>> {
    return inputTxt.split("\n")
        .map(String::trim)
        .filter { it.isNotEmpty() }
        .map { line ->
            line.split(" ")
                .map(Integer::parseInt)
                .toList()
        }
}


fun isSafeP1(inList: List<Int>): Boolean {
    var natOrder = true
    var reverseOrder = true
    for (i in 0 until inList.size - 1) {
        if (abs(inList[i] - inList[i + 1]) > 3) {
            return false
        }
        if (inList[i] == inList[i + 1]) {
            return false
        }
        if (inList[i] > inList[i + 1]) {
            natOrder = false
        }
        if (inList[i] < inList[i + 1]) {
            reverseOrder = false
        }
    }
    return natOrder || reverseOrder
}

fun explodeToOptions(input: List<Int>): List<List<Int>> {
    val options = mutableListOf(input)

    for (i in 0 until input.size) {
        val mutList = input.toMutableList()

        mutList.removeAt(i)
        options.add(mutList)
    }

    return options
}