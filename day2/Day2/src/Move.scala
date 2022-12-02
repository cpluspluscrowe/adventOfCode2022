

object Move extends Enumeration {
  protected case class MoveVal(play: String) extends super.Val {
  }

  import scala.language.implicitConversions

  val TheirRock = MoveVal("A")
  val TheirPaper = MoveVal("B")
  val TheirSissors = MoveVal("C")

  val MyRock = MoveVal("X")
  val MyPaper = MoveVal("Y")
  val MySissors = MoveVal("Z")

  // tech-debt, add enums for Lose, Tie, and Win
  val Lose = MoveVal("D")
  val Tie = MoveVal("E")
  val Win = MoveVal("F")
  def moveToOutcome(move: Move.Value): Move.Value = {
    move match {
      case MyRock => Lose
      case MyPaper => Tie
      case MySissors => Win
      case _ => throw new Exception(s"Invalid input move: $move")
    }
  }
  def getPoints(move: Move.Value): Int = {
    move match {
      case TheirRock => 1
      case TheirPaper => 2
      case TheirSissors => 3
      case MyRock => 1
      case MyPaper => 2
      case MySissors => 3
      case _ => throw new Exception(s"Invalid move input: $move")
    }
  }
  def getMyMove(opponent: Move.Value, outcome: Move.Value): Move.Value = {
    (outcome, opponent) match {
      case (Win, TheirRock) => MyPaper
      case (Win, TheirPaper) => MySissors
      case (Win, TheirSissors) => MyRock
      case (Tie, TheirRock) => MyRock
      case (Tie, TheirPaper) => MyPaper
      case (Tie, TheirSissors) => MySissors
      case (Lose, TheirRock) => MySissors
      case (Lose, TheirPaper) => MyRock
      case (Lose, TheirSissors) => MyPaper
      case _ => throw new Exception(s"Invalid combination: $outcome, $opponent")
    }
  }
  def doIWin(them: Move.Value, me: Move.Value): Int = {
    (them, me) match{
      case (TheirRock, MyPaper) => 1
      case (TheirRock, MySissors) => -1
      case (TheirPaper, MyRock) => -1
      case (TheirPaper, MySissors) => 1
      case (TheirSissors, MyRock) => 1
      case (TheirSissors, MyPaper) => -1
      // handle ties
      case(TheirSissors, MySissors) => 0
      case(TheirPaper, MyPaper) => 0
      case(TheirRock, MyRock) => 0
      case _ => throw new Exception(s"Invalid RPS Combination: $them, $me")
    }
  }
  def toEnum(move: String): Move.Value = {
    move match {
      case "A" => Move.TheirRock
      case "B" => Move.TheirPaper
      case "C" => Move.TheirSissors
      case "X" => Move.MyRock
      case "Y" => Move.MyPaper
      case "Z" => Move.MySissors
      case _ => throw new Exception(s"invalid input to toEnum: $move")
    }
  }
  def winValue(input: Tuple2[Move.Value, Move.Value]): Int = {
    val opponent = input._1
    val me = input._2
    val comparison = doIWin(opponent, me)
    comparison match {
      case 1 => 6
      case 0 => 3
      case -1 => 0
      case _ => throw new Exception("Should not occur")
    }
  }
}

