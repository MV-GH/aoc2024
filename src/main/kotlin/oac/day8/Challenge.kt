package oac.day8

import oac.util.Point
import oac.util.getResourceAsText

val txtInput = getResourceAsText("/day8/input.txt")!!

val inputList = txtInput.split("\n")
    .map(String::trim)
    .filter { it.isNotEmpty() }

fun getAntennas(): Map<Char, List<Point>> {
    val antennas = mutableMapOf<Char, MutableList<Point>>()

    for (y in inputList.indices) {
        val line = inputList[y]
        for (x in line.indices) {
            val c = line[x]
            if (c != '.') {
                antennas
                    .getOrPut(c) { mutableListOf<Point>() }
                    .add(Point(x, y))
            }
        }
    }

    return antennas
}

fun withinBounds(point: Point, fieldSizeX: Int, fieldSizeY: Int): Boolean {
    return point.x in 0 until fieldSizeX && point.y in 0 until fieldSizeY
}

fun part1() {
    val antinodes = mutableSetOf<Point>()
    val antennas = getAntennas()
    val fieldSizeX = inputList[0].length
    val fieldSizeY = inputList.size

    for (antennaList in antennas.values) {
        for (i in antennaList.indices) {
            val size = antennaList.size
            val rootAntenna = antennaList[i]
            for (j in 1 until size) {
                val translatedIndex = (i + j) % size
                val comparedAntenna = antennaList[translatedIndex]
                val diff = comparedAntenna - rootAntenna
                val antinodePos = rootAntenna - diff
                if (withinBounds(antinodePos, fieldSizeX, fieldSizeY)) {
                    antinodes.add(antinodePos)
                }
            }
        }
    }
    println(antinodes)
    println(antinodes.size)
}

fun part2() {
    val antinodes = mutableSetOf<Point>()
    val antennas = getAntennas()

    val fieldSizeX = inputList[0].length
    val fieldSizeY = inputList.size

    for (antennaList in antennas.values) {
        for (i in antennaList.indices) {
            val size = antennaList.size
            val rootAntenna = antennaList[i]
            for (j in 1 until size) {
                val translatedIndex = (i + j) % size
                val comparedAntenna = antennaList[translatedIndex]
                val diff = comparedAntenna - rootAntenna

                var antinodePosNeg = rootAntenna - diff
                while (withinBounds(antinodePosNeg, fieldSizeX, fieldSizeY)) {
                    antinodes.add(antinodePosNeg)
                    antinodePosNeg -= diff
                }
                var antinodePos = rootAntenna + diff
                while (withinBounds(antinodePos, fieldSizeX, fieldSizeY)) {
                    antinodes.add(antinodePos)
                    antinodePos += diff
                }
            }
        }
    }
    println(antinodes)
    println(antinodes.size)
}

fun main() {
    part1()
}