package com.quiz.scala

import org.mongodb.scala.{Document, MongoCollection, Observable, Observer}
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonDocument

import scala.io.StdIn
import scala.sys.exit
import scala.util.Random
//import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.bson.collection.mutable
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._


import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.concurrent.{Await, Future}
import scala.concurrent.Await.result
import scala.concurrent.duration.DurationInt

class Quiz (val Category: String, val Level: String, val QuestionsCount: Int){

  val ce = new ConnectionEst
  val collection: MongoCollection[BsonDocument] = ce.connect()


  def pickQuestions(): Seq[String] ={

    val findObservable: FindObservable[Document] = collection.find(and(equal("Category", Category), equal("Level", Level)))
    val futureResult: Future[Seq[Document]] = findObservable.toFuture()
    val result: Seq[Document] = Await.result(futureResult, 5.seconds)
    val QsCount = getQuestionsCount(result)
    if(QsCount<QuestionsCount){
      println("Too few questions in database")
      exit(0)
    }
    val randomNums = chooseRandomNums(QuestionsCount, QsCount)
    chooseRandomQuestions(result, randomNums)
  }

  def getQuestionsCount(Qs: Seq[Document]): Int ={
    var counter = 0
    Qs.foreach { _: Document =>
      counter = counter + 1
    }
    counter
  }

  // Using Set and switching to Seq because cannot randomize the Seq- it's already unordered
  def chooseRandomNums(required: Int, all: Int): Set[Int] ={
    val allQuestions: Seq[Int] = 1 to all
    val random: Random = new Random(System.currentTimeMillis())
    var shuffledSeq: Seq[Int] = random.shuffle(allQuestions)
    for (_ <- 1 to all-required) {
      shuffledSeq = shuffledSeq.tail
    }
    shuffledSeq.toSet
  }

  def chooseRandomQuestions(result : Seq[Document], randomNums: Set[Int]): Seq[String] ={
    var randomQs = Seq.empty[String]
    var counter = 1
    result.foreach { document: Document =>
      if (randomNums.contains(counter)) {
        val jsonDocument = document.toJson()
        val temp = Seq(jsonDocument)
        randomQs = randomQs ++ temp
      }
      counter = counter + 1
    }
    randomQs
  }

  def serveQuestion(parser: jsonParser): Unit={
    println(parser.parseQuestion())
    println()
  }

  def serveAnswers(parser: jsonParser): Unit={
    val answers = parser.parseAnswers()
    for (i <- 0 to 3) {
      val ans = answers(i)
      val letter = answerLetter(i)
      println(s"$letter) $ans")
    }
    println()
  }

  def serveQuestionsGetScore(questions: Seq[String]): Int = {
    var totalscore = 0
    for (q <- questions) {
      val parser = new jsonParser(q)
      serveQuestion(parser)
      serveAnswers(parser)
      totalscore = totalscore + validateAnswer(StdIn.readLine(), q)
      Thread.sleep(1500) // Oczekiwanie na 2 sekundy
    }
    totalscore
  }

  def validateAnswer(ans: String, question: String): Int={
    val parser = new jsonParser(question)
    val correct = parser.parseCorrect()
    val ansUpper = ans.toUpperCase()
    if(ansUpper.equals(correct)){
      println(s"Yes, $ansUpper is a correct answer!")
      println()
      1
    }else{
      val content = parser.getAnswerContent(correct)
      println(s"Wrong, correct answer is $correct: $content!")
      println()
      0
    }
  }

  def answerLetter(number: Int): String ={
    val result = number match {
      case 0 => "a"
      case 1 => "b"
      case 2 => "c"
      case _ => "d"
    }
    result
  }

  def printResults(score:Int, possible:Int): Unit={
    println()
    println("----------------Results----------------")
    println(s"Congratulations, you scored $score/$possible points!")
    println()
  }

  def startQuiz(): Unit ={
    val questions = pickQuestions()
    val score = serveQuestionsGetScore(questions)
    printResults(score, questions.length)
  }
}
