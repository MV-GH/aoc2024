package oac.day9

import oac.util.getResourceAsText

private val txtInput = getResourceAsText("/day9/input.txt")!!

fun checksum(id: Int, from: Int, to: Int): Long {
    var sum = 0L
    for (i in from until to) {
        sum += i * id
    }
    return sum
}

fun indexToId(index: Int): Int {
    return index / 2
}

private fun moveInputRec(amount: Int, index: Int, input: MutableList<Int>): Long {
    val current = input.last()
    val id = indexToId(input.size - 1)
    if (current == amount) {
        input.removeLast()
        input.removeLast() // Empty space
        return checksum(id, index, index + amount)
    } else if (current > amount) {
        input[input.size - 1] = current - amount
        return checksum(id, index, index + amount)
    } else {
        var moved = 0
        var checksumTotal = 0L
        while (moved < amount) {
            val current = input.last()
            val sent = (amount - moved).coerceAtMost(current)
            checksumTotal += moveInputRec(sent, index + moved, input)
            moved += sent
        }
        return checksumTotal
    }
}

fun part1() {
    var input = txtInput.split("").filter { it.isNotBlank() }.map(String::toInt).toMutableList()
    var checksum = 0L

    var i = 0
    var id = 0
    var filePosition = 0
    while (i < input.size) {
        val current = input[i]
        val next = input.getOrNull(i + 1) ?: 0
        checksum += checksum(id, filePosition, filePosition + current)
        filePosition += current
        if (next != 0) {
            checksum += moveInputRec(next, filePosition, input)
            filePosition += next
        }
        i += 2
        id++
    }
    println(checksum)
}

fun main() {
    part1()
}