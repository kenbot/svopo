package svo

import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ListBuffer


trait LowPriorityCanBuildFroms[S <: Single[S,M] with M, M <: Multiple[S, M]]  {
  this: MultipleFactory[S,M] =>

  implicit def canBuildSetFromMultiple[B] = new CanBuildFrom[M, B, Set[B]] {
    def apply() = Set.newBuilder
    def apply(from: M) = Set.newBuilder[B]
  }
}

trait MultipleFactory[S <: Single[S,M] with M, M <: Multiple[S, M]] extends LowPriorityCanBuildFroms[S,M] {factory =>  
  def apply(seq: S*): M = if (seq.size == 1) seq(0) else newMultiple(seq.toSet)
  val empty = apply()

  protected def newMultiple(ss: Set[S]): M

  def newBuilder: Builder[S, M] = new ListBuffer[S] mapResult {b => apply(b: _*)}

  implicit def iterableToMultiple(coll: Iterable[S]): M = apply(coll.toSeq: _*)

  class MultipleCanBuildFrom[From] extends CanBuildFrom[From, S, M] {
    def apply() = newBuilder
    def apply(from: From) = newBuilder
  }

  implicit def canBuildFromMultiple[From <: Multiple[_,From]] = new MultipleCanBuildFrom[From]


}

trait Multiple[S <: Single[S,M] with M, M <: Multiple[S, M]] /* extends Iterable[S] with IterableLike[S, M] */ {
  this: M =>
  
  protected[this] def factory: MultipleFactory[S, M]
  protected val values: Set[S]
  
  def iterator = values.iterator

  def size = values.size
  def toSeq: Seq[S] = values.toSeq
  def toList: List[S] = values.toList
  def toSet: Set[S] = values.toSet

  def exists(p: S => Boolean) = values exists p
  def foreach[U](f: S => U) = values foreach f
  def isEmpty = size == 0
  def nonEmpty = !isEmpty

  def map[B, That](f: S => B)(implicit bf: CanBuildFrom[M, B, That]): That = {
    val b = bf(this)
    b.sizeHint(size) 
    b ++= (values map f)
    b.result
  } 
  
  def flatMap[B <: Single[B, _], That](f: S => Multiple[B, _])(implicit bf: CanBuildFrom[M, B, That]): That = {
    val b = bf(this)
    b ++= (values flatMap (f(_).toSeq))
    b.result
  }
  def filter(p: S => Boolean) = factory(values.filter(p).toSeq: _*)

  override def toString() = getClass.getSimpleName + values.mkString("(", ",", ")")
  override def hashCode() = values.hashCode
  override def equals(o: Any): Boolean = o match {
    case null => false
    case self if self.asInstanceOf[AnyRef] eq this => true
    case other: Multiple[_,_] => other.values == values
    case _ => false
  }
}

trait Single[S <: Single[S,M] with M, M <: Multiple[S,M]] extends Multiple[S, M] with Equals {
  this: S =>
  
  override protected val values: Set[S] = Set(this)
  
  override final def equals(o: Any): Boolean = o match {
    case null => false
    case self if self.asInstanceOf[AnyRef] eq this => true
    case s: Single[_,_] if this canEqual s => this.equalityValue == s.equalityValue
    case _ => false
  }
  
  override final def hashCode() = equalityValue.hashCode()
  protected def equalityValue(): Any
}

