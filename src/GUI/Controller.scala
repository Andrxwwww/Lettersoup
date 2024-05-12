package GUI

import GUI.FxApp.{gameHistory, startTimer}
import javafx.fxml.FXML
import javafx.scene.layout.{AnchorPane, GridPane, Pane}
import javafx.stage.Stage
import Lettersoup.Utils.{Board, Coord2D}
import Random.SeedGenerator.myRandomGenerator
import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, play, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords, randomCharNotInList}
import Lettersoup.Utils.Direction.{Direction, coordToDirection, directionToString}
import javafx.scene.control.{Button, ColorPicker, Label, TextField}
import javafx.scene.paint.Color

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

class Controller {
  @FXML var mainMenuPane: AnchorPane = _
  @FXML var settingsPane: AnchorPane = _
  @FXML var gamePane: AnchorPane = _
  @FXML var gamegrid: GridPane = _
  @FXML var winGame: Pane = _
  @FXML var wordVisualize: TextField = _
  @FXML var boardFailedPop: Pane = _
  @FXML var bgColorPicker: ColorPicker = _
  @FXML var scoreID: Label = _

  var rand = myRandomGenerator()
  var board = List.fill(5, 5)('-')
  var infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", 1, board.length)
  var boardWithWordsGUI = setBoardWithWords(board, infos._1, infos._2)
  var gameBoardGUI = completeBoardRandomly(boardWithWordsGUI, rand, randomCharNotInList(infos._1))._1

  // --> SHOWING PANES
  def showSettings(): Unit = {
    mainMenuPane.setVisible(false)
    settingsPane.setVisible(true)
    gamePane.setVisible(false)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
  }

  def showMainMenu():  Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(true)
    gamePane.setVisible(false)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
    handleReset()
  }

  def showGame(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(true)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
  }

  def showWinGame(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(true)
    winGame.setVisible(true)
    boardFailedPop.setVisible(false)
  }

  def showFailedLoad(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(true)
    winGame.setVisible(false)
    boardFailedPop.setVisible(true)
  }

  //--> MAINMENU ACTIONS
  @FXML
  def onExitClicked(): Unit = {
    val stage = mainMenuPane.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }

  @FXML
  def handlePlayButton(): Unit = {
    startTimer = System.currentTimeMillis()
    showGame()
    handleReset()
    updateGrid(gameBoardGUI, infos._1 )
  }

  //--> GAME ACTIONS
  @FXML
  def handleGamePlayButton(): Unit = {
    if( play(gameBoardGUI, formWord()  , gameHistory.head._2, firstDirection()) && infos._1.contains(formWord() )){
      System.out.println("Word found")
      val endTimer = System.currentTimeMillis()
      // assim quanto mais rapido mais pontos
      val score = 100000 - (endTimer - startTimer).toInt
      scoreID.setText(score.toString)
      showWinGame()
    } else {
      System.out.println("Word not found")
      handleReset()
      wordVisualize.setText("Wrong word :(")
    }
  }

  @FXML
  def handleRepeatGame(): Unit = {
    val buttonsWGamePop = winGame.getChildren().stream()
      .filter(_.isInstanceOf[Button]).toArray().map(_.asInstanceOf[Button])
    val buttonClicked = buttonsWGamePop.find(_.isHover)

    buttonClicked match {
      case Some(button) => button.getText() match {
        case "Yes" =>
          startTimer = System.currentTimeMillis()
          createNewGame()
        case "No" =>
          createNewGame()
          showMainMenu()
      }
    }
  }


  @FXML
  def handleGameFailedLoad(): Unit = {
    val buttonsForRetryGame = boardFailedPop.getChildren().stream()
      .filter(_.isInstanceOf[Button]).toArray().map(_.asInstanceOf[Button])
    val buttonClicked = buttonsForRetryGame.find(_.isHover)

    buttonClicked match {
      case Some(button) => button.getText() match {
        case "Retry" =>
          handlePlayButton()
        case "Quit" =>
          showMainMenu()
      }
    }
  }


  def updateGrid(gameBoardGUI: Board , wordsToFind: List[String]): Unit = {
    if (checkBoard(gameBoardGUI, wordsToFind)) {
      gameBoardGUI.zipWithIndex.flatMap { case (row, i) =>
        row.zipWithIndex.map { case (cell, j) =>
          val info = (cell.toString, (i, j))
          findButtonByInfo(info) match {
            case Some(button) => button.setText(cell.toString)
            case None => println(s"Button not found: ${info._1}, row: ${info._2._1}, col: ${info._2._2}")
          }
        }
      }
    } else {
      System.out.println("Game Not Loaded Correctly :(")
    }
  }

  def findButtonByInfo(info: (String, Coord2D)): Option[Button] = {
    val (char, (row, col)) = info
    val coord = row * 5 + col + 1
    val buttonId = s"Button$coord"

    val children = gamegrid.getChildren()
    children.collectFirst {
      case btn: Button if btn.getId == buttonId => btn
    }
  }

  def undoLastSelection(): Unit = {
    if (gameHistory.nonEmpty) {
      val lastSelectedButtonInfo = gameHistory.last

      findButtonByInfo(lastSelectedButtonInfo) match {
        case Some(button) =>
          // Reset the button's style
          button.setStyle("-fx-background-color: #DDDDDD; -fx-border-color: #000000;")
          gameHistory = gameHistory.init
          wordVisualize.setText(formWord())

          System.out.println(s"Undo last selection: ${lastSelectedButtonInfo._1}, row: ${lastSelectedButtonInfo._2._1}, col: ${lastSelectedButtonInfo._2._2}")
        case None =>
          System.out.println("Button not found")
      }
    } else {
      System.out.println("No selection to undo")
    }
  }

  def getButtonInfoClicked(): (String, Coord2D) = {
    val color = handleColorPicker()
    val buttonClicked = getButtonWhenHover()
    val buttonId = buttonClicked.getId
    val buttonChar = buttonClicked.getText
    val coord = buttonId.substring(6).toInt
    val row = (coord - 1) / 5
    val col = (coord - 1) % 5
    val currentSelection = (buttonChar, (row, col))

    if (gameHistory.nonEmpty && gameHistory.last == currentSelection) {
      undoLastSelection()
    } else if (checkCoordDifference((row, col)) && !buttonClicked.getStyle.contains(color)) {
      buttonClicked.setStyle(s"-fx-background-color: $color; -fx-border-color: #000000;")
      addToHistory(currentSelection)
      var word = formWord()
      var firstDir = directionToString(firstDirection())
      wordVisualize.setText(word)
      System.out.println(s"-fx-background-color: $color; -fx-border-color: #000000;")
      System.out.println(s"gameHistory: $gameHistory")
      System.out.println(s"Button clicked: $buttonChar, x: $row, y: $col")
      System.out.println(s"firstDir: $firstDir")
      System.out.println(s"word: $word")
    } else {
      System.out.println("Invalid move")
    }
    currentSelection
  }

  def getButtonWhenHover(): Button={
    val buttons = gamegrid.getChildren().stream()
      .filter(_.isInstanceOf[Button]).toArray().map(_.asInstanceOf[Button])
    val buttonClicked = buttons.filter(_.isHover).head
    buttonClicked
  }

  def checkCoordDifference(newCoord: Coord2D): Boolean = {
    gameHistory.lastOption match {
      case Some((_, lastCoord)) =>
        val rowDiff = Math.abs(lastCoord._2 - newCoord._2)
        val colDiff = Math.abs(lastCoord._1 - newCoord._1)
        rowDiff <= 1 && colDiff <= 1
      case None =>
        true
    }
  }

  def firstDirection(): Direction = {
    if (gameHistory.length < 2) {
      return null
    } else {
      val coords = gameHistory.map(_._2)
      val coord1 = coords(0)
      val coord2 = coords(1)
      val coord = (coord2._1 - coord1._1, coord2._2 - coord1._2)
      val firstDir = coordToDirection(coord)
      firstDir
    }
  }

  def addToHistory(charInfo: (String, Coord2D)): Unit = {
    if(gameHistory.isEmpty){
      gameHistory = gameHistory :+ charInfo
    } else {
      if (gameHistory.contains(charInfo)) {
        gameHistory = gameHistory.filterNot(_ == charInfo)
      } else {
        gameHistory = gameHistory :+ charInfo
      }
    }
  }

  def formWord(): String = {
    val word = gameHistory.map(_._1).mkString
    if (word.isEmpty) {
      " "
    } else {
      word
    }
  }

  def createNewGame(): Unit = {
    rand = myRandomGenerator()
    board = List.fill(5, 5)('-')
    infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", 1, board.length)
    boardWithWordsGUI = setBoardWithWords(board, infos._1, infos._2)
    gameBoardGUI = completeBoardRandomly(boardWithWordsGUI, rand, randomCharNotInList(infos._1))._1
    updateGrid(gameBoardGUI, infos._1)
    handleReset()
    showGame()
  }

  def handleReset(): Unit = {
    val buttons = gamegrid.getChildren().stream().filter(_.isInstanceOf[Button]).toArray().map(_.asInstanceOf[Button])
    gameHistory = List()
    wordVisualize.setText("")
    buttons.map(_.setStyle("-fx-background-color: #DDDDDD; -fx-border-color: #000000;"))
  }

  //--> SETTINGS ACTIONS
  @FXML
  def handleColorPicker(): String = {
    val color = bgColorPicker.getValue.toString.substring(2, 8)
    //System.out.println("#" + color)
    "#" + color
  }

}
