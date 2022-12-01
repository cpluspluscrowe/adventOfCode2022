import scala.io.Source._
import scala.collection.mutable.ListBuffer

def sumCalories(group: ListBuffer[Int]): Int = {
  var sum = 0
  for(item <- group){
    sum = sum + item
  }
  sum
}

def generateGroups(lines: List[String]): ListBuffer[ListBuffer[Int]] = {
  var groups: ListBuffer[ListBuffer[Int]] = ListBuffer()
  var currentList: ListBuffer[Int] = ListBuffer()
  for(line <- lines){
    line match
    case "" => {
      groups += currentList
      currentList = ListBuffer()
    }
    case _ => currentList += line.toInt
  }
  groups
}

val source = fromFile("input.txt")
val lines = source.getLines.toList
source.close
val groups = generateGroups(lines)
val personCalories = groups.map(sumCalories).sorted(Ordering.Int.reverse)
//val mostCaloriesPerPerson = personCalories.max
val topThree = personCalories(0) + personCalories(1) + personCalories(2)



