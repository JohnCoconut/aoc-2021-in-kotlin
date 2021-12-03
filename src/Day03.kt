fun main() {
    fun part1(input: List<String>): Int {
        val numOfLines = input.size
        val lengthOfLine = input[0].length
        val numOfOnes = MutableList(lengthOfLine) { 0 }
        input.forEach { it.forEachIndexed { index, c -> numOfOnes[index] += c - '0' } }
        val gamma = numOfOnes.reversed().foldIndexed(0) { index, acc, i ->
            if (2 * i > numOfLines) acc + (1 shl index) else acc
        }
        val epsilon = (1 shl lengthOfLine) - gamma - 1
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        fun criteria(input: List<String>, bit: Int, cmp: (a: Int, b: Int) -> Boolean): String {
            if (input.size == 1) return input[0]
            val parts = input.partition { it[bit] == '0' }
            val zeroPart = parts.first
            val onePart = parts.second
            return if (cmp(zeroPart.size, onePart.size)) {
                criteria(zeroPart, bit + 1, cmp)
            } else {
                criteria(onePart, bit + 1, cmp)
            }
        }

        val oxygenCount = criteria(input, 0) { i, j -> i > j }.toInt(2)
        val co2Count = criteria(input, 0) { i, j -> i <= j }.toInt(2)
        return oxygenCount * co2Count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
