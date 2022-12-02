
import scala.io.Source._

object day2 extends App {
  def partA(): Int = {
    val lines = loadInput()
    val plays = lines.map(getPlays)
    sharedLogic(plays)
  }

  def partB(): Int = {
    val lines = loadInput()
    val inputPlays = lines.map(getPlays)
    val opponentPlays: List[Move.Value] = inputPlays.map(_._1)
    val myOldPlays: List[Move.Value] = inputPlays.map(_._2)
    val desiredOutcome: List[Move.Value] = myOldPlays.map(Move.moveToOutcome)
    val combineOpponentAndOutcome: List[(Move.Value, Move.Value)] = opponentPlays zip desiredOutcome
    val myPlays = combineOpponentAndOutcome.map(t => Move.getMyMove(t._1, t._2))
    val plays = opponentPlays zip myPlays
    sharedLogic(plays)
  }

  def sharedLogic(plays: List[Tuple2[Move.Value,Move.Value]]): Int = {
    val winPoints = plays.map(Move.winValue)
    val movePoints = plays.map(m => Tuple2(Move.getPoints(m._1), Move.getPoints(m._2)))
    val totalPoints = winPoints.sum + movePoints.map(t => t._2).sum
    totalPoints
  }
  println(partA())
  println(partB())

  def getPlays(line: String): Tuple2[Move.Value, Move.Value] = {
    val plays = line.split(" ").map(Move.toEnum)
    new Tuple2(plays(0),plays(1))
  }
  def loadInput(): List[String] = {
    val source = fromFile("src/input.txt")
    val lines = source.getLines.toList
    source.close
    lines
  }
}

