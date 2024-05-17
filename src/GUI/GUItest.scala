package GUI

import Lettersoup.Utils.{Coord2D, Direction}
import javafx.application.Application
import javafx.fxml.FXMLLoader
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
  var numWords = 0
  var gameState = Game(5, numWords, "src/Lettersoup/Palavras.txt")

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[GUItest], args: _*)
  }
}

