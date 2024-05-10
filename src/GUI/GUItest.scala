package GUI

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class GUItest extends Application {
  override def start(primaryStage: Stage): Unit = {
    val fxmlLoader = new FXMLLoader(getClass.getResource("MainMenu.fxml"))
    val root: Parent = fxmlLoader.load()

    primaryStage.setTitle("LetterSoup")
    primaryStage.setScene(new Scene(root, 600, 400))
    primaryStage.show()
  }
}

object FxApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[GUItest], args: _*)
  }
}

