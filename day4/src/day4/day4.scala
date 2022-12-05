package day4

import scala.io.Source._

object day4 extends App {
  val lines: List[Tuple2[String, String]] = getInput(false).map(getSections)
  val sections = lines.map(t => (sectionToCoverage(t._1), sectionToCoverage(t._2)))
  val answers = sections.map(t => doesIntersect(t._1, t._2))
  val answerCount = answers.count(p => p == true)

  def containsTheOtherPart1(section1: List[Int], section2: List[Int]): Boolean = {
    val union = section1.toSet.union(section2.toSet)
    if(union == section1.toSet || union == section2.toSet){
      return true
    }
    false
  }

  def doesIntersect(section1: List[Int], section2: List[Int]): Boolean = {
    val intersect = section1.toSet.intersect(section2.toSet)
    if(intersect.size > 0){
      return true
    }
    false
  }
  def sectionToCoverage(section: String): List[Int] = {
    val startEnd = section.split("-")
    val start = startEnd(0).toInt
    val end = startEnd(1).toInt
    if(start == end) return List(start)
    List.range(start, end) :+ end
  }
  def getSections(line: String): Tuple2[String, String] = {
    val split = line.split(",")
    (split(0), split(1))
  }
  def getInput(isTest: Boolean = true): List[String] = {
    val path = if(isTest)  "src/day4/inputTest.txt" else "src/day4/input.txt"
    val source = fromFile(path)
    val lines = source.getLines.toList
    source.close
    lines
  }
  /*
  Visually Inspect
  val toPrint = lines zip sections zip answers
  toPrint.foreach(println(_))
   */
  println(answerCount)
}


