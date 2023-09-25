//> using lib org.xerial:sqlite-jdbc:3.43.0.0

import simplesql as sq // for new way of doing things
import org.sqlite.SQLiteDataSource // for new way of doing things


object Main:
    
  case class Employee(name: String, age: Int, salary: Int) derives sq.Reader

  val url = "jdbc:sqlite:employees.db"

  def makedb(employees: Seq[Employee]) =
    val ds = SQLiteDataSource()
    ds.setUrl(url)
        
    sq.transaction(ds) {
        sq.write(sql"drop table if exists employees")
        sq.write(sql"create table employees (name string, age int, salary int)")
        
        for employee <- employees do
            sq.write(sql"insert into employees (name, age, salary) values (${employee.name}, ${employee.age}, ${employee.salary})")
    }
      

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
    println()

    println("about to makedb")
    makedb(employees)
    println("done makedb")
    println()

    val employees3 = dbToEmployees2(url)
    println("New funky List of employees from db:")
    employees3.foreach(println)
    