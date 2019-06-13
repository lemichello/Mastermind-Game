data class Evaluation(val rightPositions: Int, val wrongPositions: Int)

fun Evaluation.isGuessed(): Boolean = rightPositions == LETTERS_COUNT