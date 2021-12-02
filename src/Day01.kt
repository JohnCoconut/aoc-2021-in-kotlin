fun main() {
    fun increasingCount(depths: List<Int>): Int {
        // acc: Pair<Int, Int>
        // acc.first: total increasing count
        // acc.second: previous depth
        return depths.fold (Pair(0, Int.MAX_VALUE)) { acc: Pair<Int, Int>, i ->
            if (i > acc.second) Pair(acc.first + 1, i) else Pair(acc.first, i)
        }.first
    }

    fun part1(input: List<String>): Int {
        val depths = input.map { it.toInt() }
        return increasingCount(depths)
    }

    fun part2(input: List<String>): Int {
        val depths = input.map {it.toInt()}.windowed(size = 3, step = 1) { it.sum() }
        return increasingCount(depths)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
