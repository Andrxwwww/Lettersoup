package Lettersoup

// class with some util types and objects for the understanding of the code/game
object UtilStuff {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest = Value
  }

}
