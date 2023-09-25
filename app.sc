//> using lib org.xerial:sqlite-jdbc:3.43.0.0
//> using dep com.lihaoyi::upickle::3.1.3
//> using dep com.lihaoyi::pprint::0.8.1

import scala.io.Source

import upickle.default.*

val wordsFile = "words.txt"

val words = Array (
    "aa",
    "ala",
    "able",
    "bale",
    "cage",
    "zeta",
    "zygote"
)

// print("Enter something:")
// val input = scala.io.StdIn.readLine()
// println(s"You entered: $input")

def fileAsString(filename: String) = 
    Source.fromFile(filename).mkString


case class UserResponse(response: String, seconds_to_respond: Int) derives ReadWriter
case class UserResponses(user_responses: Seq[UserResponse]) derives ReadWriter

// compare word from words with userResponse.response from userResponses
// return the number of correct responses
def scoreWordsWithResponses(words: Array[String], userResponses:Seq[UserResponse]): Int = {
    var nCorrect = 0
    for ((word, userResponse) <- words zip userResponses) {
        if (userResponse.response == word) then nCorrect += 1
    }
    nCorrect
}

// a run is a sequence of words presented to a user
// each word in that sequence is compared to each response in the json file
// return the numer of correct responses
def runUsingJsonFile(words: Array[String], responsesJsonFilename: String): Int =
    val myUserResponses = read[UserResponses](fileAsString(responsesJsonFilename))
    scoreWordsWithResponses(words, myUserResponses.user_responses)


// test several runs of the same sequence of words
// the sequence of words is tested against different json files (containing responses)
for filename <- Seq("responses01.json", "responses02.json") do
    println(s"nCorrect: ${runUsingJsonFile(words, filename)}")

