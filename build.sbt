name := "svopo"

version := "0.1"

organization := "kenbot"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.9.1" % "test", 
  "junit" % "junit" % "4.8.1" % "test")

initialCommands := "import svopo._;"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:reflectiveCalls")

