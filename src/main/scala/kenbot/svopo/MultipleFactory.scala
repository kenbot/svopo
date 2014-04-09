package kenbot.svopo

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

trait MultipleFactory[S <: Single[S,M] with M, M <: Multiple[S, M]] extends LowPriorityCanBuildFroms[S,M] {
  factory =>  
    
  def apply(seq: M*): M = if (seq.size == 1) seq(0) 
                          else newMultiple( (Set[S]() /: seq)(_ ++ _.toSet) )
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
