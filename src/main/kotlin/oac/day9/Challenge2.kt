package oac.day9

import oac.util.getResourceAsText

private val txtInput = getResourceAsText("/day9/input.txt")!!


fun moveInputFile(amount: Int, filePos: Int, startPos: Int, input: MutableList<Int>, movedFiles: MutableSet<Int>): Long {
    val endPos = input.size - 1
    var currentPos = endPos
    var moved = 0
    var checksumTotal = 0L

    while (currentPos > startPos && moved < amount) {
        val current = input[currentPos]

        if (!movedFiles.contains(currentPos) && current <= (amount - moved)) {
            checksumTotal += checksum(indexToId(currentPos), filePos + moved, filePos + current + moved)
            moved += current
            movedFiles.add(currentPos)
        }

        currentPos -= 2
    }
    return checksumTotal
}

fun part2() {
    val input = txtInput.split("").filter { it.isNotBlank() }.map(String::toInt).toMutableList()
    val movedFiles = mutableSetOf<Int>()
    var checksum = 0L

    var i = 0
    var id = 0
    var filePosition = 0
    while (i < input.size) {
        val current = input[i]
        val next = input.getOrNull(i + 1) ?: 0

        if (!movedFiles.contains(i)) {
            checksum += checksum(id, filePosition, filePosition + current)
        }

        filePosition += current
        if (next != 0) {
            checksum += moveInputFile(next, filePosition, i, input, movedFiles)
            filePosition += next
        }
        i += 2
        id++
    }
    println(checksum)
}

fun main() {
    part2()
}