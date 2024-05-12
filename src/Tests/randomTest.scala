package Tests

import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, play, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords, randomCharNotInList}
import Lettersoup.Utils.{Coord2D, Direction}
import Lettersoup.Utils.Direction.{Direction, stringToDirection}
import Random.SeedGenerator.myRandomGenerator

object randomTEST {

  def main(args: Array[String]): Unit = {


    val dirs = Direction.values.toList
    val board = List.fill(5, 5)('-')

    val rand = myRandomGenerator()

    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", 2, board.length)
    println(infos._1 + " " + infos._2)
    val boardWithWords = setBoardWithWords(board, infos._1, infos._2)
    println(boardWithWords.map(_.mkString(" ")).mkString("\n"))
    println(" ")
    val gameBoard = completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1))._1
    println(gameBoard.map(_.mkString(" ")).mkString("\n"))
    println(" is the board valid: " + checkBoard(gameBoard, infos._1))

    var gameplay = playGame()
    if (play(gameBoard, gameplay._1, gameplay._2, gameplay._3)) {
      println("You found the word!")
    } else {
      println("You didn't find the word!")
    }
  }

  def playGame(): ( String, Coord2D , Direction) = {
    print("Insert a word: ")
    val word = scala.io.StdIn.readLine().toUpperCase
    if (word == "EXIT") {
      System.exit(0)
    }
    print("Insert a coord -> x,y: ")
    val coord = scala.io.StdIn.readLine()
    val coords = coord.split(",")
    val coord2D = (coords(0).toInt, coords(1).toInt)
    print("Insert a direction: ")
    val direction = scala.io.StdIn.readLine().toUpperCase()
    val dir = stringToDirection(direction)
    (word, coord2D, dir)
  }

}