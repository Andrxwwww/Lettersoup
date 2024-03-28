package Lettersoup

import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, play, setBoardWithWords}
import Lettersoup.UtilFuncs.{getWordsAndCoords, randomCharNotInList, stringToDirection}
import Lettersoup.UtilStuff.Board

import scala.annotation.tailrec

object GameStates {

  //TA - function that makes the menu of the game
  def mainLoop(): Unit = {
    println(" ")
    println("----WELCOME TO THE LETTERSOUP GAME----")
    println(" ")
    println("1.Play")
    println("2.Settings")
    println("3.Exit")
    println(" ")
    print("Choose an option: ")
    val option = scala.io.StdIn.readLine()
    option match {
      //TODO: call the functions for each case
      case "1" => {
        loadGame()
      }
      case "2" => {
        println("Settings")
      }
      case "3" => {
        println("See you next time!")
        System.exit(0)
      }
      case _ => println("Invalid option")
    }
  }

  //T-CASE1 - function for start the game
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

  //T-CASE1 - function that loads the game
  private def loadGame(): Unit = {
    val rand = MyRandom(System.currentTimeMillis())
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
      runGame(gameBoard, infos._1, wordsFound)
    } else {
      println("The game not loaded correctly :(")
      startGame()
    }
  }

  //T-CASE1 - function for run the game
  @tailrec
  def runGame(board: Board, list: List[String], wordsFound: Int = 0): Unit = {
    print("Insert a word: ")
    val word = scala.io.StdIn.readLine().toUpperCase
    if (word == "EXIT") return
    if (word == "RESTART") loadGame()
    print("Insert a coord -> x,y: ")
    val coord = scala.io.StdIn.readLine()
    val coords = coord.split(",")
    val coord2D = (coords(0).toInt, coords(1).toInt)
    print("Insert a direction: ")
    val direction = scala.io.StdIn.readLine().toUpperCase()
    val dir = stringToDirection(direction)
    if (play(board, word, coord2D, dir, list)) {
      val wordsToFind = list.filterNot(_ == word)
      if (wordsToFind.isEmpty) {
        winGame()
      } else {
        println("The word is in the board, you still have " + wordsToFind.length + " words to find! ")
        runGame(board, wordsToFind, wordsFound + 1)
      }
    } else {
      println("Please try again :(")
      runGame(board, list , wordsFound)
    }
  }

  private def winGame(): Unit = {
    println("Congratulations! You found all the words!")
    println("Do you want to play again? (y/n)")
    val playAgain = scala.io.StdIn.readLine()
    playAgain match {
      case "y" => loadGame()
      case "n" => mainLoop()
      case _ => println("Invalid option")
    }
  }


}
