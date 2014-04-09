package kenbot.svopo


object SVOPs extends MultipleFactory[SVOP, SVOPs]  {
  protected def newMultiple(ss: Set[SVOP]) = new SVOPs(ss)
}

class SVOPs(protected val values: Set[SVOP] = Set.empty) extends Multiple[SVOP, SVOPs] /*with SVOWithoutObjects*/ {
  protected def factory = SVOPs

  def -(obj: Nouns): SVOs = for (svop <- this; o <- obj) yield svop-o
  def svo: SVOs = map(_.svo)
  def prep: Prepositions = map(_.prep)

  // Query for all known objects to this query 
  def -*(implicit u: Universe): Nouns = for (svop <- this; o <- svop-*) yield o
}


case class SVOP(override val svo: SVO, override val prep: Preposition) extends SVOPs with Single[SVOP, SVOPs] /*with SVOWithoutObject*/ {
  def -(obj: Noun) = SVOPO(svo, PO(prep,obj))

  // Query for all known objects to this query 
  override def -*(implicit u: Universe): Nouns = u select { 
    case svopo: SVOPO => svopo match {
      case `svo`--`prep`-o => o // Bug: match error
    }
  }
  override def toString() = svo + "--" + prep
  override def equalityValue = (svo, prep)
}


object -- {
  def unapply(svop: SVOP): Option[(SVO, Preposition)] = Some((svop.svo, svop.prep))
}

/*

trait SVOWithoutObjects {
  def -(obj: Nouns): SVOs 
  def subj: Nouns
  def verb: Verbs
}

trait SVOWithoutObject extends SVOWithoutObjects {
  def -(obj: Noun): SVO
  def subj: Noun
  def verb: Verb
}

*/