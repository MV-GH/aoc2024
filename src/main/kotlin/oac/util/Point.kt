package oac.util

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    companion object {
        val zero = Point(0, 0)

        val directionsEight = listOf(
            Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0),
            Point(1, 1), Point(-1, -1), Point(1, -1), Point(-1, 1)
        )

        val directionsFour = listOf(
            Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0)
        )
    }
}