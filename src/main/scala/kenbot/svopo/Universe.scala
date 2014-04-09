package kenbot.svopo

import DefaultWords.because

object Universe {
  implicit val global: MutableUniverse = new MutableUniverseImpl
}

trait Universe {
  def facts: Seq[SVO]
  def words: Map[Symbol, Word]
  def select[R](f: PartialFunction[SVO, R]): List[R]
  def exists(w: Word): Boolean
  def exists(svo: SVO): Boolean
  
  def addImplies(n1: Noun, n2: Noun): Universe
  def addImplies(v1: Verb, v2: Verb): Universe
  def addRule(pf: RuleFunction): Universe
  def add(svo: SVO): Universe
}

trait ImmutableUniverse extends Universe

trait MutableUniverse extends Universe {
  def snapshot: Universe
}
 
object ImmutableUniverse {
  val empty: ImmutableUniverse = new ImmutableUniverseImpl 
}

class ImmutableUniverseImpl private (
    private val factList: Seq[SVO],
    private val wordList: Map[Symbol, Word],
    private val ruleList: Seq[Rule],
    private val impliesNouns: Map[Noun, Noun],
    private val impliesVerbs: Map[Verb, Verb]) extends ImmutableUniverse {

  def this() = this(Vector(), Map(), Vector(), Map(), Map())
  
  def facts = factList
  def words = wordList
  
  def select[R](f: PartialFunction[SVO, R]): List[R] = (List[R]() /: facts) {
    (list, svo) => if (f isDefinedAt svo) f(svo) :: list else list
  }
  
  def exists(w: Word) = words.keysIterator contains w
  def exists(svo: SVO) = facts contains svo

  private def copy(
      factList: Seq[SVO] = this.factList, 
      wordList: Map[Symbol, Word] = this.wordList, 
      ruleList: Seq[Rule] = this.ruleList, 
      impliesNouns: Map[Noun, Noun] = this.impliesNouns, 
      impliesVerbs: Map[Verb, Verb] = this.impliesVerbs) = {
    
    new ImmutableUniverseImpl(factList, wordList, ruleList, impliesNouns, impliesVerbs)
  }
  
  def addImplies(n1: Noun, n2: Noun): Universe = copy(impliesNouns = impliesNouns + (n1 -> n2))
  def addImplies(v1: Verb, v2: Verb): Universe = copy(impliesVerbs = impliesVerbs + (v1 -> v2))
  def addRule(pf: RuleFunction): Universe = copy(ruleList = ruleList :+ Rule(pf))
  
  def add(svo: SVO): Universe = {
    val newFactsImplied = ruleList.flatMap(r => (r(svo)--because-svo).toSeq)
    copy(factList = factList ++ newFactsImplied :+ svo)
  }
}

object MutableUniverse {
  def apply(): MutableUniverse = new MutableUniverseImpl 
}

class MutableUniverseImpl extends MutableUniverse {
  private var currentUniverse: Universe = ImmutableUniverse.empty
  
  def snapshot: Universe = currentUniverse
  
  def facts: Seq[SVO] = currentUniverse.facts
  def words: Map[Symbol, Word] = currentUniverse.words
  def select[R](f: PartialFunction[SVO, R]): List[R] = currentUniverse select f
  def exists(w: Word): Boolean = currentUniverse exists w
  def exists(svo: SVO): Boolean = currentUniverse exists svo
  
  def addImplies(n1: Noun, n2: Noun): this.type = {
    currentUniverse = currentUniverse.addImplies(n1, n2)
    this
  }
  
  def addImplies(v1: Verb, v2: Verb): this.type = {
    currentUniverse = currentUniverse.addImplies(v1, v2)
    this
  }
  def addRule(pf: RuleFunction): this.type = {
    currentUniverse = currentUniverse addRule pf
    this
  }
  def add(svo: SVO): this.type = {
    currentUniverse = currentUniverse add svo
    this
  }
}