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

  var gameHistory = List[(String , Coord2D)]()
  var startTimer = 0L

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[GUItest], args: _*)
  }
}

