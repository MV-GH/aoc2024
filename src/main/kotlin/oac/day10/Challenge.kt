package oac.day10

import oac.util.Node
import oac.util.Point
import oac.util.getResourceAsText
import kotlin.text.digitToInt

val txtInput = getResourceAsText("/day10/input.txt")!!

private val heightMatrix = txtInput.split("\n")
    .map(String::trim)
    .filter { it.isNotEmpty() }
    .map {
        it.map(Char::digitToInt)
    }

private data class TrailPoint(val position: Point, val height: Int)


private fun constructTree(position: Point, inputMatrix: List<List<Int>>): Node<TrailPoint> {
    val current = inputMatrix[position.y][position.x]
    if (current != 0) {
        throw IllegalStateException("This should not happen")
    }

    val root = Node(TrailPoint(position, current))

    val queue = ArrayDeque<Node<TrailPoint>>(listOf(root))

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        for (direction in Point.directionsFour) {
            val newPosition = currentNode.data.position + direction
            if (newPosition.x in inputMatrix[0].indices && newPosition.y in inputMatrix.indices) {
                val newValue = inputMatrix[newPosition.y][newPosition.x]
                if (newValue == currentNode.data.height + 1) {
                    val newNode = Node(TrailPoint(newPosition, newValue))
                    currentNode.addChild(newNode)
                    queue.add(newNode)
                }
            }
        }
    }

    return root
}

private fun scoreTree(root: Node<TrailPoint>): Int {
    val queue = ArrayDeque<Node<TrailPoint>>(listOf(root))
    var score = 0
    val visited = mutableSetOf<Point>()

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        val children = currentNode.children
        if (currentNode.data.height == 9 && !visited.contains(currentNode.data.position)) {
            score += 1
            visited.add(currentNode.data.position)
        }
        if (children.isNotEmpty()) {
            queue.addAll(children)
        }
    }

    return score
}

// TODO part2
private fun rateTree(root: Node<TrailPoint>): Int {
    val queue = ArrayDeque<Node<TrailPoint>>(listOf(root))
    var rating = 0
    val visited = mutableSetOf<Point>()

//    while (queue.isNotEmpty()) {
//        val currentNode = queue.removeFirst()
//        val children = currentNode.children
//        if (currentNode.data.height == 9 && !visited.contains(currentNode.data.position)) {
//            rating += 1
//            visited.add(currentNode.data.position)
//        }
//        if (children.isNotEmpty()) {
//            queue.addAll(children)
//        }
//    }

    return rating
}

fun part1() {
    var score = 0
    for (y in heightMatrix.indices) {
        for (x in heightMatrix[y].indices) {
            val height = heightMatrix[y][x]
            if (height == 0) {
                val tree = constructTree(Point(x, y), heightMatrix)
                score += scoreTree(tree)
            }
        }
    }
    println(score)
}



fun main() {
    part1()
}