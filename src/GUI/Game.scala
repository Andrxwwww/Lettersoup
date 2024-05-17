package GUI

import Lettersoup.GameLogic.{completeBoardRandomly, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords, randomCharNotInList}
import Random.SeedGenerator.myRandomGenerator

case class Game(boardSize: Int, maxWords: Int, filePath: String) {
  private var rand = myRandomGenerator()
  private val board = List.fill(boardSize, boardSize)('-')
  private var infos = getWordsAndCoords(filePath, 1, board.length)
  private var boardWithWords = setBoardWithWords(board, infos._1, infos._2)
  private var gameBoard = completeBoardRandomly(boardWithWords, rand,randomCharNotInList(infos._1))._1

  def reset(n: Int): Unit = {
    rand = myRandomGenerator()
    infos = getWordsAndCoords(filePath, n, board.length)
    boardWithWords = setBoardWithWords(board, infos._1, infos._2)
    gameBoard = completeBoardRandomly(boardWithWords, rand, randomCharNotInList(infos._1))._1
  }

  def getGameBoard: List[List[Char]] = gameBoard
  def getWords: List[String] = infos._1
  def updateWords(words: List[String]): Unit = {
    infos = (words, infos._2)
  }
}
