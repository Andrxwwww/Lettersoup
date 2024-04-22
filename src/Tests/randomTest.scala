package Tests

import Lettersoup.GameLogic.{completeBoardRandomly, setBoardWithWords}
import Lettersoup.UtilFunctions.{getCharsAround, getWordsAndCoords, randomCharNotInList}
import Lettersoup.Utils.Direction
import MyRandom.MyRandom
import _root_.MyRandom.SeedGenerator.randomIntFunction

object randomTest {

  def main(args: Array[String]): Unit = {


    val dirs = List(Direction.North, Direction.South, Direction.East, Direction.West, Direction.NorthEast, Direction.NorthWest, Direction.SouthEast, Direction.SouthWest)

    val board = List.fill(5, 5)('-')

    val rand = MyRandom(randomIntFunction()) //TODO: change the seed for being impured DONE :]

    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", 2, board.length)
    println(infos._1 + " " + infos._2)
    val boardWithWords = setBoardWithWords(board, infos._1, infos._2)
    println(boardWithWords.map(_.mkString(" ")).mkString("\n"))
    println(" ")
    val gameBoard = completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1))._1
    println(gameBoard.map(_.mkString(" ")).mkString("\n"))

    println(" ")
    println(getCharsAround(gameBoard,( 5,5) , dirs))
  }

}
