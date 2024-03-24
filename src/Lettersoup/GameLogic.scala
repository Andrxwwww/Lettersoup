package Lettersoup

class GameLogic {

  //T1 - function that generates a random character
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val (nextInt, newRand) = rand.nextInt(26) // Gera um número aleatório entre 0 e 26
    val randomChar = ('A' + nextInt).toChar // Converte o número para um caractere entre 'A' e 'Z'
    (randomChar, newRand) // Retorna o caractere aleatório e o novo estado do gerador de números aleatórios
  }

}
object TestGameLogic {
  def main(args: Array[String]): Unit = {

    //test T1
    val gameLogic = new GameLogic()
    val rand = MyRandom(21) // para o output mudar a cada execução, só usar System.currentTimeMillis() na seed
    val (char, _) = gameLogic.randomChar(rand)
    println(char)
  }
}