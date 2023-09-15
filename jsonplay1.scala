//> using dep com.lihaoyi::upickle::3.1.2
//> using dep com.lihaoyi::pprint::0.8.1

import upickle.default.*

object Main:
    
  case class Employee(name: String, age: Int, salary: Int) derives ReadWriter
  
  def objectToJson(employees: Seq[Employee]): String =
    write(employees)

  def jsonToObject(json: String): Seq[Employee] =
    read[Seq[Employee]](json)
    
  def main(args:Array[String]) =

    val e1 = Employee("John", 30, 1000)
    val e2 = Employee("Peter", 20, 2000)
    val e3 = Employee("Mark", 50, 3000)
    val employees = List(e1, e2, e3)

    // print the list of employees
    println("List of employees:")
    employees.foreach(println)
    println()

    println("about to serialize to json")
    val json1 = objectToJson(employees)
    println("done serialize to json")
    println(json1)
    println()

    println("about to deserialize from json")
    val employees3 = jsonToObject(json1)
    println("done deserialize from json")
    println("New employees from json:")
    employees3.foreach(println)
    
    val words = Array (
      "aa",
      "ala",
      "able",
      "bale",
      "cage",
      "zeta",
      "zygote"
    )
    
    // jsonify it
    val json2 = write(words)
    
    // print it
    println(json2)
    
