package oac.day6

import oac.util.Point
import oac.util.getResourceAsText

val txtInput = getResourceAsText("/day6/input.txt")!!
val listInput = txtInput.split("\n")
    .map(String::trim)
    .filter { it.isNotEmpty() }


val rotationMatrix = arrayOf(
    intArrayOf(0, -1),
    intArrayOf(1, 0)
)

const val obstacle = '#'

fun rotate90Right(point: Point): Point {
    val newX = rotationMatrix[0][0] * point.x + rotationMatrix[0][1] * point.y
    val newY = rotationMatrix[1][0] * point.x + rotationMatrix[1][1] * point.y
    return Point(newX, newY)
}

fun findGuard(): Point {
    for (y in listInput.indices) {
        for (x in listInput[y].indices) {
            if (listInput[y][x] == '^') {
                return Point(x, y)
            }
        }
    }
    return Point(listInput[0].length / 2, listInput.size / 2)
}

fun boundsCheck(p: Point, inputList: List<String> = listInput): Boolean {
    return p.x >= 0 && p.x < inputList[0].length && p.y >= 0 && p.y < inputList.size
}

fun part1() {
    val visited = mutableSetOf<Point>()
    var currentPos = findGuard()
    var currentDir = Point(0, -1)

    while (boundsCheck(currentPos)) {
        visited.add(currentPos)
        var nextPos = currentPos + currentDir
        if (boundsCheck(nextPos) && listInput[nextPos.y][nextPos.x] == obstacle) {
            currentDir = rotate90Right(currentDir)
        } else {
            currentPos = nextPos
        }
    }
    println(visited.size)
}


fun main() {
    part1()
}