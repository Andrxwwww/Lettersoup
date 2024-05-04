package Lettersoup

import Lettersoup.Utils.{Board, Coord2D}
import Random.SeedGenerator.myRandomGenerator

import scala.io.Source


// class for make some function that are util for the game but they are not associated directly with the game logic
object UtilFunctions {
  /*
  _getWordsAndCoords_
  - params: filename -> name of the file with the words and coordinates
            n -> number of words to get
            boardSize -> size of the board
  - description: function that returns a List of words from a .txt file^ + a sequence of coordinates for put in the word
  - return: a tuple with a List of words and a List of List of coordinates
  ^ in the format -> WORD-(x1,y1)_(x2,y2)_(x3,y3)...
  */

  /*
  _parseLine_
  - params: line -> a line from the file
  - description: function that parses a line from the file and returns a tuple with the word and a list of coordinates
  - return: a tuple with the word and a list of coordinates
   */

  /*
  _shuffleLines_
  - params: lines -> list of lines from the file
            usedCoords -> set of coordinates that are already used
  description: checks if there is no coordinates repeated [for not superimpose words]
  - return: a list of lines shuffled
  //Todo: if a player wants p.e: 4 words , and 2 of them are repeated, the game will only have 3 words
   */

  def getWordsAndCoords(filename: String, n: Int , boardSize: Int): (List[String], List[List[Coord2D]]) = {
    val lines = Source.fromFile(filename).getLines().toList
    val r = myRandomGenerator()

    def parseLine(line: String): (String, List[Coord2D]) = {
      val info = line.split('-')
      val word = info(0) // retira a palavra
      val coordStrings = info(1).split('_') // retira as coordenadas
      val coords = coordStrings.map { coordString =>
        val numbers = coordString.stripPrefix("(").stripSuffix(")").split(',')
        (numbers(0).toInt,numbers(1).toInt)
      }.toList
      (word, coords)
    }

    // checks if there is no coordinates greater than boardSize[for not having incomplete words or words out of bounds]
    val filteredLines = lines.filter { line =>
      val (_, coords) = parseLine(line)
      !coords.exists(coord => coord._1 >= boardSize && coord._2 >= boardSize)
    }

    def shuffleLines(lines: List[String], usedCoords: Set[Coord2D] = Set()): List[String] = {
      if (lines.isEmpty || usedCoords.size == n) {
        List()
      } else {
        val line = r.shuffle(lines).head
        val (_, coords) = parseLine(line)
        if (coords.exists(usedCoords.contains)) {
          shuffleLines(lines.filterNot(_ == line), usedCoords)
        } else {
          line :: shuffleLines(lines.filterNot(_ == line), usedCoords ++ coords)
        }
      }
    }
    /*
    val randomLines = r.shuffle(lines)
    val wordsAndCoords = randomLines.map(parseLine).take(n)
    */
    val randomLines = shuffleLines(filteredLines)
    val wordsAndCoords = randomLines.map(parseLine).take(n)
    val (words, coordLists) = wordsAndCoords.unzip
    (words, coordLists)
  }

  def validPosition(coord: Coord2D, board: Board): Boolean = {
    coord._1 >= 0 && coord._1 < board.length && coord._2 >= 0 && coord._2 < board.head.length
  }


}
