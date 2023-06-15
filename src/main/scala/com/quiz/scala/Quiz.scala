package com.quiz.scala

import org.mongodb.scala.{Document, MongoCollection, Observable, Observer}
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.collection.immutable.Document
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


  def pickQuestions(): Unit ={

    val findObservable: FindObservable[Document] = collection.find(equal("Category", "Maths"))
    val futureResult: Future[Seq[Document]] = findObservable.toFuture()
    val result: Seq[Document] = Await.result(futureResult, 5.seconds)
    var counter = 0
    result.foreach { document: Document =>
      println(document.toJson)
      counter = counter + 1
    }
    println(counter)
  }

  def startQuiz(): Unit ={

  }
}
