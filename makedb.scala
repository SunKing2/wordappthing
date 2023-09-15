//> using lib org.xerial:sqlite-jdbc:3.43.0.0

import java.sql.{Connection, DriverManager, ResultSet};

val wordsFile = "words.txt"

object Main:
  val url = "jdbc:sqlite:sqlite.db"
  val driver = "org.sqlite.JDBC"

  def makedb() =
    Class.forName(driver)
    var connection:Connection = DriverManager.getConnection(url)
    try
      val statement = connection.createStatement

      statement.executeUpdate("drop table if exists words")
      statement.executeUpdate("create table words (word string)")

      scala.io.Source.fromFile(wordsFile).getLines().foreach { line =>
        statement.executeUpdate(s"insert into words values('$line')")
      }

    catch
      case e: Exception => e.printStackTrace
    finally
      connection.close
    
  def main(args:Array[String]) =
    makedb()
