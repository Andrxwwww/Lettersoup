package Lettersoup

trait RandomWithState {
  def nextInt(): (Int, RandomWithState)
  def nextInt(n: Int): (Int, RandomWithState)
}

case class MyRandom(seed: Long) extends RandomWithState{
    def nextInt(): (Int, MyRandom) = MyRandom.nextInt(seed)
    def nextInt(n: Int): (Int, MyRandom) = MyRandom.nextInt(n, seed)
}


object MyRandom {
  def nextInt(seed: Long): (Int, MyRandom) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRandom = MyRandom(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRandom)
  }

  def nextInt(n:Int, seed: Long): (Int, MyRandom) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRandom = MyRandom(newSeed)
    val nn = (newSeed >>> 16).toInt % n
    (if (nn < 0) -nn else nn, nextRandom)
  }
}
