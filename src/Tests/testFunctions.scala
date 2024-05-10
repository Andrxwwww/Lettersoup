package Tests

import Lettersoup.GameLogic.checkBoard
import Lettersoup.Utils.Board

object testFunctions {
  def main(args: Array[String]): Unit = {
    val board: Board = List(
      List('a', 'b', 'a'),
      List('c', 'c', 'c'),
      List('a', 'a', 'a')
    )

    val wordsToFind: List[String] = List("aba","aba")

    val result: Boolean = checkBoard(board, wordsToFind)
    println(s"Board contains repeated words: $result")
  }
}
