package Lettersoup

import Lettersoup.UtilStuff.{Board, Coord2D}
import Lettersoup.UtilFuncs.{listSequence, randomCoord, randomInt}
// class for the game logic
class GameLogic {

  //T2 - function that associates a random char to a coordinate
  def fillOneCell(board: Board, coord: Coord2D, char: Char): Board = {
    if (coord._1 < 0 || coord._1 >= board.length || coord._2 < 0 || coord._2 >= board(0).length || char == '-' ) {
      //se tiver fora do tabuleiro ou não for um char válido, retorna o tabuleiro sem alterações
      board
    } else {
      board.updated(coord._1, board(coord._1).updated(coord._2, char))
    }
  }

  //T3 - function that fills each char of a word in the board
  def setBoardWithWords(board: Board, position: List[Coord2D], word: String): Board = {
    if (word.isEmpty) {
      board
    } else {
      val newBoard = fillOneCell(board, position.head, word.head)
      setBoardWithWords(newBoard, position.tail, word.tail)
    }
  }

  //T4 - function that completes the board with random chars
  def completeBoardRandomly(board:Board, r:MyRandom, f: MyRandom => (Char, MyRandom)):(Board, MyRandom) = {
    def completeBoardRandomlyAux(board:Board, r:MyRandom, f: MyRandom => (Char, MyRandom)): (Board, MyRandom) = {
      if (board.flatten.contains('-')) {
        val (char, newRand) = f(r)
        val (coord, newRandCoord) = randomCoord(newRand, board)
        val newBoard = fillOneCell(board, coord, char)
        completeBoardRandomlyAux(newBoard, newRandCoord, f)
      } else {
        (board, r)
      }
    }
    completeBoardRandomlyAux(board, r, f)
  }

}
