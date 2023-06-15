ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"


lazy val root = (project in file("."))
  .settings(
    name := "ScalaQuiz"
  )

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.9.0"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.32"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"