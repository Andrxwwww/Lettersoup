package Lettersoup

import Lettersoup.UtilFuncs.directionToCoord
import Lettersoup.UtilStuff.{Board, Coord2D, Direction}
import Lettersoup.UtilStuff.Direction.Direction

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

  //T4 - function that completes the board with random chars [other part of T4 is in UtilFuncs for use in f param]
  def completeBoardRandomly(board: Board, r: MyRandom, f: MyRandom => (Char, MyRandom)): (Board, MyRandom) = {
    val (newBoard, newRand) = board.indices.foldLeft((board, r)) { case ((b, rand), i) =>
      b(i).indices.foldLeft((b, rand)) { case ((bb, rrand), j) =>
        if (bb(i)(j) == '-') {
          val (char, newRand) = f(rrand)
          (fillOneCell(bb, (i, j), char), newRand)
        } else (bb, rrand)
      }
    }
    (newBoard, newRand)
  }
  //T5 - function that checks if a word is in the board
  private def play(board: Board, word: String, coordInitial: Coord2D, direction: Direction): Boolean = {
    val wordList = word.toList
    if (board(coordInitial._1)(coordInitial._2) == wordList.head
      && board(coordInitial._1 + directionToCoord(direction)._1)(coordInitial._2 + directionToCoord(direction)._2) == wordList(1)){
      true
    } else {
      false
    }
  }

  //T6 - function that checks that the words are in the board
  def checkBoard(board: Board , wordsToFind: List[String]): Boolean = {
    val charsOnBoard = board.flatten.filter(_ != '-')
    val charsToFind = wordsToFind.flatMap(_.toList)
    val commonChars = charsOnBoard.filter(charsToFind.contains)
    val noDuplicates = wordsToFind.distinct.length == wordsToFind.length

    commonChars.length == charsToFind.length && noDuplicates
  }

  //TA- function that input a word in board with a initial coord and a direction
  def runGame(board: Board): Unit = {
    print("Insert a word: ")
    val word = scala.io.StdIn.readLine().toUpperCase
    if ( word == "EXIT") return
    if ( word == "RESTART") runGame(board)
    print("Insert a coord -> x,y: ")
    val coord = scala.io.StdIn.readLine()
    val coords = coord.split(",")
    val coord2D = (coords(0).toInt,coords(1).toInt)
    print("Insert a direction: ")
    val direction = scala.io.StdIn.readLine()
    val dir = direction match {
      case "N" => Direction.North
      case "S" => Direction.South
      case "E" => Direction.East
      case "W" => Direction.West
      case "NE" => Direction.NorthEast
      case "NW" => Direction.NorthWest
      case "SE" => Direction.SouthEast
      case "SW" => Direction.SouthWest
    }
    if (play(board,word,coord2D,dir)){
      println("The word is in the board")
    } else {
      println("The word is not in the board , please try again :(")
      runGame(board)
    }
  }

}
