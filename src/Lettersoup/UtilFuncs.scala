package Lettersoup

import Lettersoup.UtilStuff.Direction.Direction
import Lettersoup.UtilStuff.{Coord2D, Direction}

import scala.io.Source


// class for make some function that are util for the game but they are not associated directly with the game logic
object UtilFuncs {
  /*
  _getWordsAndCoords_
  - params: filename -> name of the file with the words and coordinates
            n -> number of words to get
            boardSize -> size of the board
  - description: function that returns a List of words from a .txt file^ + a sequence of coordinates for put in the word
  - return: a tuple with a List of words and a List of List of coordinates
  ^ in the format -> WORD-(x1,y1)_(x2,y2)_(x3,y3)...
  */
  def getWordsAndCoords(filename: String, n: Int , boardSize: Int): (List[String], List[List[Coord2D]]) = {
    val lines = Source.fromFile(filename).getLines().toList
    val r = MyRandom(System.currentTimeMillis())
    /*
    _parseLine_
    - params: line -> a line from the file
    - description: function that parses a line from the file and returns a tuple with the word and a list of coordinates
    - return: a tuple with the word and a list of coordinates
     */
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

    /*
    _shuffleLines_
    - params: lines -> list of lines from the file
              usedCoords -> set of coordinates that are already used
    description: checks if there is no coordinates repeated [for not superimpose words]
    - return: a list of lines shuffled
     */
    //Todo: if a player wants p.e: 4 words , and 2 of them are repeated, the game will only have 3 words
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

    val randomLines = shuffleLines(filteredLines)
    val wordsAndCoords = randomLines.map(parseLine).take(n)
    val (words, coordLists) = wordsAndCoords.unzip
    (words, coordLists)
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

  //TA - function that associates a coord. diretion vector to a Direction
  def directionToCoord(direction: Direction): Coord2D = {
    direction match {
      case Direction.North => (-1, 0)
      case Direction.South => (1, 0)
      case Direction.East => (0, 1)
      case Direction.West => (0, -1)
      case Direction.NorthEast => (-1, 1)
      case Direction.NorthWest => (-1, -1)
      case Direction.SouthEast => (1, 1)
      case Direction.SouthWest => (1, -1)
    }
  }

  //TA - function that associates a string to a Direction
  def stringToDirection(direction: String): Direction = {
    direction match {
      case "N" => Direction.North
      case "S" => Direction.South
      case "E" => Direction.East
      case "W" => Direction.West
      case "NE" => Direction.NorthEast
      case "NW" => Direction.NorthWest
      case "SE" => Direction.SouthEast
      case "SW" => Direction.SouthWest
    }
  }



}
