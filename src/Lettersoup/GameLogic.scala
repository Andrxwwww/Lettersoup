package Lettersoup

import Lettersoup.UtilFunctions.validPosition
import Lettersoup.Utils.{Board, Coord2D, Direction}
import Lettersoup.Utils.Direction.{Direction, directionToCoord, oppositeDirection}
import Random.MyRandom

// class for the game logic
object GameLogic {
  /* T1
  _randomChar_
  - @param: rand -> a random number generator
  - description: function that generates a random char
  - @return: a tuple with a random char and a new random number generator
   */
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val (nextInt, newRand) = rand.nextInt(26) // Gera um número aleatório entre 0 e 26
    val randomChar = ('A' + nextInt).toChar // Converte o número para um caractere entre 'A' e 'Z'
    (randomChar, newRand) // Retorna o caractere aleatório e o novo estado do gerador de números aleatórios
  }

  /* T2
  _fillOneCell_
  - @param: board -> the board
            coord -> the coordinate to fill
            char -> the char to fill the coordinate
  - description: function that associates a random char to a coordinate
  - @return: the board with the coordinate filled with the char
   */
  private def fillOneCell(board: Board, coord: Coord2D, char: Char): Board = {
    if (coord._1 < 0 || coord._1 >= board.length || coord._2 < 0 || coord._2 >= board.head.length || char == '-' ) {
      //se tiver fora do tabuleiro ou não for um char válido, retorna o tabuleiro sem alterações
      board
    } else {
      board.updated(coord._1, board(coord._1).updated(coord._2, char))
    }
  }
  /* T3
  _setBoardWithWords_
  - @param: board -> the board
            words -> the list of words to fill the board
            positions -> the list of positions to fill the words
  - description: function that fills each char of a word in the board
  - @return: the board with the words filled
   */
  def setBoardWithWords(board: Board, words: List[String], positions: List[List[Coord2D]]): Board = {
    words.zip(positions).foldLeft(board) { case (b, (word, positionList)) =>
      word.zip(positionList).foldLeft(b) { case (b, (char, position)) =>
        fillOneCell(b, position, char)
      }
    }
  }
  /* T4
  _completeBoardRandomly_
  - @param: board -> the board
            r -> a random number generator
            f -> a function that generates a random char
  - description: function that completes the board with random chars
  [other part of T4 is in UtilFuncs for use in f param]
  - @return: a tuple with the board completed and a new random number generator
   */
  def completeBoardRandomly(board: Board, r: MyRandom, f: MyRandom => (Char, MyRandom)): (Board, MyRandom) = {
    val (newBoard, newRand) = board.indices.foldLeft((board, r)) { case ((b, rand), i) =>
      b(i).indices.foldLeft((b, rand)) { case ((bb, rrand), j) =>
        if (bb(i)(j) == '-') {
          val (char, newRand) = f(rrand)
          (fillOneCell(bb, (i, j), char), newRand)
        } else (bb, rrand)
      }
    }
    (newBoard, newRand)
  }

  // TODO REDO T5 AND T6
  /* T5
  _play_
  - @param: board -> the board
            word -> the word to find
            coordInitial -> the initial coordinate to start the search
            direction -> the direction to search
  - description: function that checks if a word is in the board
  - @return: true if the word is in the board, false otherwise
   */
  def play(board:Board, word:String, coord:Coord2D, direction: Direction): Boolean = {
    val remainWord = word.tail
    val nextCoord = (coord._1 + directionToCoord(direction)._1, coord._2 + directionToCoord(direction)._2)

    def charAt(letter: Char, coord: Coord2D, board: Board): Boolean = {
      val (x, y) = coord
      if (validPosition(coord,board)) {
        board(x)(y) == letter
      } else {
        false
      }
    }

    (validPosition(coord,board),remainWord) match {
      case (_, "") => true
      case (false, _) => false
      case (true, _) if word.nonEmpty =>
        val letter = word.tail.head
        if (charAt(letter, nextCoord, board)) {
          val remainDirs = Direction.values.toList.filter(_ != oppositeDirection(direction))
          remainDirs.exists(dir => play(board, remainWord, nextCoord, dir))
        } else {
          false
        }
    }
  }

  /* T6 (V2)
  _checkBoard_
  - @param: board -> the board
            wordsToFind -> the list of words to find
  - description: function that checks if the board has no repeated words and has all the words
  - @return: true if the board has all the words, false otherwise
   */
  /*
  Todo: se houver uma palavra fora da lista de palavras
    que esteja no board e que esteja repetida com uma que esteja no board
    a função vai retornar true porque so checka com as palavras da lista e nao com o board
   */
  def checkBoard(board: Board, wordsToFind: List[String]): Boolean = {
    def checkWord(word: String): Boolean = {
      board.indices.exists { x =>
        board(x).indices.exists { y =>
          Direction.values.exists { direction =>
            play(board, word, (x, y), direction)
          }
        }
      }
    }

    def checkAllWords(words: List[String]): Boolean = words match {
      case Nil => true
      case head :: tail =>
        val result = checkWord(head) // Check the current word
        result && checkAllWords(tail) // Recursively check the rest, combining results with AND
    }

    val allWordsCanBePlayed = checkAllWords(wordsToFind)
    val noDuplicates = wordsToFind.distinct.length == wordsToFind.length //TODO: need fix

    allWordsCanBePlayed && noDuplicates
  }




}
