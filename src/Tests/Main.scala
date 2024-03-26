package Tests

import Lettersoup.{GameLogic, MyRandom}
import Lettersoup.UtilFuncs.{getWordsAndCoords,randomCharNotInList}

object Main {
  def main(args: Array[String]): Unit = {
    //TO-DO: METER CONSTANTE PARA QUANTITY_OF_WORDS
    val gameLogic = new GameLogic
    val rand = MyRandom(System.currentTimeMillis())
    val board = List.fill(5,5)('-')

    //print the board
    println("Board:")
    println(board.map(_.mkString(" ")).mkString("\n"))

    //print the words and the sequence of coordinates from the file
    val infos = getWordsAndCoords("src/Lettersoup/Palavras.txt",2)
    println("Words-> " + infos._1)
    println("Coords-> " + infos._2)

    //print the board with the words
    val boardWithWords = gameLogic.setBoardWithWords(board, infos._1, infos._2)
    println(" ")
    println("Board with words:")
    println(boardWithWords.map(_.mkString(" ")).mkString("\n"))

    //print the board with the words + random chars
    val boardWithRandomChars = gameLogic.completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1) )._1
    println(" ")
    println("Board with random chars + words:")
    println(boardWithRandomChars.map(_.mkString(" ")).mkString("\n"))

    //check the board
    println(" ")
    println("Check the board:")
    println(gameLogic.checkBoard(boardWithRandomChars, "src/Lettersoup/Palavras.txt" ,2))
  }
}
