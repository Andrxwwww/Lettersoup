# Lettersoup Game 
MainMenu                                                                                                 |  Game
:-------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:
![image](https://github.com/Andrxwwww/Lettersoup/assets/117539407/01196654-9890-41ca-b949-bfa2db0dd687)  |  ![image](https://github.com/Andrxwwww/Lettersoup/assets/117539407/deb664ff-977d-4a5d-843c-09b42cb09917)

## Overview
This project was programmed using scala language
The GUI is implemented using JavaFX and controls various game states such as the main menu, settings, game board, and game outcome screens. 
The gameLogic itself is on Lettersoup package , plus with a TUI , there's also a MyRandom class which is like a Random class but made by myself.

## Features
- __Main Menu:__ Start a new game, access settings, and exit the application.
- __Settings:__ Customize the game background color.
- __Game Board:__ Visual representation of the Lettersoup game.
- __Game Outcomes:__ Display win or fail states and handle game reset or retry.
- __Word Selection:__ Visualize and check word selections made by the player.

## File Structure
The main class file for the GUI controller is:
- __Controller.scala:__ Contains the logic for managing the GUI and game interactions.
- __Game.scala:__ Represents the game/Board with the respective keywords , size of the board , random chars in it ...
- __GUItest.scala:__ To run the GUI
- __LettersoupGUI.fxml:__ where it's designed the GUI + the variables

## How to Run
1. Ensure you have Java and Scala installed on your machine.
2. Set up JavaFX library in your project.
3 .Compile and run the application using your preferred IDE or build tool (e.g., sbt, IntelliJ IDEA).

## Methods in the Controller.scala
### Fields
- AnchorPane mainMenuPane, settingsPane, gamePane, numOfWordsPane, numOfWordsPaneAnchor
- GridPane gamegrid
- Pane winGame, boardFailedPop
- TextField wordVisualize, numWordField
- ColorPicker bgColorPicker
- Label scoreID
### Methods
- showSettings(): Displays the settings pane.
- showMainMenu(): Displays the main menu pane and resets the game.
- showGame(): Displays the game pane.
- showWinGame(): Displays the win game pane.
- showFailedLoad(): Displays the board failed popup.
- showNumOfWords(): Displays the number of words input pane.
- onExitClicked(): Closes the application.
- handlePlayButton(): Starts the game timer and shows the number of words input pane.
- handleInsertWords(): Validates the number of words input and starts the game if valid.
- handleGamePlayButton(): Handles the gameplay logic including word checking and updating the game state.
- handleRepeatGame(): Handles the logic for repeating the game after winning.
- handleGameFailedLoad(): Handles the logic for retrying the game after a failed load.
- updateGrid(Board, List[String]): Updates the game board GUI with the current game state.
- findButtonByInfo((String, Coord2D)): Finds a button in the game grid by its information.
- undoLastSelection(): Undoes the last selection made by the player.
- getButtonInfoClicked(): Gets the information of the button clicked by the player.
- getButtonWhenHover(): Gets the button currently hovered by the player.
- checkCoordDifference(Coord2D): Checks if the new coordinate difference is valid.
- firstDirection(): Gets the first direction of the word formed.
- addToHistory((String, Coord2D)): Adds a character and its coordinate to the game history.
- formWord(): Forms a word from the game history.
- createNewGame(Int): Creates a new game with the specified number of words.
- handleReset(): Resets the game state and GUI.
- getButtons(Parent): Gets all buttons within a parent node.
- handleColorPicker(): Handles the color picker action and sets the background color.
## Dependencies
- JavaFX
- Scala
