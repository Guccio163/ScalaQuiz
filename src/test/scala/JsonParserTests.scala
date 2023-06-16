import com.quiz.scala.jsonParser
import org.scalatest.funspec.AnyFunSpec
import io.circe._
import io.circe.syntax._
import io.circe.Json
import scala.io.Source
import io.circe.parser._

import scala.util.Using



class JsonParserTests extends AnyFunSpec {


  val strictValue : String = "{\n  \"_id\": {\n    \"$oid\": \"648c5e189ba1d6138bda2a57\"\n  },\n  \"Category\": \"Movies\",\n  \"Level\": \"Easy\",\n  \"Q\": \"What is a name of one of two main characters in Fast and Furious franchise?\",\n  \"A\": [\n    \"Rom Dometto\",\n    \"Dom Toretto\",\n    \"Tom Doretto\",\n    \"Mom Rodetto\"\n  ],\n  \"Correct\": \"B\"\n}"
  val parser = new jsonParser(strictValue)

  // Smth didn't work properly, up to eventual solve

//  val filePath = "resources/mock.json"
//  val mockJson: String = getJsonString(filePath)
//
//  def getJsonString(filePath: String): String = {
//    val source: Source = Source.fromFile(filePath)
//    val fileContent: String = source.mkString
//    val json: Either[io.circe.Error, Json] = parse(fileContent)
//    val jsonContent :String = json.toString
//    source.close()
//    jsonContent
//  }

  describe("reading Category"){
    it("should give back right category"){
      assert(parser.parseCategory().equals("Movies"))
    }
  }

  describe("reading Level") {
    it("should give back right difficulty level") {
      assert(parser.parseLevel().equals("Easy"))
    }
  }

  describe("reading Question") {
    it("should give back right question") {
      assert(parser.parseQuestion().equals("What is a name of one of two main characters in Fast and Furious franchise?"))
    }
  }

  describe("reading answers") {
    it("should give back right answers set") {
      assert(parser.parseAnswers().sameElements(Array("Rom Dometto",
        "Dom Toretto",
        "Tom Doretto",
        "Mom Rodetto")))
    }
  }

  describe("reading right answers as to order numbers") {
    it("should give back right answer for the order") {
      assert(parser.getAnswerContent("A").equals("Rom Dometto"))
      assert(parser.getAnswerContent("B").equals("Dom Toretto"))
      assert(parser.getAnswerContent("C").equals("Tom Doretto"))
      assert(parser.getAnswerContent("D").equals("Mom Rodetto"))
    }
  }

  describe("reading Correct") {
    it("should give back right correct answer") {
      assert(parser.parseCorrect().equals("B"))
    }
  }
}

