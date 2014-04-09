package kenbot.svopo

import DefaultWords.because
  
object Examples extends App {
 
  
  implicit val u = MutableUniverse()
  
  // Save some nouns and verbs
  val killed: Verb = 'killed
  val kissed: Verb = 'kissed
  val tim: Noun = 'tim
  val friends: Verb = 'friends
  val loves: Verb = 'loves
  val angry: Verb = 'angry
  val jealous: Verb = 'jealous


  // If someone gets killed, then all their friends will be angry with the murderer.
  Rule {case perp-`killed`-victim => (victim-friends-*)-angry-perp}!
  Rule {
    case a-`kissed`-b => SVOs((*-(loves)-a)-jealous-b, 
                              (*-(loves)-b)-jealous-a)
  }!
  
  'jim-loves-'sarah!
  'jane-loves-'bill!
  'sarah-kissed-'bill!
  
  println("jim jealous of bill? " + ('jim-jealous-'bill?))
  println("jane jealous of sarah? " + ('jane-jealous-'sarah?))
  
  // Tracks causality!

  println("Why? " + (('jane-jealous-'sarah)--because-*))
  
  'bob-friends-'sally! 
  'bob-friends-'bill! 
  
  
  tim-killed-'bob--'with-'knife--'on-'Tuesday--'in-'library!
  
  
  
  tim-killed-'fred--'with-'hammer! 
  tim-killed-'gertrude! 
  'fred-killed-'bob! 
  'bob-friends-'sally! 
  'tim-friends-'roger! 
  'fred-friends-'nathan! 
  'joe-friends-'fred! 

  // Select all nouns that were killed by tim
  u select {case `tim`-`killed`-o => o} 
  
  // Shorthand for previous line
  tim-killed-* 
  
  // Select all 
  
  // Projection! Select all the friends of everyone who was killed by tim.
  (tim-killed-*)-friends-*

  
  println(u.facts)
  println("Roger angry at joe?" + ('roger-'angry-'joe?))
  u add 'joe-'killed-'tim
  println(u.facts)
  println("Roger angry at joe?" + ('roger-'angry-'joe?))
  //val xxx = 'tim-'take-(great-superDuperSword-#2) from 'templeOfDoom
  
  val John: Noun = 'John
  val Bob: Noun = 'Bob
  val Gave: Verb = 'gave
  val Book1: Noun = 'Book1

  val attack = 'tall+'soldier-'killed-'bob--'at-"5:00pm"
  println(attack)
  
  val giveBook = John-Gave-'book--'to-'Bob--'at-'shop

  giveBook match {
    case svo @ person1-Gave-obj--to-person2 => println("match 1: " + svo)
    //case s-v-o1--p-o2 => println("Success!")
    case x => println("Failed: " + x)
  }
}

