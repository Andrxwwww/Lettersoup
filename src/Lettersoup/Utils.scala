package Lettersoup

// class with some util types and objects for the understanding of the code/game
object Utils {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest = Value

    //TA - function that associates a coord. diretion vector to a Direction
    def directionToCoord(direction: Direction): Coord2D = {
      direction match {
        case North => (-1, 0)
        case South => (1, 0)
        case East => (0, 1)
        case West => (0, -1)
        case NorthEast => (-1, 1)
        case NorthWest => (-1, -1)
        case SouthEast => (1, 1)
        case SouthWest => (1, -1)
      }
    }

    //TA - function that associates a string to a Direction
    def stringToDirection(direction: String): Direction = {
      direction match {
        case "N" => North
        case "S" => South
        case "E" => East
        case "W" => West
        case "NE" =>NorthEast
        case "NW" =>NorthWest
        case "SE" =>SouthEast
        case "SW" =>SouthWest
      }
    }
  }

}
