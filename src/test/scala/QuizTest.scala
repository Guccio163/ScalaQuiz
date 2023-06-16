import com.quiz.scala.Quiz
import org.scalatest.freespec.AnyFreeSpec

class QuizTest extends AnyFreeSpec{

  val quiz = new Quiz("Movies", "Easy", 1)
  val strictValue : String = "{\n  \"_id\": {\n    \"$oid\": \"648c5e189ba1d6138bda2a57\"\n  },\n  \"Category\": \"Movies\",\n  \"Level\": \"Easy\",\n  \"Q\": \"What is a name of one of two main characters in Fast and Furious franchise?\",\n  \"A\": [\n    \"Rom Dometto\",\n    \"Dom Toretto\",\n    \"Tom Doretto\",\n    \"Mom Rodetto\"\n  ],\n  \"Correct\": \"B\"\n}"


  "answerLetter" - {
    "should return right letter" in {
      assert(quiz.answerLetter(0).equals("a"))
      assert(quiz.answerLetter(1).equals("b"))
      assert(quiz.answerLetter(2).equals("c"))
      assert(quiz.answerLetter(3).equals("d"))
    }
  }

  "validateAnswer" - {
    "should return 1 for right and 0 for wrong answers" in {
      assert(quiz.validateAnswer("A", strictValue).equals(0))
      assert(quiz.validateAnswer("B", strictValue).equals(1))
      assert(quiz.validateAnswer("C", strictValue).equals(0))
      assert(quiz.validateAnswer("D", strictValue).equals(0))
    }
  }

  "chooseRandomNums" - {
    "should return other set everytime" in {
      assert(!quiz.chooseRandomNums(3, 10).equals(quiz.chooseRandomNums(3, 10)))
    }
  }

}
