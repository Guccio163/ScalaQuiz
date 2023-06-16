package com.quiz.scala
import scala.io.StdIn
import scala.language.postfixOps

object MainApp extends App {

  def getSpecs: Seq[String] ={
    println("Hi, would you like to solve a custom or premade quiz?")
    println("Choose 1 for custom or 2 for premade")
    val choice = StdIn.readLine().toInt
    if(choice == 1){
      println("What quiz would you like to solve today?")
      println("Category (Biology/Maths/ more available soon):")
      val category = StdIn.readLine()
      println("Difficulty (Easy/Medium/Hard(soon):")
      val level = StdIn.readLine()
      println("Questions count (1-10):")
      val count = StdIn.readLine()
      Seq(category, level, count)
    }
    else {
      Seq("Biology", "Easy", "5")
    }
  }

//   Category/Level/Question/Ans1/Ans2/Ans3/Ans4/Correct

//  val qa = new QuestionAdder("Maths", "Easy", "What is the next number in the sequence: 1, 3, 5, 7, ___ ?",
//    "8", "9","10","11", "B")
//  qa.addQuestion()



  val specs = getSpecs
  val quiz = new Quiz(specs.head, specs(1), specs(2).toInt)
  quiz.startQuiz()


}
