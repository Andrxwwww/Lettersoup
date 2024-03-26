package Lettersoup

import Lettersoup.UtilStuff.{Board, Coord2D, DirectionVector}
import scala.io.Source


// class for make some function that are util for the game but they are not associated directly with the game logic
object UtilFuncs {

  //TA (Tarefa Auxiliar) - function that generates a random coordinate
  def randomCoord(rand: MyRandom, board: Board): (Coord2D, MyRandom) = {
    val (nextIntRow, newRandRow) = rand.nextInt(board.length) // Gera um número aleatório entre 0 e o número de linhas do tabuleiro
    val (nextIntCol, newRandCol) = newRandRow.nextInt(board(0).length) // Gera um número aleatório entre 0 e o número de colunas do tabuleiro
    if (board(nextIntRow)(nextIntCol) != '-')
      randomCoord(newRandCol, board) // Se a célula já estiver preenchida, chama a função recursivamente
    else
      ((nextIntRow, nextIntCol), newRandCol) // Retorna a coordenada aleatória e o novo estado do gerador de números aleatórios
  }

  //TA - function that returns a List of words from a .txt file + a sequence of coordinates for put in the word
  def getWordsAndCoords(filename: String): (List[String], List[List[Coord2D]]) = {
    val lines = Source.fromFile(filename).getLines().toList
    val wordsAndCoords = lines.map { line =>
      val info = line.split('-')
      val word = info(0) // retira a palavra
      val coordStrings = info(1).split('_') // retira as coordenadas
      val coords = coordStrings.map { coordString =>
        val numbers = coordString.stripPrefix("(").stripSuffix(")").split(',')
        (numbers(0).toInt,numbers(1).toInt)
      }.toList
      (word, coords)
    }
    val (words, coordLists) = wordsAndCoords.unzip
    (words, coordLists)
  }

}
