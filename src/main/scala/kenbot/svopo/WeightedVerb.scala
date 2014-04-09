package kenbot.svopo
import collection.{SeqProxy, SeqProxyLike}


/*
object WeightedVerbs {
  def apply(seq: Seq[WeightedVerb]) = if (seq.size == 1) seq(0) else WeightedVerbSeq(seq)
}

sealed trait WeightedVerbs extends Seq[WeightedVerb] {
  def verb: Verbs
}

case class WeightedVerb(verb: Verb, weight: Double = 1.0) extends WeightedVerbs with UnitSeq[WeightedVerb] {
  require(weight >= 0.0 && weight <= 1.0)
  override def toString() = verb + "%" + weight
}

case class WeightedVerbSeq(self: Seq[WeightedVerb]) extends WeightedVerbs with SeqProxy[WeightedVerb] {
  def verb: Verbs = Verbs(for (wv <- self) yield wv.verb)
  override def toString() = mkString("WeightedVerbs(", ",", ")")
}
*/


/*
object % {
  def unapply(f: WeightedVerb): Option[(Verb, Double)] = Some(f.verb, f.weight)
}
*/