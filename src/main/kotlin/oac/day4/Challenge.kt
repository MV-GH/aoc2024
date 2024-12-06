package oac.day4

import oac.util.getResourceAsText

val txtInput = getResourceAsText("/day4/input.txt")!!

val inputList = txtInput.split("\n")
    .map(String::trim)
    .filter { it.isNotEmpty() }

data class Point(val x: Int, val y: Int)

val directionsNine = listOf(Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0), Point(1, 1), Point(-1, -1), Point(1, -1), Point(-1, 1))

const val findWord = "XMAS"


fun main() {
    part2()
}

fun part1() {
    var count = 0

    for (x in inputList.indices) {
        for (y in inputList[x].indices) {
            if (inputList[x][y] == findWord[0]) {
                count += hasXMASPart1(x, y)
            }
        }
    }
    println(count)
}

fun hasXMASPart1(x: Int, y: Int): Int {
    var count = 0

    for ((dx, dy) in directionsNine) {
        var depth = 0

        do {
            val lookingFor = findWord[depth]
            if (inputList.getOrNull(x + (dx * depth))?.getOrNull(y + (dy * depth)) == lookingFor) {
                depth++
            } else {
                break
            }
        } while (depth < findWord.length)


        if (depth == findWord.length) {
            count++
        }
    }
    return count
}

val cornerDirections = listOf(
    listOf(Point(1, 1), Point(-1, -1)),
    listOf(Point(1, -1), Point(-1, 1)),
)

val solutions = setOf(
    Pair('S', 'M'),
    Pair('M', 'S')
)


fun hasXMASPart2(x: Int, y: Int): Boolean {
    for ((p1, p2) in cornerDirections) {
        val foundP1 = inputList.getOrNull(x + p1.x)?.getOrNull(y + p1.y)
        val foundP2 = inputList.getOrNull(x + p2.x)?.getOrNull(y + p2.y)

        if (!solutions.contains(Pair(foundP1, foundP2))) {
            return false
        }
    }
    return true
}

fun part2() {
    var count = 0

    for (x in inputList.indices) {
        for (y in inputList[x].indices) {
            if (inputList[x][y] == 'A' && hasXMASPart2(x, y)) {
                count += 1
            }
        }
    }
    println(count)
}