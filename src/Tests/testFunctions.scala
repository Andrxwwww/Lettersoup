package Tests

import Lettersoup.GameLogic.checkBoard
import Lettersoup.Utils.Board

object testFunctions {
  def main(args: Array[String]): Unit = {
    val board: Board = List(
      List('a', 'b', 'c'),
      List('d', 'e', 'f'),
      List('d', 'e', 'f')
    )

    val wordsToFind: List[String] = List("abc" , "def")

    val result: Boolean = checkBoard(board, wordsToFind)
    println(s"Board contains repeated words: $result")
  }
}
