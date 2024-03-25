package Lettersoup

import Lettersoup.UtilFuncs.{listSequence, randomCoord}
import Lettersoup.UtilStuff.{Board, Coord2D, DirectionVector}

import scala.io.Source


// class for make some function that are util for the game but they are not associated directly with the game logic
object UtilFuncs {

  //T1 - function that generates a random character
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val (nextInt, newRand) = rand.nextInt(26) // Gera um número aleatório entre 0 e 26
    val randomChar = ('A' + nextInt).toChar // Converte o número para um caractere entre 'A' e 'Z'
    (randomChar, newRand) // Retorna o caractere aleatório e o novo estado do gerador de números aleatórios
  }

  //TA (Tarefa Auxiliar) - function that generates a random integer
  def randomInt(rand: MyRandom, n: Int): (Int, MyRandom) = {
    val (nextInt, newRand) = rand.nextInt(n) //
    (nextInt, newRand)
  }

  //TA - function that generates a random coordinate
  def randomCoord(rand: MyRandom, board: Board): (Coord2D, MyRandom) = {
    val (nextIntRow, newRandRow) = rand.nextInt(board.length) // Gera um número aleatório entre 0 e o número de linhas do tabuleiro
    val (nextIntCol, newRandCol) = newRandRow.nextInt(board(0).length) // Gera um número aleatório entre 0 e o número de colunas do tabuleiro
    if (board(nextIntRow)(nextIntCol) != '-')
      randomCoord(newRandCol, board) // Se a célula já estiver preenchida, chama a função recursivamente
    else
      ((nextIntRow, nextIntCol), newRandCol) // Retorna a coordenada aleatória e o novo estado do gerador de números aleatórios
  }

  //TA - function that return a list of possible directions for a given coordinate
  def possibleDirections(coord: Coord2D , board: Board): List[Coord2D] = {
    val directions = List(DirectionVector.North, DirectionVector.South, DirectionVector.East, DirectionVector.West, DirectionVector.NorthEast, DirectionVector.NorthWest, DirectionVector.SouthEast, DirectionVector.SouthWest)
    val possibleDirections = directions.filter(d => coord._1 + d._1 >= 0 && coord._1 + d._1 < board.length && coord._2 + d._2 >= 0 && coord._2 + d._2 < board(0).length)
    possibleDirections
  }
  //TA - function that returns a List of words from a .txt file
  def listOfWords(filename: String): List[String] = {
    val source = Source.fromFile(filename) // Abre o arquivo
    val words = source.getLines.toList // Lê as linhas do arquivo
    source.close() // Fecha o arquivo
    words // Retorna a lista de palavras
  }

  //TA - function that returns a valid List of coordinates in form of a sequence
  def listSequence(board: Board, coord: Coord2D, size: Int): List[Coord2D] = {
    def listSequenceAux(board: Board, coord: Coord2D, size: Int, usedCoords: List[Coord2D]): List[Coord2D] = {
      if (size == 0 || usedCoords.contains(coord)) {
        usedCoords
      } else {
        val rand = MyRandom(System.currentTimeMillis())
        val validDirections = possibleDirections(coord, board).filterNot(dir => usedCoords.contains((dir._1 + coord._1, dir._2 + coord._2)))
        if (validDirections.isEmpty) {
          usedCoords
        } else {
          val randomInteger = randomInt(rand, validDirections.size)._1
          val nextCoord = (validDirections(randomInteger)._1 + coord._1, validDirections(randomInteger)._2 + coord._2)
          listSequenceAux(board, nextCoord, size - 1, coord :: usedCoords)
        }
      }
    }
    listSequenceAux(board, coord, size, List())
  }

}
