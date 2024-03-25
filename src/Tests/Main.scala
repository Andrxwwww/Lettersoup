package Tests

import Lettersoup.{GameLogic, MyRandom}
import Lettersoup.UtilFuncs.{listOfWords, listSequence, randomChar, randomCoord}

object TestGameLogic {
  def main(args: Array[String]): Unit = {
    val gameLogic = new GameLogic
    val rand = MyRandom(System.currentTimeMillis())
    val board = List.fill(5,5)('-')

    println("Board:")
    println(board.map(_.mkString(" ")).mkString("\n"))

    println(" ")
    val words = listOfWords("src/Lettersoup/Palavras.txt")

    val randomWord = words(rand.nextInt(words.length)._1)
    val randomcoord = randomCoord( rand , board)
    val possibleSequence = listSequence(board, randomcoord._1, randomWord.length)
    println("Random word -> " + randomWord)
    println("Random coordinate -> " + randomcoord._1)
    println("Possible Sequence for it -> " + possibleSequence)

    val BoardWithWord = gameLogic.setBoardWithWords(board, possibleSequence, randomWord)
    println(" ")
    println("Board with the word:")
    println(BoardWithWord.map(_.mkString(" ")).mkString("\n"))

    val boardCompleted = gameLogic.completeBoardRandomly(BoardWithWord, rand, MyRandom => (randomChar(MyRandom)._1, MyRandom))._1
    println(" ")
    println("Board completed:")
    println(boardCompleted.map(_.mkString(" ")).mkString("\n"))
  }
}
