import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.IllegalStateException
import java.util.*

fun main() {
    data class Board(val cells: MutableList<Int>) {
        fun markNumber(draw: Int) {
            val index = cells.indexOf(draw)
            if (index != -1) cells[index] = -1
        }

        fun win(): Boolean {
            return cells.windowed(size = 5, step = 5).any { window -> window.sum() == -5 }
                    ||
                    (0..4).any { i ->
                        cells.drop(i).windowed(size = 1, step = 5) { window -> window.first() }.sum() == -5
                    }
        }

        fun sumUnmarked(): Int {
            return cells.filter { i -> i != -1 }.sum()
        }
    }

    data class BingoData(val drawNumber: List<Int>, val boards: List<Board>)

    fun getBingoData(file: File): BingoData {
        fun readBoard(scanner: Scanner): List<Int> {
            val result = mutableListOf<Int>()
            while (scanner.hasNextInt()) {
                result.add(scanner.nextInt())
            }
            return result
        }

        val scanner = Scanner(file)
        val drawNumber = scanner.nextLine().split(',').map { it.toInt() }
        val boards = readBoard(scanner).windowed(size = 25, step = 25) { window -> Board(window.toMutableList()) }
        return BingoData(drawNumber, boards)
    }

    fun part1(input: File): Int {
        val (drawNumber, boards) = getBingoData(input)

        for (draw in drawNumber) {
            for (board in boards) {
                board.markNumber(draw)
                if (board.win()) {
                    return draw * board.sumUnmarked()
                }
            }
        }
        throw IllegalStateException("No winner is found")
    }

    fun part2(input: File): Int {
        val (drawNumber, boards) = getBingoData(input)

        var numOfWinningBoards = 0
        val winningIndices = mutableSetOf<Int>()
        for (draw in drawNumber) {
            loop@ for (index in boards.indices) {
                if (winningIndices.contains(index)) continue@loop
                val board = boards[index]
                board.markNumber(draw)
                if (board.win()) {
                    winningIndices.add(index)
                    numOfWinningBoards += 1
                    if (numOfWinningBoards == boards.size) {
                        return draw * board.sumUnmarked()
                    }
                }
            }
        }
        throw IllegalStateException("No winner is found")
    }

    // test if implementation meets criteria from the description, like:
    val testFile = File("src", "Day04_test.txt")
    check(part1(testFile) == 4512)
    check(part2(testFile) == 1924)

    val file = File("src", "Day04.txt")
    println(part1(file))
    println(part2(file))
}
