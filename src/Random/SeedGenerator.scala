package Random

import java.io._

object SeedGenerator {

  def getSeedFromFile(filename: String): MyRandom = {
    val ois = new ObjectInputStream(new FileInputStream(filename))
    val myRandom = try ois.readObject().asInstanceOf[MyRandom] finally ois.close()
    myRandom
  }

  def updateSeedInFile(filename: String, newSeed: MyRandom): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(filename))
    try oos.writeObject(newSeed) finally oos.close()
  }

  def seedCore(seed: MyRandom): MyRandom = {
    val rand = seed
    val (randomInt , newRand) = rand.nextInt(1000)
    updateSeedInFile("src/Random/seedBinary.txt", newRand)
    newRand
  }

  def myRandomGenerator(): MyRandom = {
    val seed = getSeedFromFile("src/Random/seedBinary.txt")
    seedCore(seed)
  }

  def main(args: Array[String]): Unit = {
    //updateSeedInFile("src/Random/seedBinary.txt", MyRandom(1))
    print(myRandomGenerator())
  }
}
