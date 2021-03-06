package kenbot.svopo

object Rule {
  implicit def apply(pf: RuleFunction): Rule = pf match {
    case r @ Rule(p, e) => r
    case f => Rule(f.isDefinedAt, f)
  }
}

case class Rule(predicate: SVO => Boolean, effect: SVO => SVOs) extends RuleFunction {
  def isDefinedAt(x: SVO) = predicate(x)
  final def apply(occurrence: SVO): SVOs = 
    if (predicate(occurrence)) effect(occurrence) 
    else SVOs.empty
  
  def !(implicit u: MutableUniverse) {u addRule this}
  def ![T](x: => T)(implicit u: MutableUniverse): T = {this ! u; x}
}

