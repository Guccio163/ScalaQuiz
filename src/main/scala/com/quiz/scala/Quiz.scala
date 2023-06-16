package com.quiz.scala

import org.mongodb.scala.{Document, MongoCollection, Observable, Observer}
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonDocument

import scala.io.StdIn
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

class Quiz (val Category: String, val Level: Int, val QuestionsCount: Int){

  val ce = new ConnectionEst
  val collection: MongoCollection[BsonDocument] = ce.connect()


  def pickQuestions(): Seq[String] ={

    val findObservable: FindObservable[Document] = collection.find(equal("Category", "Biology"))
    val futureResult: Future[Seq[Document]] = findObservable.toFuture()
    val result: Seq[Document] = Await.result(futureResult, 5.seconds)
    val QsCount = getQuestionsCount(result)
    val randomNums = chooseRandom(QuestionsCount, QsCount)
    var randomQs = Seq.empty[String]
    var counter = 1

    result.foreach { document: Document =>
      if(randomNums.contains(counter)) {
        val jsonDocument = document.toJson()
        val temp = Seq(jsonDocument)
        randomQs = randomQs ++ temp
      }
      counter = counter + 1
    }
    randomQs
  }

  def getQuestionsCount(Qs: Seq[Document]): Int ={
    var counter = 0
    Qs.foreach { _: Document =>
      counter = counter + 1
    }
    counter
  }

  def chooseRandom(required: Int, all: Int): Set[Int] ={
    val allQ: Seq[Int] = 1 to all
    val random: Random = new Random(System.currentTimeMillis())
    var shuffledSet: Seq[Int] = random.shuffle(allQ)
    for (_ <- 1 to all-required) {
      shuffledSet = shuffledSet.tail
    }
    shuffledSet.toSet
  }

  def serveQuestion(jsonString: String): Unit={
    val parser = new jsonParser(jsonString)
    println(parser.parseQuestion())
    println()
    val answers = parser.parseAnswers()
    for (i <- 0 to 3) {
      val ans = answers(i)
      val letter = answerLetter(i)
      println(s"$letter) $ans")
    }
    println()
  }

  def serveQuestions(questions: Seq[String]): Int = {
    var totalscore = 0
    for (q <- questions) {
      serveQuestion(q)
      val ans = StdIn.readLine()
      totalscore = totalscore + validateAnswer(ans, q)
    }
    totalscore
  }

  def validateAnswer(ans: String, question: String): Int={
    val parser = new jsonParser(question)
    val correct = parser.parseCorrect()
    val Ans = ans.toUpperCase()
    if(Ans.equals(correct)){
      1
    }else{
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

  def startQuiz(): Unit ={
    val questions = pickQuestions()
    val score = serveQuestions(questions)
    println(score)
  }
}
