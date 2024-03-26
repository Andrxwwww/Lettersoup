package Lettersoup

import Lettersoup.UtilStuff.{Board, Coord2D}
import Lettersoup.UtilFuncs.{getWordsAndCoords, randomCoord}

// class for the game logic
class GameLogic {

  //T1 - function that generates a random char
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val (nextInt, newRand) = rand.nextInt(26) // Gera um número aleatório entre 0 e 26
    val randomChar = ('A' + nextInt).toChar // Converte o número para um caractere entre 'A' e 'Z'
    (randomChar, newRand) // Retorna o caractere aleatório e o novo estado do gerador de números aleatórios
  }

  //T2 - function that associates a random char to a coordinate
  private def fillOneCell(board: Board, coord: Coord2D, char: Char): Board = {
    if (coord._1 < 0 || coord._1 >= board.length || coord._2 < 0 || coord._2 >= board.head.length || char == '-' ) {
      //se tiver fora do tabuleiro ou não for um char válido, retorna o tabuleiro sem alterações
      board
    } else {
      board.updated(coord._1, board(coord._1).updated(coord._2, char))
    }
  }

  //T3 - function that fills each char of a word in the board
  def setBoardWithWords(board: Board, words: List[String], positions: List[List[Coord2D]]): Board = {
    words.zip(positions).foldLeft(board) { case (b, (word, positionList)) =>
      word.zip(positionList).foldLeft(b) { case (b, (char, position)) =>
        fillOneCell(b, position, char)
      }
    }
  }

  //T4 - function that completes the board with random chars
  def completeBoardRandomly(board:Board, r:MyRandom, f: MyRandom => (Char, MyRandom)):(Board, MyRandom) = {
      if (board.flatten.contains('-')) {
        val (char, newRand) = f(r)
        val (coord, newRandCoord) = randomCoord(newRand, board)
        val newBoard = fillOneCell(board, coord, char)
        completeBoardRandomly(newBoard, newRandCoord, f)
      } else {
        (board, r)
      }
  }

  //T6 - function that checks that the words are in the board
  def checkBoard(board: Board , filename: String , n: Int): Boolean = {
    val wordsToFind = getWordsAndCoords(filename , n)._1 // Obter todas as palavras do board
    val charsOnBoard = board.flatten.filter(_ != '-') // Obter todos os caracteres no tabuleiro
    val charsToFind = wordsToFind.flatMap(_.toList) // Converter a lista de palavras para uma lista de caracteres
    val noDuplicates = wordsToFind.distinct.length == wordsToFind.length // Verificar se nenhuma das palavras fornecidas está duplicada
    val commonChars = charsOnBoard.intersect(charsToFind) // Obter os caracteres que estão tanto em charsOnBoard quanto em charsToFind
    val correctQuantity = commonChars.length == charsToFind.length // Verificar se a quantidade de caracteres comuns é igual à quantidade de caracteres nas palavras fornecidas
    noDuplicates && correctQuantity
  }

}
