# My kewl wordy app

- app.sc is the intended future app in various states of disrepair
  - responses1.json
  - responses2.json
  - it reads in json files and does a "run" against each of them.
  - To run it:
`scala-cli app.sc`

- dbplayground
  - dbplay3 explores how to write case class to a db and get them back to a case class. `scala-cli dbplayground.scala simplesql.scala`
  - simplesql.scala (a github [not mine] library)
  - employees.db : gets created

- makedb
  - makedb.scala makes a words database with table words with one filed: words:
  - words.txt : file containg words to populate the table
  - sqlite.db : gets created
```bash
alias doit='rm -f sqlite.db; scala-cli makedb.scala && echo "select * from words" | sqlite3 sqlite.db'
doit
```
