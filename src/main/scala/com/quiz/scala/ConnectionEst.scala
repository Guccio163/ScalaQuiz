package com.quiz.scala

import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

class ConnectionEst {
  def connect(): MongoCollection[BsonDocument] ={
    val mongoUrl = "mongodb://localhost:27017/?minPool=2&maxPool=10"
    val mongoClient: MongoClient = MongoClient(mongoUrl)
    val dataBase: MongoDatabase = mongoClient.getDatabase("ScalaQuiz")
    val collection: MongoCollection[BsonDocument] = dataBase.getCollection("Questions")
    collection
  }

//  def connectDoc(): MongoCollection[Document] = {
//    val mongoUrl = "mongodb://localhost:27017/?minPool=2&maxPool=10"
//    val mongoClient: MongoClient = MongoClient(mongoUrl)
//    val dataBase: MongoDatabase = mongoClient.getDatabase("WG")
//    val collection: MongoCollection[Document] = dataBase.getCollection("student")
//    collection
//  }
}
