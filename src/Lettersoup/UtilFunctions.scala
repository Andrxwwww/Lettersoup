package Lettersoup

import Lettersoup.Utils.Direction.Direction
import Lettersoup.Utils.{Board, Coord2D, Direction}
import Random.MyRandom
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

  def getWordsAndCoords(filename: String, n: Int, boardSize: Int): (List[String], List[List[Coord2D]]) = {
    val lines = Source.fromFile(filename).getLines().toList
    val r = myRandomGenerator()

    def parseLine(line: String): (String, List[Coord2D]) = {
      val info = line.split('-')
      val word = info(0) // retira a palavra
      val coordStrings = info(1).split('_') // retira as coordenadas
      val coords = coordStrings.map { coordString =>
        val numbers = coordString.stripPrefix("(").stripSuffix(")").split(',')
        (numbers(0).toInt, numbers(1).toInt)
      }.toList
      (word, coords)
    }

    // Filtra as linhas para garantir que todas as coordenadas estejam dentro dos limites do tabuleiro
    val filteredLines = lines.filter { line =>
      val (_, coords) = parseLine(line)
      !coords.exists(coord => coord._1 >= boardSize || coord._2 >= boardSize)
    }

    // Embaralha as linhas filtradas
    val shuffledLines = r.shuffle(filteredLines)

    // Coleta as primeiras `n` palavras e suas coordenadas
    val wordsAndCoords = shuffledLines.map(parseLine).take(n)
    val (words, coordLists) = wordsAndCoords.unzip
    (words, coordLists)
  }

  def validPosition(coord: Coord2D, board: Board): Boolean = {
    coord._1 >= 0 && coord._1 < board.length && coord._2 >= 0 && coord._2 < board.head.length
  }

  //T4 - function that return a char which is not contained in a list of Strings
  /*
  _randomCharNotInList_
  - params: list -> list of strings
            rand -> MyRandom object
  - description: function that returns a random character that is not contained in a list of strings
  - return: a tuple with a random character and a new MyRandom object
   */
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

  def countWordInBoard(board: Board, word: String): Int = {
    val directions = Direction.values.toList

    def searchWord(x: Int, y: Int, index: Int, visited: Set[(Int, Int)]): Int = {
      if (index == word.length) 1
      else {
        directions.map { dir =>
          val (dx, dy) = Direction.directionToCoord(dir)
          val nx = x + dx
          val ny = y + dy
          if (validPosition((nx, ny), board) && board(nx)(ny) == word.charAt(index) && !visited.contains((nx, ny)))
            searchWord(nx, ny, index + 1, visited + ((nx, ny)))
          else
            0
        }.sum
      }
    }

    val results = board.indices.flatMap { row =>
      board.head.indices.flatMap { col =>
        if (board(row)(col) == word.head) Some(searchWord(row, col, 1, Set((row, col))))
        else None
      }
    }

    results.sum
  }




}
