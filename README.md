# My kewl wordy app

- app.sc is the intended future app in various states of disrepair, `scala-cli app.sc`
- dbplayground explores how to write case class to a db and get them back to a case class. `scala-cli dbplayground.scala simplesql.scala`
- simplesql.scala taken from github, is the source version of a library (and is used and compiled together with your source file. dbplayground uses it.
- makedb.scala makes a words database with table(s?):

```bash
alias doit='rm -f sqlite.db; scala-cli makedb.scala && echo "select * from words" | sqlite3 sqlite.db'
doit
```

## Other files:

- words.txt : text file which in future will contail all valid word game words
- sqlite.db created with makedb
- employees.db created by dbplayground
