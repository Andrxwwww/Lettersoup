package GUI

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
class Controller {
  @FXML
  private var playButton_mm: Button = _
  @FXML
  private var settingsButton_mm: Button = _
  @FXML
  private var exitButton_mm: Button = _
  @FXML
  private var backButton_settings: Button = _

  // Method for Play button action
  def onPlayClicked(): Unit = {
    // TODO
  }

  // Method for Settings button action
  def onSettingsClicked(): Unit = {
    // TODO
  }

  // Method for Exit button action
  def onExitClicked(): Unit = {
    val stage: Stage = exitButton_mm.getScene.getWindow.asInstanceOf[Stage]
    stage.close() // Closes the application window
  }

  // Method for Back button action
  def onBackClicked(): Unit = {
    val stage: Stage = backButton_settings.getScene.getWindow.asInstanceOf[Stage]
    val parent = FXMLLoader.load(getClass.getResource("MainMenu.fxml"))
    stage.setScene(new Scene(parent, 600, 400))
  }
}
