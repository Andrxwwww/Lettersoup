package Lettersoup

import Lettersoup.UtilStuff.{Board, Coord2D}

import scala.annotation.tailrec
import scala.io.Source


// class for make some function that are util for the game but they are not associated directly with the game logic
object UtilFuncs {

  //TA (Tarefa Auxiliar) - function that generates a random coordinate
  @tailrec
  def randomCoord(rand: MyRandom, board: Board): (Coord2D, MyRandom) = {
    val (nextIntRow, newRandRow) = rand.nextInt(board.length) // Gera um número aleatório entre 0 e o número de linhas do tabuleiro
    val (nextIntCol, newRandCol) = newRandRow.nextInt(board.head.length) // Gera um número aleatório entre 0 e o número de colunas do tabuleiro
    if (board(nextIntRow)(nextIntCol) != '-')
      randomCoord(newRandCol, board) // Se a célula já estiver preenchida, chama a função recursivamente
    else
      ((nextIntRow, nextIntCol), newRandCol) // Retorna a coordenada aleatória e o novo estado do gerador de números aleatórios
  }

  //TA - function that returns a List of words from a .txt file + a sequence of coordinates for put in the word
  def getWordsAndCoords(filename: String, n: Int): (List[String], List[List[Coord2D]]) = {
    val lines = Source.fromFile(filename).getLines().toList
    val r = MyRandom(System.currentTimeMillis())
    val randomLines = r.shuffle(lines).take(n)

    val wordsAndCoords = randomLines.map { line =>
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

  //T4 - function that return a char which is not contained in a list of Strings
  def randomCharNotInList(list: List[String]): MyRandom => (Char, MyRandom) = {
    rand: MyRandom => {
      val (char, newRand) = rand.nextInt(26) // Gera um número aleatório entre 0 e 26
      val randomChar = ('A' + char).toChar // Converte o número para um caractere entre 'A' e 'Z'
      if (list.flatten.contains(randomChar))
        randomCharNotInList(list)(newRand) // Se o caractere já estiver na lista, chama a função recursivamente
      else
        (randomChar, newRand) // Retorna o caractere aleatório e o novo estado do gerador de números aleatórios
    }
  }


}
