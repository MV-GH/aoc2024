package oac.day6

import oac.util.Point


fun main() {
    part2()
}

data class DirPoint(val point: Point, val dir: Point)

fun part2() {
    // Keep track of visited points and directions, detect loops
    val visited = mutableSetOf<DirPoint>()
    // Keep track of Guard position, prevent obstacles from being placed there
    val visitedPos = mutableSetOf<Point>()
    var currentPos = findGuard()
    var currentDir = Point(0, -1)
    var loopsFound = mutableListOf<Point>()

    while (boundsCheck(currentPos)) {
        var nextPos = currentPos + currentDir

        if (boundsCheck(nextPos) && listInput[nextPos.y][nextPos.x] == obstacle) {
            currentDir = rotate90Right(currentDir)
        } else {
            if (boundsCheck(nextPos) && !visitedPos.contains(nextPos) && wouldLoopIfObstacleFound(currentPos, currentDir, visited)) {
                loopsFound.add(nextPos)
            }
            visitedPos.add(currentPos)
            visited.add(DirPoint(currentPos, currentDir))
            currentPos = nextPos
        }
    }
    println(loopsFound)
    println(loopsFound.size)
}

fun wouldLoopIfObstacleFound(startingPos: Point, facingDir: Point, visited: Set<DirPoint>): Boolean {
    val currentVisited = visited.toMutableSet()
    var currentPos = startingPos
    var currentDir = facingDir
    val adaptedInputList = inputFrameCopy(startingPos + facingDir)

    while (boundsCheck(currentPos, adaptedInputList)) {
        val success = currentVisited.add(DirPoint(currentPos, currentDir))

        var nextPos = currentPos + currentDir
        if (boundsCheck(nextPos, adaptedInputList) && adaptedInputList[nextPos.y][nextPos.x] == obstacle) {
            currentDir = rotate90Right(currentDir)
        } else {
            currentPos = nextPos
        }

        if (!success) {
            return true
        }

    }

    return false
}

// Put an obstacle in the input frame
fun inputFrameCopy(obstaclePos: Point): List<String> {
    val copy = listInput.toMutableList()
    val line = copy[obstaclePos.y]
    val newLine = line.replaceRange(obstaclePos.x, obstaclePos.x + 1, obstacle.toString())
    copy[obstaclePos.y] = newLine

    return copy
}