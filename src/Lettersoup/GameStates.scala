package Lettersoup

import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, play, randomChar, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords, randomCharNotInList}
import Lettersoup.Utils.{Board, Coord2D}
import Lettersoup.Utils.Direction.{Direction, stringToDirection}
import Random.SeedGenerator.myRandomGenerator

// class for make the TUI of the game (T8)
object GameStates {
  //TA - function that makes the menu of the game

  def mainLoop(): Unit = {
    println("1.Play")
    println("2.Settings")
    println("3.Exit")
    println(" ")
    print("Choose an option: ")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => loadGame()

      case "2" => loadSettings()

      case "3" => println("See you next time!"); System.exit(0)

      case _ => println("Invalid option \n"); mainLoop()
    }
  }

  //TUI1 - function for start the game
  private def startGame(): (Int,List[List[Char]]) = {
    println(" ")
    print("Insert the size of the board: ")
    val n = scala.io.StdIn.readLine().toInt
    print("Insert the quantity of words: ")
    val numWords = scala.io.StdIn.readLine().toInt
    println(" ")
    val QUANTITY_OF_WORDS = numWords
    val board = List.fill(n, n)('-')
    (QUANTITY_OF_WORDS,board)
  }

  //TUI2 - function that loads the game
  private def loadGame(): Unit = {
    val rand = myRandomGenerator()
    val wordsFound = 0
    val (numWords, board) = startGame()
    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", numWords , board.length)
    if ( numWords > infos._1.length) {
      println("This game generated " + infos._1.length + " words to find! \n")
    }
    if (infos._1.isEmpty && infos._2.isEmpty) {
      println("There is no words for this size board :( , please insert a bigger board size.")
      loadGame()
    }
    val boardWithWords = setBoardWithWords(board, infos._1, infos._2)
    val gameBoard = completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1))._1

    if (checkBoard(gameBoard, infos._1)) {
      println("----LETTERSOUP----")
      print("    ")
      println(gameBoard.map(_.mkString(" ")).mkString("\n    "))
      println(" ")
      // T7 - start the timer
      val startTimer = System.currentTimeMillis()
      runGame(gameBoard, infos._1, wordsFound, startTimer)
    } else {
      println("The game not loaded correctly :(")
      loadGame()
    }
  }

  //TUI3 - function for run the game
  def runGame(board: Board, list: List[String], wordsFound: Int = 0 , startTimer: Long): Unit = {

    print("Insert a word: ")
    val word = scala.io.StdIn.readLine().toUpperCase
    if (word == "EXIT") System.exit(0)
    if (word == "RESTART") loadGame()

    print("Insert a coord -> x,y: ")
    val coord = scala.io.StdIn.readLine()
    val coords = coord.split(",")
    val coord2D = (coords(0).toInt, coords(1).toInt)

    print("Insert a direction: ")
    val direction = scala.io.StdIn.readLine().toUpperCase()
    val dir = stringToDirection(direction)

    if (play(board, word, coord2D, dir) && list.contains(word)) {
      val wordsToFind = list.filterNot(_ == word)
      // T7 - checks if the game is over + calculates the score based on time
      if (wordsToFind.isEmpty) {
        val endTime = System.currentTimeMillis()
        val elapsedTime = (endTime - startTimer)
        val finalScore = elapsedTime * 10
        println("Congratulations! You found all the words! Your score is: " + finalScore)
        restartGame()
      } else {
        println("The word is in the board, you still have " + wordsToFind.length + " words to find! ")
        runGame(board, wordsToFind, wordsFound + 1 , startTimer)
      }
    } else {
      println("Please try again :(")
      runGame(board, list , wordsFound , startTimer)
    }
  }

  //TUIA - function that restarts the game
  def restartGame(): Unit = {
    println("Do you want to play again? (y/n)")
    val playAgain = scala.io.StdIn.readLine()
    playAgain match {
      case "y" => loadGame()
      case "n" => mainLoop()
      case _ => println("Invalid option")
    }
  }

  //TUIA - function that loads the settings
  private def loadSettings(): Unit = {
    println(" ")
    println("1. Change the color of the words")
    println("2. back")
    println(" ")
    print("Choose an option: ")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => {
        println(" ")
        print("Insert the color: ")
        val color = scala.io.StdIn.readLine()
        changeConsoleColor(color)
        println("Your color is now " + color)
        println(" ")
        mainLoop()
      }
      case "2" => {
        mainLoop()
      }
      case _ => println("Invalid option \n")
        loadSettings()
    }
  }

  //TUIA - function that changes the color of the console
  def changeConsoleColor(color: String): Unit = {
    val colorCode = color.toLowerCase match {
      case "red" => "\u001B[31m"
      case "green" => "\u001B[32m"
      case "yellow" => "\u001B[33m"
      case "blue" => "\u001B[34m"
      case _ => "\u001B[0m"
    }
    println(colorCode)
  }
}

