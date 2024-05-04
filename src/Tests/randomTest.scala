package Tests

import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, play, randomChar, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords}
import Lettersoup.Utils.Coord2D
import Lettersoup.Utils.Direction.{Direction, stringToDirection}
import Lettersoup.GameStates.userTrials
import Random.SeedGenerator.myRandomGenerator
object randomTest {


  def main(args: Array[String]): Unit = {

    val board = List.fill(5, 5)('-')

    val rand = myRandomGenerator()


    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", 2, board.length)
    println(infos._1 + " " + infos._2)
    val boardWithWords = setBoardWithWords(board, infos._1, infos._2)
    println(boardWithWords.map(_.mkString(" ")).mkString("\n"))
    println(" ")
    val gameBoard = completeBoardRandomly(boardWithWords, rand, rand => randomChar(rand))._1
    if (checkBoard(gameBoard , infos._1)) {
      println("Board loaded correctly")
      println(gameBoard.map(_.mkString(" ")).mkString("\n"))
      println(" ")
      println(System.nanoTime())
      var gameplay = userTrials()
      if (play(gameBoard, gameplay._1, gameplay._2, gameplay._3)) {
        println("You found the word!")
      } else {
        println("You didn't find the word!")
      }
    } else {
      println("Board not loaded correctly")
    }


  }



}
