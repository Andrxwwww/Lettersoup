package Lettersoup

import Lettersoup.UtilStuff.{Board, Coord2D}

object GameLogic {

  //T2 - function that associates a random char to a coordinate
  def fillOneCell(board: Board, coord: Coord2D, char: Char): Board = {
    if (coord._1 < 0 || coord._1 >= board.length || coord._2 < 0 || coord._2 >= board(0).length || char > 'Z' || char < 'A') {
      //se tiver fora do tabuleiro ou não for um char válido, retorna o tabuleiro sem alterações
      board
    } else {
      board.updated(coord._1, board(coord._1).updated(coord._2, char))
    }
  }

}
object TestGameLogic {
  def main(args: Array[String]): Unit = {
  }
}