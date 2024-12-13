package oac.day12

import oac.util.Point
import oac.util.PosNode
import oac.util.getResourceAsText

val inputTxt = getResourceAsText("/day12/input.txt")!!

val inputList = inputTxt.split("\n")
    .map(String::trim)
    .filter { it.isNotEmpty() }


private fun constructTree(position: Point, input: List<String>, visited: MutableSet<Point>): PosNode<Char> {
    val current = input[position.y][position.x]
    val root = PosNode(current, position)

    val queue = ArrayDeque<PosNode<Char>>(listOf(root))

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        for (direction in Point.directionsFour) {
            val newPosition = currentNode.position + direction
            if (newPosition.x in input[0].indices && newPosition.y in input.indices) {
                val newValue = input[newPosition.y][newPosition.x]
                if (newValue == currentNode.data && !visited.contains(newPosition)) {
                    val newNode = PosNode(newValue, newPosition)
                    currentNode.addChild(newNode)
                    queue.add(newNode)
                    visited.add(newPosition)
                }
            }
        }
    }

    return root
}

private fun constructGraph(position: Point, input: List<String>, visited: MutableSet<Point>): PosNode<Char> {
    val visitedNodeMap = mutableMapOf<Point, PosNode<Char>>()
    val current = input[position.y][position.x]
    val root = PosNode(current, position)
    visitedNodeMap[position] = root

    val queue = ArrayDeque<PosNode<Char>>(listOf(root))

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        for (direction in Point.directionsFour) {
            val newPosition = currentNode.position + direction
            if (newPosition.x in input[0].indices && newPosition.y in input.indices) {
                val newValue = input[newPosition.y][newPosition.x]
                if (newValue != currentNode.data) {
                    continue
                } else if (!visitedNodeMap.containsKey(newPosition)) {
                    val newNode = PosNode(newValue, newPosition)
                    currentNode.addChild(newNode)
                    queue.add(newNode)
                    visitedNodeMap[newPosition] = newNode
                } else {
                    val existingNode = visitedNodeMap[newPosition]!!
                    currentNode.addChild(existingNode)
                }
            }
        }
    }

    visited.addAll(visitedNodeMap.keys)
    return root
}

private fun calcArea(root: PosNode<Char>): Int {
    val queue = ArrayDeque<PosNode<Char>>(listOf(root))
    val visited = mutableSetOf<Point>()

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        val children = currentNode.children
        if (!visited.contains(currentNode.position)) {
            visited.add(currentNode.position)
            if (children.isNotEmpty()) {
                queue.addAll(children)
            }
        }

    }

    return visited.size
}

private fun calcPerimeter(rootGraph: PosNode<Char>): Int {
    val queue = ArrayDeque<PosNode<Char>>(listOf(rootGraph))
    val visited = mutableSetOf<Point>()
    var borders = 0

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()

        if (!visited.contains(currentNode.position)) {
            val children = currentNode.children
            visited.add(currentNode.position)
            if (children.isNotEmpty()) {
                queue.addAll(children)
            }
            borders += 4 - children.size
        }
    }

    return borders
}

private fun calcSides(rootGraph: PosNode<Char>): Int {
    val queue = ArrayDeque<PosNode<Char>>(listOf(rootGraph))
    val visited = mutableSetOf<Point>()
    var corners = 0 // #sides == #corners

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()

        if (!visited.contains(currentNode.position)) {
            val children = currentNode.children
            visited.add(currentNode.position)
            if (children.isNotEmpty()) {
                queue.addAll(children)
            }
        }
    }

    for (point in visited) {
        if (point + Point.UP !in visited && point + Point.RIGHT !in visited) {
            corners += 1
        }
        if (point + Point.RIGHT !in visited && point + Point.DOWN !in visited) {
            corners += 1
        }
        if (point + Point.DOWN !in visited && point + Point.LEFT !in visited) {
            corners += 1
        }
        if (point + Point.LEFT !in visited && point + Point.UP !in visited) {
            corners += 1
        }

        if (point + Point.UP_LEFT !in visited && point + Point.UP in visited && point + Point.LEFT in visited) {
            corners += 1
        }
        if (point + Point.UP_RIGHT !in visited && point + Point.UP in visited && point + Point.RIGHT in visited) {
            corners += 1
        }
        if (point + Point.DOWN_LEFT !in visited && point + Point.DOWN in visited && point + Point.LEFT in visited) {
            corners += 1
        }
        if (point + Point.DOWN_RIGHT !in visited && point + Point.DOWN in visited && point + Point.RIGHT in visited) {
            corners += 1
        }

    }
    return corners
}


fun part1() {
    val visited = mutableSetOf<Point>()
    var cost = 0

    for (y in inputList.indices) {
        for (x in inputList[y].indices) {
            val position = Point(x, y)
            if (!visited.contains(position)) {
                val root = constructGraph(position, inputList, visited)
                val size = calcArea(root)
                val perimeter = calcPerimeter(root)
                cost += size * perimeter
                println("Region: ${root.data}: $size, Perimeter: $perimeter, cost: ${size * perimeter}")
            }
        }
    }
    println(cost)
}

fun part2() {
    val visited = mutableSetOf<Point>()
    var cost = 0

    for (y in inputList.indices) {
        for (x in inputList[y].indices) {
            val position = Point(x, y)
            if (!visited.contains(position)) {
                val root = constructGraph(position, inputList, visited)
                val size = calcArea(root)
                val sides = calcSides(root)
                cost += size * sides
                println("Region: ${root.data}, Size: $size, sides: $sides, cost: ${size * sides}")
            }
        }
    }
    println(cost)
}

fun main() {
    part2()
}