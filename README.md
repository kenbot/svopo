
This is an experimental Scala DSL to represent knowledge using a subject-verb-object--preposition-object syntax.  

The aim is to 
* Store knowledge about some domain
* have an natural syntax to declare each statement
* pattern-matching with a similar syntax
* manipulate a projection of many elements in the same way as one, like scala.xml.NodeSeq does.

It takes some ideas from the "Scala Prolog DSL": http://lampsvn.epfl.ch/trac/scala/browser/scala-experimental/trunk/src/prolog/Prolog.scala

Example usages:

# Declare SVO facts
```scala
  implicit val universe = MutableUniverse()
  
  'bob-'friends-'sally!
  // Added: bob-friends-sally
```

# Preposition - Indirect Objects
Add any number of Preposition-Object clauses to an SVO construction, using a double hyphen. Facts can be treated as nouns themselves, and composed.
```scala
  'jim-'attended-'geography_class--'on-'tuesday--'after-'maths_class--'because-('jim-'enrolled-'geography_class)!
  'jane-'attended-'geography_class--'on-'wednesday--'because-('jane-'enjoys-'geography_class)!
  'jim-'attended-'maths_class--'on-'tuesday--'before-'geography_class!
  // Added: jim-attended-geography_class--on-tuesday--after-maths_class--because-(jim-enrolled-geography_class)
  // Added: jane-attended-geography_class--on-wednesday--because-(jane-enjoys-geography_class)
  // Added: jim-attended-maths_class--on-tuesday--before-geography_class
```

# Pattern match!
```scala
  universe select {
    case student-`attended`-`geography_class` => student
  }
  // geographyStudents: svo.Nouns = ManyNouns(jim,jane)
```

# Use wildcards as syntax sugar for universe.select() queries.
```scala
  *-('attended)-'geography_class
  // res9: svopo.Nouns = ManyNouns(jane,jim)
  
  'jim-*-'geography_class
  // res10: svopo.Verbs = attended
  

```
