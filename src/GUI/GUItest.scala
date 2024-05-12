package GUI

import Lettersoup.GameLogic.{completeBoardRandomly, setBoardWithWords}
import Lettersoup.UtilFunctions.{getWordsAndCoords, randomCharNotInList}
import Lettersoup.Utils.{Coord2D, Direction}
import Lettersoup.Utils.Direction.Direction
import Random.SeedGenerator.myRandomGenerator
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class GUItest extends Application {
  override def start(primaryStage: Stage): Unit = {
    val fxmlLoader = new FXMLLoader(getClass.getResource("LettersoupGUI.fxml"))
    val root: Parent = fxmlLoader.load()

    primaryStage.setTitle("LetterSoup")
    primaryStage.setScene(new Scene(root, 600, 400))
    primaryStage.show()
  }
}

object FxApp {

  var buttonsMap = Map[String, Button]() // Map to hold buttons and their IDs
  var rand = myRandomGenerator()
  var board = List.fill(5, 5)('-')
  var infos = getWordsAndCoords("src/Lettersoup/Palavras.txt", 1, board.length)
  var boardWithWordsGUI = setBoardWithWords(board, infos._1, infos._2)
  var gameBoardGUI = completeBoardRandomly(boardWithWordsGUI, rand, randomCharNotInList(infos._1))._1
  var gameHistory = List[(String , Coord2D)]()

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[GUItest], args: _*)
  }
}

