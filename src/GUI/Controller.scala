package GUI

import GUI.FxApp.{buttonsMap, gameBoardGUI, gameHistory, infos}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.layout.{AnchorPane, GridPane}
import javafx.stage.Stage
import Lettersoup.Utils.{Board, Coord2D, Direction}
import Random.SeedGenerator.myRandomGenerator
import Lettersoup.GameLogic.{checkBoard, completeBoardRandomly, play, randomChar, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords, randomCharNotInList}
import Lettersoup.Utils.Direction.{Direction, coordToDirection, directionToString, stringToDirection}
import Random.SeedGenerator.myRandomGenerator
import javafx.geometry.Insets
import javafx.scene.control.{Button, ButtonType, TextField}
import javafx.scene.input.MouseEvent
import javafx.scene.text.Font

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

class Controller {
  @FXML var mainMenuPane: AnchorPane = _
  @FXML var settingsPane: AnchorPane = _
  @FXML var gamePane: AnchorPane = _
  @FXML var gamegrid: GridPane = _
  @FXML var submitWord: Button = _
  @FXML var quitInGameButton: Button = _
  @FXML var wordVisualize: TextField = _
  @FXML var resetWord: Button = _

  def showSettings(): Unit = {
    mainMenuPane.setVisible(false)
    settingsPane.setVisible(true)
    gamePane.setVisible(false)
  }

  def showMainMenu():  Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(true)
    gamePane.setVisible(false)
  }

  def showGame(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(false)
    gamePane.setVisible(true)
  }

  def onExitClicked(): Unit = {
    val stage = mainMenuPane.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }

  @FXML
  def handlePlayButton(): Unit = {
    showGame()
    updateGrid(gameBoardGUI, infos._1 )
  }

  @FXML
  def handleGamePlayButton(): Unit = {
    if( play(gameBoardGUI, formWord()  , gameHistory.head._2, firstDirection()) && infos._1.contains(formWord() )){
      System.out.println("Word found")
      showMainMenu()
    } else {
      System.out.println("Word not found")
    }
  }

  def updateGrid(gameBoardGUI: Board , wordsToFind: List[String]): Unit = {
    if (checkBoard(gameBoardGUI, wordsToFind)) {
      for (i <- gameBoardGUI.indices; j <- gameBoardGUI(i).indices) {
        val index = i * 5 + j + 1
        val buttonId = s"Button$index"
        findButtonById(buttonId) match {
          case Some(button) => button.setText(gameBoardGUI(i)(j).toString)
          case None => println(s"Button not found: $buttonId")
        }
      }
    } else {
      System.out.println("Game Not Loaded Correctly :(")
    }
  }

  def findButtonById(id: String): Option[Button] = {
    val children = gamegrid.getChildren()
    //TODO: mudar isto
    children.collectFirst {
      case btn: Button if btn.getId == id => btn
    }
  }

  def getButtonWhenHover(): Button={
    val buttons = gamegrid.getChildren().stream().filter(_.isInstanceOf[Button]).toArray().map(_.asInstanceOf[Button])
    val buttonClicked = buttons.filter(_.isHover).head
    buttonClicked
  }


  def getButtonInfoClicked(): (String, Coord2D) = {
    val buttonClicked = getButtonWhenHover()
    val buttonId = buttonClicked.getId
    val buttonChar = buttonClicked.getText
    val coord = buttonId.substring(6).toInt
    val row = (coord - 1) / 5
    val col = (coord - 1) % 5
    if (checkCoordDifference((row, col)) && buttonClicked.getStyle != "-fx-background-color: #f56b0f; -fx-border-color: #000000;" ) {
      buttonClicked.setStyle("-fx-background-color: #f56b0f; -fx-border-color: #000000;")

      addToHistory((buttonChar, (row, col)))
      var word = formWord()
      var firstDir = directionToString(firstDirection())
      wordVisualize.setText(word)

      System.out.println(s"gameHistory: $gameHistory")
      System.out.println(s"Button clicked: $buttonChar, row: $row, col: $col")
      System.out.println(s"firstDir: $firstDir")
      System.out.println(s"word: $word")
    } else {
      System.out.println("Invalid move")
    }
    (buttonChar, (row, col))
  }

  def checkCoordDifference(newCoord: Coord2D): Boolean = {
    gameHistory.lastOption match {
      case Some((_, lastCoord)) =>
        val rowDiff = Math.abs(lastCoord._2 - newCoord._2)
        val colDiff = Math.abs(lastCoord._1 - newCoord._1)
        rowDiff <= 1 && colDiff <= 1
      case None =>
        true // If there are no previous coordinates, we consider it valid by default.
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
    word
  }

  def handleresetWord(): Unit = {
    val buttons = gamegrid.getChildren().stream().filter(_.isInstanceOf[Button]).toArray().map(_.asInstanceOf[Button])
    gameHistory = List()
    wordVisualize.setText("")
    buttons.foreach(_.setStyle("-fx-background-color: #DDDDDD; -fx-border-color: #000000;"))
  }

}
