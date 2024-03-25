package Lettersoup

class BackUtil {
  //T1 - function that generates a random character
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val (nextInt, newRand) = rand.nextInt(26) // Gera um número aleatório entre 0 e 26
    val randomChar = ('A' + nextInt).toChar // Converte o número para um caractere entre 'A' e 'Z'
    (randomChar, newRand) // Retorna o caractere aleatório e o novo estado do gerador de números aleatórios
  }

}
