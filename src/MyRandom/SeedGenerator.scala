package MyRandom

import scala.io.Source
import java.io.PrintWriter

object SeedGenerator {

  private def getSeedFromFile(filename: String): Int = {
    val source = Source.fromFile(filename)
    val seed = try source.getLines().next().toInt finally source.close()
    seed
  }

  private def updateSeedInFile(filename: String, newSeed: Int): Unit = {
    val writer = new PrintWriter(filename)
    try writer.write(newSeed.toString) finally writer.close()
  }

  private def seedCore(seed: Int): Int = {
    val rand = MyRandom(seed)
    val (_ , rand1) = rand.nextInt(seed)

    val newSeed = rand1.nextInt(1000)._1
    updateSeedInFile("src/MyRandom/seed.txt", newSeed)
    newSeed
  }

  def randomIntFunction(): Int = {
    val seed = getSeedFromFile("src/MyRandom/seed.txt")
    seedCore(seed)
  }

  def main(args: Array[String]): Unit = {
    print(randomIntFunction())
  }

}
