package Tests

import Lettersoup.{GameLogic, MyRandom}
import Lettersoup.UtilFuncs.{getWordsAndCoords, randomCharNotInList}

object Main {
  def main(args: Array[String]): Unit = {

    val QUANTITY_OF_WORDS = 2

    val gameLogic = new GameLogic
    val rand = MyRandom(5)
    val board = List.fill(5, 5)('-')

    println(" ")
    println("NW N NE")
    println("W  *  E")
    println("SW S SE")
    println(" ")

    //words and the sequence of coordinates from the file
    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", QUANTITY_OF_WORDS)
    //board with the words
    val boardWithWords = gameLogic.setBoardWithWords(board, infos._1, infos._2)
    //board with the words + random chars
    val boardWithRandomChars = gameLogic.completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1))._1
    if (gameLogic.checkBoard(boardWithRandomChars, infos._1)) {
      println("---LETTERSOUP---")
      print("   ")
      println(boardWithRandomChars.map(_.mkString(" ")).mkString("\n   "))
      println(" ")
      gameLogic.runGame(boardWithRandomChars , infos._1)
    } else {
      println("The game not loaded correctly :(")
    }
  }
}
