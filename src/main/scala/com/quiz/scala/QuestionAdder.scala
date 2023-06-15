package com.quiz.scala

import org.mongodb.scala.bson.collection.Document
import org.mongodb.scala.bson.{BsonArray, BsonDocument, BsonString, Document}
//import org.mongodb.scala.bson.collection.mutable.Document

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class QuestionAdder(val Cat: String, val Lev: String, val Q: String, val ans1: String, val ans2: String, val ans3: String, val ans4: String, val Cor: String){

  def addQuestion(): Unit ={
    val ce = new ConnectionEst
    val collection = ce.connect()
    val insertDoc = new BsonDocument()
    insertDoc.append("Category", BsonString(Cat))
      .append("Level", BsonString(Lev))
      .append("Q", BsonString(Q))
      .append("A", BsonArray(BsonString(ans1), BsonString(ans2), BsonString(ans3), BsonString(ans4)))
      .append("Correct", BsonString(Cor))


    Await.result(collection.insertOne(insertDoc).toFuture(), 100 seconds)

  }
}











