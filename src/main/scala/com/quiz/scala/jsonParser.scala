package com.quiz.scala
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import org.mongodb.scala.bson.BsonArray


class jsonParser (val jsonString: String){

  val objectMapper: ObjectMapper = new ObjectMapper()
  val jsonNode: JsonNode = objectMapper.readTree(jsonString)

  def parse(): Unit ={
    val category: String = jsonNode.get("Category").asText()
    val level: String = jsonNode.get("Level").asText()
    val question: String = jsonNode.get("Q").asText()
    val answerArrayNode: JsonNode = jsonNode.get("A")
    val answers: Array[String] = objectMapper.convertValue(answerArrayNode, classOf[Array[String]])
    val correct: String = jsonNode.get("Correct").asText()
    println(category)
    println(level)
    println(question)
    for (i <- 0 to 3) {
      val ans = answers(i)
      println(s" - $ans")
    }
    println(correct)
  }

  def parseCategory(): String ={
    jsonNode.get("Category").asText()
  }

  def parseLevel(): String = {
    jsonNode.get("Level").asText()
  }

  def parseQuestion(): String = {
    jsonNode.get("Q").asText()
  }

  def parseAnswers(): Array[String] = {
    val answerArrayNode: JsonNode = jsonNode.get("A")
    objectMapper.convertValue(answerArrayNode, classOf[Array[String]])
  }

  def parseCorrect(): String = {
    jsonNode.get("Correct").asText()
  }
}
