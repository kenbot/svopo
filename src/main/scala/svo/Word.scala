package svo



trait Word extends Equals {
  this: Single[_, _] =>
  
  def name: String

  override def toString() = name
  protected def equalityValue(): Any = name
  def canEqual(other: Any) = other match {
    case w: Word => w.getClass == getClass
    case _ => false
  }
}
