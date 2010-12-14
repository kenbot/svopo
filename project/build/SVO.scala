import sbt._

class SVOProject(info: ProjectInfo) extends DefaultProject(info) {
  val scalaTools = "scala-tools.org" at "http://www.scala-tools.org/repo-snapshots"
  //lazy val scalaSwing = "org.scala-lang" % "scala-swing" % "2.8.1"
  lazy val scalaTest = "org.scalatest" % "scalatest" % "1.2"
  override def compileOptions = super.compileOptions ++ Seq(Unchecked)
  override def consoleInit = """
    import svo._;
    implicit val u = new Universe;
  """
}