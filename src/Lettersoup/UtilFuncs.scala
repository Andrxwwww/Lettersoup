package Lettersoup

import Lettersoup.UtilStuff.Direction.Direction
import Lettersoup.UtilStuff.{Coord2D, Direction}

import scala.io.Source


// class for make some function that are util for the game but they are not associated directly with the game logic
object UtilFuncs {

  //TA - function that returns a List of words from a .txt file + a sequence of coordinates for put in the word
  // format -> WORD-(x1,y1)_(x2,y2)_(x3,y3)...
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



}
