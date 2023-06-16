ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

val scalatestVersion = "3.2.15"


lazy val root = (project in file("."))
  .settings(
    name := "ScalaQuiz"
  )

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.9.0"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.5"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.5"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test")
libraryDependencies += "io.circe" %% "circe-core" % "0.14.1"
libraryDependencies += "io.circe" %% "circe-parser" % "0.14.1"
libraryDependencies += "org.mockito" % "mockito-core" % "3.12.4" % Test

