package Random

import java.io._

object SeedGenerator {


  def getSeedFromFile(filename: String): MyRandom = {
    val file = new File(filename)
    if (file.exists() && file.length() > 0) {
      val ois = new ObjectInputStream(new FileInputStream(file))
      try {
        ois.readObject().asInstanceOf[MyRandom]
      } finally {
        ois.close()
      }
    } else {
      MyRandom(123) // Provide initial seed if file doesn't exist
    }
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
