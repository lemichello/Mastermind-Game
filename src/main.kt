import kotlin.random.Random

val ALPHABET = 'A'..'F'
const val LETTERS_COUNT = 4

fun main() {
    println("Enter [1], if you want to play with repeating letters, [2], if not.")

    val choice = readLine()!!.toInt()
    val withRepeating = choice == 1

    play(generateSecretStr(withRepeating))
}

fun compareGuess(secretStr: String, guessStr: String): Evaluation {
    val guessedCount = secretStr.zip(guessStr).count { it.first == it.second }
    var commonLettersCount = 0

    for (i in ALPHABET) {
        val secretRepeatingCount = secretStr.count { it == i }
        val guessRepeatingCount = guessStr.count { it == i }

        commonLettersCount += Math.min(secretRepeatingCount, guessRepeatingCount)
    }

    return Evaluation(guessedCount, commonLettersCount - guessedCount)
}

fun play(secretStr: String) {
    var evaluation: Evaluation

    do {
        print("Your guess variant : ")

        var guess = readLine()!!

        while (isIncorrect(guess)) {
            println(
                "ERROR: Incorrect input string: $guess. " +
                        "It\'s length must be $LETTERS_COUNT and consists of $ALPHABET"
            )

            guess = readLine()!!
        }

        evaluation = compareGuess(secretStr, guess)

        if (evaluation.isGuessed())
            println("You guessed a secret string")
        else
            println(
                "Right positions : ${evaluation.rightPositions}\n" +
                        "Wrong positions : ${evaluation.wrongPositions}"
            )

    } while (!evaluation.isGuessed())
}

fun isIncorrect(str: String): Boolean = str.length != LETTERS_COUNT || str.any { it !in ALPHABET }

fun generateSecretStr(withRepeating: Boolean): String {
    val letters = ALPHABET.toMutableList()

    return buildString {
        for (i in 1..LETTERS_COUNT) {
            val letter = letters[Random.nextInt(letters.size)]

            append(letter)

            if (!withRepeating)
                letters.remove(letter)
        }
    }
}