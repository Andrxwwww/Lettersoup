package Lettersoup

// class with some util types and objects for the understanding of the code/game
object Utils {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    private val North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest = Value

    def coordToDirection(coord: Coord2D): Direction = {
      coord match {
        case (-1, 0) => North
        case (1, 0) => South
        case (0, 1) => East
        case (0, -1) => West
        case (-1, 1) => NorthEast
        case (-1, -1) => NorthWest
        case (1, 1) => SouthEast
        case (1, -1) => SouthWest
      }
    }

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

    def directionToString(direction: Direction): String = {
      direction match {
        case North => "N"
        case South => "S"
        case East => "E"
        case West => "W"
        case NorthEast => "NE"
        case NorthWest => "NW"
        case SouthEast => "SE"
        case SouthWest => "SW"
        case _ => "no direction"
      }
    }

    def oppositeDirection(direction: Direction): Direction = {
      direction match {
        case North => South
        case South => North
        case East => West
        case West => East
        case NorthEast => SouthWest
        case NorthWest => SouthEast
        case SouthEast => NorthWest
        case SouthWest => NorthEast
      }
    }


  }

}
