import simplesql as sq
import org.sqlite.SQLiteDataSource // for new way of doing things

val url = "jdbc:sqlite:employees.db"

val ds: javax.sql.DataSource = {
  val ds = SQLiteDataSource()
  ds.setUrl(url)
  ds
}

// use either `sq.run` or `sq.transaction` blocks
sq.transaction(ds){

  sq.write(
    sql"""
      create table user (
        id integer primary key,
        name string not null,
        email string not null
      )
    """
  )

  sq.read[(Int, String, String)](sql"select * from user")
  sq.write(sql"""insert into user values (1, "admin", "admin@example.org")""")

  case class User(id: Int, name: String, email: String) derives sq.Reader
  sq.read[User](sql"select * from user")
}
