package kenbot.svopo

import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ListBuffer


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

