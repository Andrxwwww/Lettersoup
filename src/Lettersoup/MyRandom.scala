package Lettersoup

//T1 - Pure API for random number generation
case class MyRandom(seed: Long) {
    def nextInt(): (Int, MyRandom) = MyRandom.nextInt(seed)
    def nextInt(n: Int): (Int, MyRandom) = MyRandom.nextInt(n, seed)
    def shuffle(list: List[String]): List[String] = MyRandom.shuffle(list, seed)
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

  def shuffle (list: List[String], seed: Long): List[String] = {
    val r = MyRandom(seed)
    shuffleAux(list, r)._1
  }

  private def shuffleAux(list: List[String], r: MyRandom): (List[String], MyRandom ) = {
    if (list.isEmpty) (List(), r)
    else {
      val (index, newRand) = r.nextInt(list.length)
      val (newList, newRand2) = shuffleAux(list.patch(index, Nil, 1), newRand)
      (list(index) :: newList, newRand2)
    }
  }
}
