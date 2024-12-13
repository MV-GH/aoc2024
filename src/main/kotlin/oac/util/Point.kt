package oac.util

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    companion object {
        val directionsEight = listOf(
            Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0),
            Point(1, 1), Point(-1, -1), Point(1, -1), Point(-1, 1)
        )

        val directionsFour = listOf(
            Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0)
        )

        val ZERO = Point(0, 0)
        val UP = Point(0, -1)
        val DOWN = Point(0, 1)
        val LEFT = Point(-1, 0)
        val RIGHT = Point(1, 0)
        val UP_LEFT = UP + LEFT
        val UP_RIGHT = UP + RIGHT
        val DOWN_LEFT = DOWN + LEFT
        val DOWN_RIGHT = DOWN + RIGHT
    }
}