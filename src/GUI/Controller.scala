package GUI

import javafx.fxml.FXML
import javafx.scene.layout.{AnchorPane}
import javafx.stage.Stage
class Controller {
  @FXML var mainMenuPane: AnchorPane = _
  @FXML var settingsPane: AnchorPane = _

  def showSettings(): Unit = {
    mainMenuPane.setVisible(false)
    settingsPane.setVisible(true)
  }

  def showMainMenu(): Unit = {
    settingsPane.setVisible(false)
    mainMenuPane.setVisible(true)
  }

  def onExitClicked(): Unit = {
    val stage = mainMenuPane.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }

}
