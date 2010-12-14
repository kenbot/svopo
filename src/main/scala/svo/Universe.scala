package svo
import DefaultWords._

class Universe {
  private var factList: List[SVO] = Nil
  private var wordList: Map[Symbol, Word] = Map.empty
  private var ruleList: List[Rule] = Nil
  private var impliesNouns: Map[Noun, Noun] = Map.empty
  private var impliesVerbs: Map[Verb, Verb] = Map.empty

  def facts = factList
  def words = wordList
  
  def select[R](f: PartialFunction[SVO, R]): List[R] = (List[R]() /: facts) {
    (list, svo) => if (f isDefinedAt svo) f(svo) :: list else list
  }
  
  def exists(w: Word) = words.keysIterator contains w
  def exists(svo: SVO) = facts contains svo

  def addImplies(n1: Noun, n2: Noun) {impliesNouns += n1 -> n2}
  def addImplies(v1: Verb, v2: Verb) {impliesVerbs += v1 -> v2}
  
  
  def addRule(pf: RuleFunction) {
    ruleList ::= Rule(pf)
    println("Rule added")
  }
  def add(svo: SVO) {
    
    factList = svo :: ruleList.flatMap(r => (r(svo)--because-svo).toList) ::: factList
    println("Added: " + svo)
    //factList :::= factList flatMap (_.apply(svo))
  }
}

