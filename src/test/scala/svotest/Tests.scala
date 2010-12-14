package svotest

import org.scalatest._
import matchers._
import svo._



trait Data {

  implicit val u = new Universe
  
  // Tim is father of Bob, Jim
  'tim-'father-'bob!;
  'tim-'father-'jim!;
  
  // Tim kills bob, fred, gertrude
  'tim-'killed-'bob!; // `with` 'knife in 'kitchen)
  'tim-'killed-'fred!; // `with` 'knife in 'kitchen)
  'tim-'killed-'gertrude!; // `with` 'knife in 'kitchen)
  
  // Fred! How could you!
  'fred-'killed-'bob!; // `with` 'potatoPeeler in 'library)
  
  // Friends...
  'bob-'friends-'sally!;
  'tim-'friends-'roger!;
  'fred-'friends-'nathan!;
  'joe-'friends-'fred!;
}

class NounSpec extends Spec with ShouldMatchers  {
  describe("a Noun") {
    it("should auto-convert from a Symbol") {('bbb: Noun) should equal (Noun("bbb"))}
    it("should auto-convert from a String") {("bbb": Noun) should equal (Noun("bbb"))}
    it("should create an SV with S-V") {("noun"-"verb") should equal (SV(Noun("noun"), Verb("verb")))}
    it("shouldn't equal a verb with the same value") {"foo".n should not equal ("foo".v)}
  }
  describe("pattern matching") {
    it("Should match on the noun") { 
      ('zzz.n match {case Noun("zzz") => true; case _ => false}) should be (true)
    }
  }
}

class VerbSpec extends Spec with ShouldMatchers  {
  describe("a Verb") {
    it("should auto-convert from a Symbol") {('bbb: Verb) should equal (Verb("bbb"))}
    it("should auto-convert from a String") {("bbb": Verb) should equal (Verb("bbb"))}
  }
  describe("pattern matching") {
    it("Should match on the verb") { 
      ('zzz.v match {case Verb("zzz") => true; case _ => false}) should be (true)
    }
  }
}

class SimpleSVOSpec extends Spec with ShouldMatchers  {
  describe("S-V-O creation syntax") {
    it("should have the subject in first place") {
      val svo = 'bob-'hates-'cats
      svo.subj should equal ('bob.n)
    }
    it("should have the verb in 2nd place") {
      val svo = 'bob-'hates-'cats
      svo.verb should equal ('hates.v)
    }
    it("should have the object in 3rd place") {
      val svo = 'bob-'hates-'cats
      svo.obj should equal ('cats.n)
    }
  }
  describe("pattern matching") {
    it("should match s-v-o") {
      val www: SVO = "Wally"-"went"-"Wellington"
      (www match {case svo @ s-v-o => svo; case _ => null}) should equal (www)
    }
  }
}

class ComplexSVOSpec extends Spec with ShouldMatchers  {
  val bobHatesCats = "bob"-"hates"-"cats"--"with"-"passion"
  
  describe("S-V-O1--P-O2 creation syntax") {
    it("should have 1 preposition-object pair") {bobHatesCats.prepObjects.size should equal (1)}
    it("should have the given preposition") {bobHatesCats.prepObjects(0).prep should equal ('with.pp)}
    it("should have the given object") {bobHatesCats.prepObjects(0).obj should equal ('passion.n)}
  }
  describe("pattern matching") {

    it("should match s-v-o1--p-o2") {
      val wwwww = ('Wally-'went-'Wellington)--'with-'worries
      (wwwww match {case s-v-o1--p-o2 => wwwww}) should equal (wwwww)
    }
    it("should match s-v-o1--p-o2--p-o3") {
      val wwwwwot = 'Wally-'went-'Wellington--'with-'worries--'on-'Wednesday
      (wwwwwot match {case s-v-o1--p-o2 => wwwwwot; case _ => null}) should equal (wwwwwot)
    }
  }
}

class WildcardTest extends Spec with ShouldMatchers with Data {

  describe("S-V-* wildcard expansion") {
    it("should ") {('tim-'killed-*).toSet should equal (Set('bob.n, 'fred.n, 'gertrude.n))}
  }
  describe("Nested (S-V-*)-V-* wildcard expansion") {
    (('tim-'killed-*)-'friends-*).toSet should equal (Set('sally.n, 'nathan.n))
  }
  describe("S-*-O wildcard expansion") {
    ('tim-*-'bob).toSet should equal (Set('killed.v, 'father.v))
  }
  describe("Nested S-(S-*-O)-*) wildcard expansion") {
    ('tim-('tim-*-'bob)-*).toSet should equal (Set('bob.n, 'fred.n, 'gertrude.n, 'jim.n))
  }
}
