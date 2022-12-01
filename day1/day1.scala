import scala.io.Source._
import scala.collection.mutable.ListBuffer

object CalorieHungryElves extends App {
  val lines = loadLines()
  val groups = generateGroups(lines)
  val personCalories = groups.map(sumCalories).sorted(Ordering.Int.reverse)
  val topThree = personCalories(0) + personCalories(1) + personCalories(2)
  println(topThree)

  def loadLines(): List[String] = {
    val source = fromFile("input.txt")
    val lines = source.getLines.toList
    source.close
    lines
  }

  def sumCalories(group: ListBuffer[Int]): Int = {
    var sum = 0
    for (item <- group) {
      sum = sum + item
    }
    sum
  }

  def generateGroups(lines: List[String]): ListBuffer[ListBuffer[Int]] = {
    var groups: ListBuffer[ListBuffer[Int]] = ListBuffer()
    var currentList: ListBuffer[Int] = ListBuffer()
    for (line <- lines) {
      line match
        case "" => {
          groups += currentList
          currentList = ListBuffer()
        }
        case _ => currentList += line.toInt
    }
    groups
  }
}
