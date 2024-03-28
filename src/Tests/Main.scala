package Tests

import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, setBoardWithWords}
import Lettersoup.GameStates.runGame
import Lettersoup.MyRandom
import Lettersoup.UtilFuncs.{getWordsAndCoords, randomCharNotInList}

object Main {
  def main(args: Array[String]): Unit = {

    val QUANTITY_OF_WORDS = 2
    val rand = MyRandom(5)
    val board = List.fill(5, 5)('-')
    val wordFound = 0

    println(Console.BLUE + " now it's really blue!" + Console.RESET)

    //words and the sequence of coordinates from the file
    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", QUANTITY_OF_WORDS , board.length)
    //board with the words
    val boardWithWords = setBoardWithWords(board, infos._1, infos._2)
    //board with the words + random chars
    val boardWithRandomChars = completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1))._1
    if (checkBoard(boardWithRandomChars, infos._1)) {
      println("---LETTERSOUP---")
      print("   ")
      println(boardWithRandomChars.map(_.mkString(" ")).mkString("\n   "))
      println(" ")
      runGame(boardWithRandomChars , infos._1 , wordFound)
    } else {
      println("The game not loaded correctly :(")
    }
  }
}
