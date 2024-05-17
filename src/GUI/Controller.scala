package GUI

import GUI.FxApp.{gameHistory, gameState, numWords, startTimer}
import javafx.fxml.FXML
import javafx.scene.layout.{AnchorPane, GridPane, Pane}
import javafx.stage.Stage
import Lettersoup.Utils.{Board, Coord2D}
import Lettersoup.GameLogic.{checkBoard, play}
import Lettersoup.Utils.Direction.{Direction, coordToDirection, directionToString}
import javafx.scene.Parent
import javafx.scene.control.{Button, ColorPicker, Label, TextField}

import scala.util.Try

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
  @FXML var numWordField: TextField = _
  @FXML var numOfWordsPane: Pane = _
  @FXML var numOfWordsPaneAnchor: AnchorPane = _


  // --> SHOWING PANES
  def showSettings(): Unit = {
    mainMenuPane.setVisible(false)
    settingsPane.setVisible(true)
    gamePane.setVisible(false)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
    numOfWordsPane.setVisible(false)
    numOfWordsPaneAnchor.setVisible(false)
  }

  def showMainMenu():  Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(true)
    gamePane.setVisible(false)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
    numOfWordsPane.setVisible(false)
    numOfWordsPaneAnchor.setVisible(false)
    handleReset()
  }

  def showGame(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(true)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
    numOfWordsPane.setVisible(false)
    numOfWordsPaneAnchor.setVisible(false)
  }

  def showWinGame(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(true)
    winGame.setVisible(true)
    boardFailedPop.setVisible(false)
    numOfWordsPane.setVisible(false)
    numOfWordsPaneAnchor.setVisible(false)
  }

  def showFailedLoad(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(false)
    winGame.setVisible(false)
    boardFailedPop.setVisible(true)
    numOfWordsPane.setVisible(false)
    numOfWordsPaneAnchor.setVisible(true)
  }

  def showNumOfWords(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(false)
    winGame.setVisible(false)
    boardFailedPop.setVisible(false)
    numOfWordsPane.setVisible(true)
    numOfWordsPaneAnchor.setVisible(true)
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
    showNumOfWords()
  }

  @FXML
  def handleInsertWords(): Unit = {

    val buttonForStartGame = getButtons(numOfWordsPane)
    val buttonClicked = buttonForStartGame.find(_.isHover)

    buttonClicked match {
      case Some(button) => button.getText() match {
        case "Start" =>
          canConvertToInt(numWordField.getText) match {
            case true =>
              numWords = numWordField.getText.toInt
              if (numWords > 0 && numWords <= 9) {
                showGame()
                createNewGame(numWords)
              } else {
                //System.out.println("Invalid number of words")
              }
            case false => showFailedLoad()
              //System.out.println("Invalid input")
          }
      }
    }
  }

  def canConvertToInt(s: String): Boolean = Try(s.toInt).isSuccess

  //--> GAME ACTIONS
  @FXML
  def handleGamePlayButton(): Unit = {
    var words = gameState.getWords

    gameHistory.headOption match {
      case Some(head) =>
        if (play(gameState.getGameBoard, formWord(), head._2, firstDirection()) && words.contains(formWord())) {
          val wordsToFind = words.filterNot(_ == formWord())
          if (wordsToFind.isEmpty) {
            System.out.println("All words found")
            val endTimer = System.currentTimeMillis()
            val score = 100000 - (endTimer - startTimer).toInt
            scoreID.setText(score.toString)
            showWinGame()
          } else {

            System.out.println("Word found")
            gameState.updateWords(wordsToFind)
            handleReset()
            wordVisualize.setText("Word found")
          }
        } else {

          System.out.println("Word not found")
          handleReset()  // Reset game state if necessary
          wordVisualize.setText("Wrong word :(")
        }
      case None =>
        System.out.println("No game history available, cannot proceed.")
    }
  }

  @FXML
  def handleRepeatGame(): Unit = {
    val buttonsWGamePop = getButtons(winGame)
    val buttonClicked = buttonsWGamePop.find(_.isHover)

    buttonClicked match {
      case Some(button) => button.getText() match {
        case "Yes" =>
          startTimer = System.currentTimeMillis()
          showGame()
          createNewGame(numWords)
        case "No" =>
          createNewGame(numWords)
          showMainMenu()
      }
    }
  }


  @FXML
  def handleGameFailedLoad(): Unit = {
    val buttonsForRetryGame = getButtons(boardFailedPop)
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
      //System.out.println("Game Not Loaded Correctly :(")
      showFailedLoad()
    }
  }

  def findButtonByInfo(info: (String, Coord2D)): Option[Button] = {
    val (char, (row, col)) = info
    val coord = row * 5 + col + 1
    val buttonId = s"Button$coord"

    val children = gamegrid.getChildren()  // This returns a JavaFX ObservableList[Node]

    def findRecursive(index: Int): Option[Button] = {
      if (index >= children.size()) None
      else children.get(index) match {
        case btn: Button if btn.getId == buttonId => Some(btn)
        case _ => findRecursive(index + 1)
      }
    }

    findRecursive(0)
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

          //System.out.println(s"Undo last selection: ${lastSelectedButtonInfo._1}, row: ${lastSelectedButtonInfo._2._1}, col: ${lastSelectedButtonInfo._2._2}")
        case None =>
          //System.out.println("Button not found")
      }
    } else {
      //System.out.println("No selection to undo")
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
      //System.out.println(s"-fx-background-color: $color; -fx-border-color: #000000;")
      //System.out.println(s"gameHistory: $gameHistory")
      //System.out.println(s"Button clicked: $buttonChar, x: $row, y: $col")
      //System.out.println(s"firstDir: $firstDir")
      //System.out.println(s"word: $word")
    } else {
      //System.out.println("Invalid move")
    }
    currentSelection
  }

  def getButtonWhenHover(): Button={
    val buttons = getButtons(gamegrid)
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

  def createNewGame(n: Int): Unit = {
    gameState.reset(n)
    updateGrid(gameState.getGameBoard, gameState.getWords)
    handleReset()
  }

  def handleReset(): Unit = {
    val buttons = getButtons(gamegrid)
    gameHistory = List()
    wordVisualize.setText("")
    buttons.map(_.setStyle("-fx-background-color: #DDDDDD; -fx-border-color: #000000;"))
  }

  def getButtons(parent: Parent): Array[Button] = {
    parent.getChildrenUnmodifiable.stream()
      .filter(_.isInstanceOf[Button])
      .toArray
      .map(_.asInstanceOf[Button])
  }

  //--> SETTINGS ACTIONS
  @FXML
  def handleColorPicker(): String = {
    val color = bgColorPicker.getValue.toString.substring(2, 8)
    //System.out.println("#" + color)
    "#" + color
  }

}
