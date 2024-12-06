package oac.day5

import oac.util.getResourceAsText

val inputRulesTxt = getResourceAsText("/day5/input-rules.txt")!!
val inputPagesTxt = getResourceAsText("/day5/input-pages.txt")!!


fun main() {
    part2()
}


fun getRules(): Map<Int, Set<Int>> {
    val mappings = inputRulesTxt.split("\n")
        .map(String::trim)
        .filter { it.isNotEmpty() }
        .map {
            val (x1, x2) = it.split("|")
            Pair(x1.toInt(), x2.toInt())
        }

    val rules = mutableMapOf<Int, MutableSet<Int>>()

    for ((x1, x2) in mappings) {
        rules.getOrPut(x1) { mutableSetOf() }.add(x2)
    }
    return rules
}

fun getPages(): List<List<Int>> {
    return inputPagesTxt.split("\n")
        .map(String::trim)
        .filter { it.isNotEmpty() }
        .map { line ->
            line.split(",")
                .map(String::trim)
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
        }
}

fun part1() {
    val rules = getRules()
    val pages = getPages()

    val sum = pages
        .filter { pageList -> isOrdered(pageList, rules) }
        .sumOf { it[(it.size - 1) / 2].toInt() }
    println(sum)
}


fun isOrdered(page: List<Int>, rules: Map<Int, Set<Int>>): Boolean {
    val list = page.toMutableList()

    while (list.isNotEmpty()) {
        val current = list.removeAt(list.size - 1)
        for (elem in list) {
            if (rules[current]?.contains(elem) == true) {
                return false
            }
        }
    }
    return true
}


fun reorderList(page: List<Int>, rules: Map<Int, Set<Int>>): List<Int> {
    val list = page.toMutableList()

    val orderedList = mutableListOf<Int>()

    outer@ while (list.isNotEmpty()) {
        val current = list.removeAt(list.size - 1)
        for (elem in list) {
            if (rules[current]?.contains(elem) == true) {
                val index = list.indexOf(elem)
                list.add(index, current)
                continue@outer // Skip add
            }
        }
        orderedList.add(current)
    }
    return orderedList.reversed()
}

fun part2() {
    val rules = getRules()
    val pages = getPages()

    val sum = pages
        .filter { pageList -> !isOrdered(pageList, rules) }
        .map { reorderList(it, rules) }
        .sumOf { it[(it.size - 1) / 2].toInt() }

    println(sum)
}

