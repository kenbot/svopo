package svo


object Main {
  def main(args: Array[String]) {

    implicit val u = new Universe
    'tim-'killed-'bob--'with-'knife!
    'tim-'killed-'fred--'with-'hammer! 
    'tim-'killed-'gertrude! 
    'tim-'ate-'potato! 
    'fred-'killed-'bob! 
    'bob-'friends-'sally! 
    'tim-'friends-'roger! 
    'fred-'friends-'nathan! 
    'joe-'friends-'fred! 
    
    val killed = 'killed.v
    val tim = 'tim.n
    val friends = 'friends.v
    val angry = 'angry.v
    
    println( u select {case `tim`-`killed`-o => o} )
    println(tim-killed-*)
    println((tim-killed-*)-friends-*)

    Rule {case perp-`killed`-victim => (victim-friends-*)-angry-perp}!
    
    println(u.facts)
    println("Roger angry at joe?" + ('roger-'angry-'joe?))
    u add 'joe-'killed-'tim
    println(u.facts)
    println("Roger angry at joe?" + ('roger-'angry-'joe?))
    //val xxx = 'tim-'take-(superDuperSword-#2) from 'templeOfDoom
    
    val John: Noun = 'John
    val Bob: Noun = 'Bob
    val Gave: Verb = 'gave
    val Book1: Noun = 'Book1

    val attack: SVO = 'soldier-'killed-'bob
    val giveBook = John-Gave-'book--'to-'Bob--'at-'shop

    giveBook match {
      case svo @ person1-Gave-obj--to-person2 => println("match 1: " + svo)
      //case s-v-o1--p-o2 => println("Success!")
      case x => println("Failed: " + x)
    }
  }
}

