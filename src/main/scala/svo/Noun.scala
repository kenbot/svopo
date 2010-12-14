package svo


object Nouns extends MultipleFactory[Noun, Nouns] {
  protected def newMultiple(ss: Set[Noun]) = new ManyNouns(ss)
}

trait Nouns extends Multiple[Noun, Nouns] {
  def -(vs: Verbs): SVs = for (n <- this; v <- vs) yield n-v
 
  def -*-(os: Nouns)(implicit u: Universe): Verbs = for {
    s <- this
    o <- os
    v <- u select {case `s`-v-`o` => v}
  } yield v
}

class ManyNouns(protected val values: Set[Noun] = Set.empty) extends Nouns {
  protected def factory = Nouns
}


object Noun {
  def apply(name: String) = SimpleNoun(name)
  def apply(svo: SVO) = SVONoun(svo)
  def unapply(noun: Noun): Option[String] = Some(noun.name)
}

abstract class Noun extends ManyNouns with Single[Noun, Nouns] with Word {
  def -(v: Verb) = new SV(this, v)
  def n: this.type = this
  def ==> (n: Noun)(implicit u: Universe) {u.addImplies(this, n)}
  def <== (n: Noun)(implicit u: Universe) {u.addImplies(n, this)}
  def <==> (n: Noun)(implicit u: Universe) {
    this ==> n
    this <== n
  }
}

case class SimpleNoun(name: String) extends Noun 

case class SVONoun(svo: SVO) extends Noun{
  override def name = "(" + svo + ")"
  override def equalityValue = svo
}


/*
sealed trait Variable

case object * extends Variable

object Variable {
  implicit def var2Noun(v: Variable) = new Noun(v.toString) with Variable
  implicit def var2Verb(v: Variable) = new Verb(v.toString) with Variable
  implicit def var2Preposition(v: Variable) = new Preposition(v.toString) with Variable
}
*/
