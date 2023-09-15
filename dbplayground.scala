//> using lib org.xerial:sqlite-jdbc:3.43.0.0

import java.sql.{Connection, DriverManager, ResultSet};
import simplesql as sq // for new way of doing things
import org.sqlite.SQLiteDataSource // for new way of doing things


  
  
  
// val wordsFile = "words.txt"

// val words = Array (
//     "aa",
//     "ala",
//     "able",
//     "bale",
//     "cage",
//     "zeta",
//     "zygote"
// )

//  print("Enter something:")
// val input = scala.io.StdIn.readLine()
// println(s"You entered: $input")


// for (word <- words) {
//     println(s"${word.sorted} $word")
// }




object Main:
    
  case class Employee(name: String, age: Int, salary: Int) derives sq.Reader

  val url = "jdbc:sqlite:employees.db"
  val driver = "org.sqlite.JDBC"

  def makedb(employees: Seq[Employee]) =
    Class.forName(driver)
    var connection:Connection = DriverManager.getConnection(url)
    try
      val statement = connection.createStatement

      statement.executeUpdate("drop table if exists employees")
      statement.executeUpdate("create table employees (name string, age int, salary int)")
      
      for employee <- employees do
        statement.executeUpdate(s"insert into employees values('${employee.name}', ${employee.age}, ${employee.salary})")

    catch
      case e: Exception => e.printStackTrace
    finally
      connection.close
      

  def dbToEmployees(url: String, driver: String) : Seq[Employee] =
    var employees = Seq[Employee]()

    Class.forName(driver)
    var connection:Connection = DriverManager.getConnection(url)

    try
      val statement = connection.createStatement
      val resultSet = statement.executeQuery("select * from employees")
      while resultSet.next do
        val name = resultSet.getString("name")
        val age = resultSet.getInt("age")
        val salary = resultSet.getInt("salary")
        employees = employees :+ Employee(name, age, salary)
      employees
    catch
      case e: Exception => e.printStackTrace
    finally
      connection.close 
    employees  
    
         

  def dbToEmployees2(url: String) : Seq[Employee] =
    var employees = Seq[Employee]()
    
    val ds = SQLiteDataSource()
        ds.setUrl(url)

        sq.transaction(ds) {
            employees = sq.read[Employee](sql"select * from employees")
        }    
    
    employees
    

    
  def main(args:Array[String]) =

    val e1 = Employee("John", 30, 1000)
    val e2 = Employee("Peter", 20, 2000)
    val e3 = Employee("Mark", 50, 3000)
    val employees = List(e1, e2, e3)

    // print the list of employees
    println("List of employees:")
    employees.foreach(println)

    println("about to makedb")
    makedb(employees)
    println("done makedb")

    val employees2 = dbToEmployees(url, driver)
    println("List of employees from db:")
    employees2.foreach(println)
    
    val employees3 = dbToEmployees2(url)
    println("New funky List of employees from db:")
    employees3.foreach(println)
    