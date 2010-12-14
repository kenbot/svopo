
package svo {
  trait LowPriority {
    implicit def sym2Prep(sym: Symbol) = Preposition(sym.name)
    implicit def str2Prep(str: String) = Preposition(str)
  }
}

package object svo extends LowPriority {


  type RuleFunction = PartialFunction[SVO, SVOs]

  //implicit def seqN2NounSeq(seq: Seq[Noun]) = NounSeq(seq: _*)
  implicit def sym2Verb(sym: Symbol) = Verb(sym.name)
  implicit def sym2Noun(sym: Symbol) = SimpleNoun(sym.name)
  implicit def word2Sym(word: Word) = word.name
  implicit def str2Verb(str: String) = Verb(str)
  implicit def str2Noun(str: String) = SimpleNoun(str)
  implicit def svo2Noun(svo: SVO) = SVONoun(svo)

  val totally = 1.0
  val mostly = 0.9
  val very = 0.7
  val somewhat = 0.5
  val slightly = 0.3
  val barely = 0.1
  val not = 0.0
  
  //val by, `for`, from, `with`, on, at, in, because, to = ~~
}
