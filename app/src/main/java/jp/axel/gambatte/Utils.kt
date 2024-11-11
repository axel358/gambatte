package jp.axel.gambatte

import java.util.Random


object Utils {
    fun generateRandomNumbers(count: Int, min: Int, max: Int): Set<Int> {
        require(count <= max - min + 1) { "Cannot generate more unique numbers than the range allows." }
        val random = Random()
        val generatedNumbers: MutableSet<Int> = HashSet()
        while (generatedNumbers.size < count) {
            val randomNumber: Int = min + random.nextInt(max - min + 1)
            generatedNumbers.add(randomNumber)
        }
        return generatedNumbers
    }
}