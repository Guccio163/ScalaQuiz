package com.quiz.scala
import com.fasterxml.jackson.databind.ObjectMapper


class jsonParser (val jsonString: String){

  def parse(): Unit ={
    println(jsonString)
    val objectMapper: ObjectMapper = new ObjectMapper()
    val jsonNode = objectMapper.readTree(jsonString)

    val cor: String = jsonNode.get("Correct").asText()
    println(cor)

  }
}
