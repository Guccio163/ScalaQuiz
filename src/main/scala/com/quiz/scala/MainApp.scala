package com.quiz.scala
import scala.language.postfixOps

object MainApp extends App {


//   Category/Level/Question/Ans1/Ans2/Ans3/Ans4/Correct

//  val qa = new QuestionAdder("Maths", "Easy",
//    "What is the correct solution of 2+2*2?",
//    "2", "4","6","8", "C")
//  qa.addQuestion()
//  println(qa)

  val quiz = new Quiz("Biology", 1, 3)
  quiz.startQuiz()



}
