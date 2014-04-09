package kenbot.svopo



object Verbs extends MultipleFactory[Verb, Verbs] {
  protected def newMultiple(ss: Set[Verb]) = new Verbs(ss)
}

class Verbs(protected val values: Set[Verb] = Set.empty) extends Multiple[Verb, Verbs] {
  protected def factory = Verbs
}

case class Verb(name: String) extends Verbs with Single[Verb, Verbs] with Word {
  def v: this.type = this
  def ==> (v: Verb)(implicit u: MutableUniverse) {u.addImplies(this, v)}
  def <== (v: Verb)(implicit u: MutableUniverse) {u.addImplies(v, this)}
  def <==> (v: Verb)(implicit u: MutableUniverse) {
    this ==> v
    this <== v
  }
}