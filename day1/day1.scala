import scala.io.Source._
import scala.collection.mutable.ListBuffer
val source = fromFile("input.txt")
val lines = source.getLines.toList
source.close

def sumCalories(group: ListBuffer[Int]): Int = {
  var sum = 0
  for(item <- group){
    sum = sum + item
  }
  sum
}

// now create list of lists with groups.
var groups: ListBuffer[ListBuffer[Int]] = ListBuffer()
var currentList: ListBuffer[Int] = ListBuffer()
for(line <- lines){
  line match 
  case "" => {
    groups += currentList
    currentList = ListBuffer()
  }
  case _ => {
    currentList += line.toInt
  }
}
val personCalories = groups.map(sumCalories).sorted
//val mostCaloriesPerPerson = personCalories.max
val personCalories = groups.map(sumCalories).sorted(Ordering.Int.reverse)
val topThree = personCalories(0) + personCalories(1) + personCalories(2)
println(topThree)



