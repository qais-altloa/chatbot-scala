
import models.Models._
import modules.RecommendationEngine._
import data.KnowledgeBase.allTechniques

object TestModule2 extends App {

  // Setup
  val emptyState = ConversationState.empty

  println("╔══════════════════════════════════════════════════╗")
  println("║      MODULE 2 — RECOMMENDATION ENGINE TEST      ║")
  println("╚══════════════════════════════════════════════════╝")

  // ── Test 1: getUserPreferences empty ──────────
  println("\n=== Test 1: getUserPreferences (empty) ===")
  println(s"Preferences: ${getUserPreferences(emptyState)}")
  // Expected: Map()

  // ── Test 2: updatePreferences ─────────────────
  println("\n=== Test 2: updatePreferences ===")
  val state1 = updatePreferences("goal",       "focus",    emptyState)
  val state2 = updatePreferences("difficulty", "beginner", state1)
  val state3 = updatePreferences("duration",   "short",    state2)
  println(s"After updates: ${getUserPreferences(state3)}")
  // Expected: Map(goal -> focus, difficulty -> beginner, duration -> short)

  // ── Test 3: scoreItem ─────────────────────────
  println("\n=== Test 3: scoreItem ===")
  val preferences = getUserPreferences(state3)
  val pomodoro    = allTechniques.find(_.name == "Pomodoro Technique").get
  val deepWork    = allTechniques.find(_.name == "Deep Work").get
  println(s"Pomodoro score : ${scoreItem(pomodoro, preferences)}")
  println(s"Deep Work score: ${scoreItem(deepWork, preferences)}")
  // Expected: Pomodoro higher than Deep Work

  // ── Test 4: recommend top 3 ───────────────────
  println("\n=== Test 4: recommend (focus + beginner + short) ===")
  recommend(preferences, allTechniques) match {
    case None =>
      println("No recommendations found.")
    case Some(techniques) =>
      techniques.zipWithIndex.foreach { case (t, i) =>
        println(s"${i + 1}. ${t.name} | ${t.category} | ${t.difficulty} | ${t.duration} min")
      }
  }

  // ── Test 5: recommend no preferences ──────────
  println("\n=== Test 5: recommend (no preferences) ===")
  recommend(Map.empty, allTechniques) match {
    case None    => println("Result: None ✅")
    case Some(l) => println(s"Got ${l.length} results")
  }

  // ── Test 6: explainRecommendation ─────────────
  println("\n=== Test 6: explainRecommendation ===")
  println(explainRecommendation(pomodoro, preferences))

  // ── Test 7: explainTechnique known ────────────
  println("\n=== Test 7: explainTechnique (Pomodoro) ===")
  explainTechnique("Pomodoro") match {
    case Some(explanation) => println(explanation)
    case None              => println("Not found")
  }

  // ── Test 8: explainTechnique unknown ──────────
  println("\n=== Test 8: explainTechnique (Unknown) ===")
  explainTechnique("Flying Technique") match {
    case Some(_) => println("Found unexpectedly")
    case None    => println("Result: None ✅")
  }

  // ── Test 9: handleRecommendationRequest ───────
  println("\n=== Test 9: handleRecommendationRequest (focus) ===")
  println(handleRecommendationRequest("[RECOMMENDATION_REQUEST:focus]", state3))

  // ── Test 10: explanation tag ──────────────────
  println("\n=== Test 10: handleRecommendationRequest (Eisenhower) ===")
  println(handleRecommendationRequest("[EXPLANATION_REQUEST:Eisenhower]", state3))

  // ── Test 11: getAllCategories ──────────────────
  println("\n=== Test 11: getAllCategories ===")
  println(getAllCategories())

  // ── Test 12: getTechniquesByCategory ──────────
  println("\n=== Test 12: getTechniquesByCategory (focus) ===")
  getTechniquesByCategory("focus") match {
    case None    => println("No techniques found")
    case Some(l) => l.foreach(t => println(s"  - ${t.name}"))
  }

  println("\n╔══════════════════════════════════════════════════╗")
  println("║              ALL TESTS COMPLETE ✅               ║")
  println("╚══════════════════════════════════════════════════╝")
}
