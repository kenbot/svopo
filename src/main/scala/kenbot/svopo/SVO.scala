package kenbot.svopo


object SVOs extends MultipleFactory[SVO, SVOs] {
  protected def newMultiple(ss: Set[SVO]): SVOs = new ManySVOs(ss)
}

sealed trait SVOs extends Multiple[SVO, SVOs]  {
  def --(preps: Prepositions): SVOPs = for {
    svo <- this
    p <- preps
  } yield svo--p

  protected def factory = SVOs
  
  def subj: Nouns
  def verb: Verbs
  def obj: Nouns
  
  def ![T](x: => T)(implicit u: MutableUniverse): T = {this ! u; x}
  def !(implicit u: MutableUniverse) {this foreach u.add}
  def ?[T](x: => T)(implicit u: Universe): T = {this ? u; x}
  def ?(implicit u: Universe) = this exists u.exists
}

class ManySVOs(val values: Set[SVO] = Set.empty) extends SVOs {
  def subj: Nouns = map(_.subj)
  def verb: Verbs = map(_.verb)
  def obj: Nouns = map(_.obj)
}

sealed abstract class SVO extends SVOs with Single[SVO, SVOs] {
  def --(prep: Preposition): SVOP = SVOP(this, prep)
  val subj: Noun
  val verb: Verb
  val obj: Noun
  def sv: SV
  val weight: Double
  def pos: List[PO]
}

case class SimpleSVO(sv: SV, obj: Noun) extends SVO {
  val subj: Noun = sv.subj
  val verb: Verb = sv.verb
  val weight = 1.0
  def pos = Nil
  override def toString() = sv + "-" + obj
  override def equalityValue = (sv, obj)
}

case class SVOPO(svo: SVO, po: PO) extends SVO {
  val subj = svo.subj
  val verb = svo.verb
  val obj = svo.obj
  def sv = svo.sv
  def svop = svo--po.prep
  val weight = svo.weight
  def pos = po :: svo.pos
  override def equalityValue = (svo, po)
  override def toString() = svo + "--" + po
}

object - {
  def unapply(sv: SV): Option[(Noun, Verb)] = Some(sv.subj, sv.verb)
  def unapply(svo: SVO): Option[(SV, Noun)] = Some( (svo.sv, svo.obj) )
  def unapply(svo: SVOPO): Option[(SVOP, Noun)] = Some( (svo.svop, svo.po.obj) )
}


