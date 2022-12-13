import scala.io.Source._
import scala.collection.mutable._

val path = "./input.txt"
val source = fromFile(path)
val lines = source.getLines.toList
source.close

sealed trait LineType(line: String) {
  val classification = "nothing"
}

class Command(line: String) extends LineType(line: String) {
  override val classification = "command"
  val spl = line.split(" ")
  val command = spl(1)
  var where = ""
  if(spl.size == 3){
    where = spl(2)
  }
}

class Directory(line: String, val parentDirectory: Directory = null) {
  val name = if (line.contains("$") || line.contains("dir"))  then line.split(" ")(1) else line
  var children = ListBuffer[Directory]()
  var files = ListBuffer[File]()
  def getOrCreateChild(name: String): Directory = {
    val child = children.filter(c => c.name == name)
    if(child.isEmpty){
      val newDir = Directory(name, this)
      children += newDir
      newDir
    }else{
      return child.head
    }
  }
  def getFileSize(): Int = {
    var sum = 0
    for(file <- files){
      sum += file.size
    }
    for(child <- children){
      sum += child.getFileSize()
    }
    sum
  }
}

class File(line: String) extends LineType(line: String) {
  val spl = line.split(" ")
  val size = spl(0).toInt
  val name = spl(1)
}

object LineParser {

def isCommand(line: String) : Boolean = {
  val spl = line.split(" ")
  spl(0) match {
    case "$" => true
    case _ => false
  }
}

def isDir(line: String): Boolean = {
  return line.split(" ")(0) == "dir"
}

def isFile(line: String): Boolean = {
  if line.split(" ")(0).toIntOption.isEmpty then false else true
}

// now let's also populate data about the command in the class instance.

def classifyLines[A <: LineType](line: String): Any = {
  if(LineParser.isCommand(line)) return Command(line)
  else if(LineParser.isDir(line)) return Directory(line)
  else if(LineParser.isFile(line)) return File(line)
  else {
    throw new Exception(s"This $line should not be reached")
  }
}
}

val types = lines.map(LineParser.classifyLines)
val both = lines zip types
val topDirectory = Directory("/")
var currentDirectory: Directory = topDirectory
for((line, lineType) <- both){
  if(lineType.isInstanceOf[Command]){
    val instance = lineType.asInstanceOf[Command]
    if(instance.command.equals("cd")){
      if(instance.where.equals("..")){
        currentDirectory = currentDirectory.parentDirectory
      }else if(instance.where.equals("/")){
        currentDirectory = topDirectory
      }else{
        currentDirectory = currentDirectory.getOrCreateChild(instance.where)
      }
    }else if(instance.command.equals("ls")){
      // grab the files later
    }else throw new Exception(s"Invalid command: ${instance.command}")
  }else if(lineType.isInstanceOf[File]){
    val instance = lineType.asInstanceOf[File]
    val file = File(line)
    currentDirectory.files += file
  }else if(lineType.isInstanceOf[Directory]){
    val instance = lineType.asInstanceOf[Directory]
    val directory = Directory(instance.name, currentDirectory)
    currentDirectory.children += directory
  }
}



while(currentDirectory.parentDirectory != null){
  currentDirectory = currentDirectory.parentDirectory
}

val space = 70000000 - currentDirectory.getFileSize()

def printStats(directory: Directory): Int = {
  val dirSize = directory.getFileSize()
  var smallest = 70000001
  val toBeFreed = space + dirSize
  if(toBeFreed >= 30000000){
    println(s"${directory.name}: $dirSize")
    smallest = (toBeFreed).min(smallest)
  }  
  for(child <- directory.children){
    smallest = smallest.min(printStats(child))
  }
  smallest
}

val answer = printStats(currentDirectory)
println(answer - space)


