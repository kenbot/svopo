package kenbot.svopo

import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ListBuffer


trait Multiple[S <: Single[S,M] with M, M <: Multiple[S, M]] {
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
