fun main() {
    fun part1(input: List<String>): Int {
        data class Direction(val h: Int, val d: Int)
        val displacement = input.fold(Direction(0, 0)) { acc, line ->
            val distance = Character.getNumericValue(line.last())
            when (line.first()) {
                'f' -> Direction(acc.h + distance, acc.d)
                'u' -> Direction(acc.h, acc.d - distance)
                'd' -> Direction(acc.h, acc.d + distance)
                else -> throw IllegalStateException("wrong direction")
            }
        }
        return displacement.h * displacement.d
    }

    fun part2(input: List<String>): Int {
        data class Direction(val h: Int, val d: Int, val aim: Int)
        val displacement = input.fold(Direction(0, 0, 0)) { acc, line ->
            val distance = Character.getNumericValue(line.last())
            when (line.first()) {
                'f' -> Direction(acc.h + distance, acc.d + acc.aim * distance, acc.aim)
                'u' -> Direction(acc.h, acc.d, acc.aim - distance)
                'd' -> Direction(acc.h, acc.d, acc.aim + distance)
                else -> throw IllegalStateException("wrong direction")
            }
        }
        return displacement.h * displacement.d
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
