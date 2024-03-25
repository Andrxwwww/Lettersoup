package Lettersoup

// class with some util types and objects for the understanding of the code/game
object UtilStuff {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest = Value
  }
  object DirectionVector {
    val North = (-1, 0)
    val South = (1, 0)
    val East = (0, 1)
    val West = (0, -1)
    val NorthEast = (-1, 1)
    val NorthWest = (-1, -1)
    val SouthEast = (1, 1)
    val SouthWest = (1, -1)
  }

}
