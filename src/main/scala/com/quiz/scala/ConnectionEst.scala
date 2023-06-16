package com.quiz.scala

import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

class ConnectionEst {

  val mongoUrl = "mongodb://localhost:27017/?minPool=2&maxPool=10"
  val dbName = "ScalaQuiz"
  val collectionName = "Questions"

  def connect(): MongoCollection[BsonDocument] ={
    getCollection(getDB(getClient(mongoUrl), dbName), collectionName)
  }
  def getClient(url: String): MongoClient = {
    MongoClient(url)
  }
  def getDB(client: MongoClient, name: String): MongoDatabase = {
    client.getDatabase(name)
  }
  def getCollection(db:MongoDatabase, name:String): MongoCollection[BsonDocument] ={
    db.getCollection(name)
  }
}
