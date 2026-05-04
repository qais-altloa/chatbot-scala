// =============================================================
// File: TestModule3.scala
// Purpose: Tests all functions in Module 3
//          (ConversationMemory)
// Course: C-CS219 — Functional Programming
// =============================================================

import models.Models._
import modules.ConversationMemory._

object TestModule3 extends App {

  // ─────────────────────────────────────────────
  // Setup: build a sample conversation history
  // ─────────────────────────────────────────────
  val emptyState = ConversationState.empty

  println("╔══════════════════════════════════════════════════╗")
  println("║      MODULE 3 — CONVERSATION MEMORY TEST        ║")
  println("╚══════════════════════════════════════════════════╝")

  // ── Test 1: logInteraction ────────────────────
  println("\n=== Test 1: logInteraction ===")
  val state1 = logInteraction("Hello there",          "Welcome! How can I help?",        emptyState)
  val state2 = logInteraction("I need help with focus", "Let me suggest some techniques.", state1)
  val state3 = logInteraction("What is Pomodoro?",    "Pomodoro is a focus technique.",   state2)
  val state4 = logInteraction("I love this technique", "Great! Glad it is helpful.",      state3)
  val state5 = logInteraction("Recommend something for energy", "Here are energy techniques.", state4)
  println(s"Total interactions logged: ${state5.history.length}")
  // Expected: 5

  // ── Test 2: getConversationHistory ────────────
  println("\n=== Test 2: getConversationHistory ===")
  val history = getConversationHistory(state5)
  history.foreach { entry =>
    println(s"  [${entry.sequenceNo}] ${entry.userInput} | Intent: ${entry.detectedIntent}")
  }
  // Expected: 5 entries with correct intents

  // ── Test 3: getLastNInteractions ──────────────
  println("\n=== Test 3: getLastNInteractions (2) ===")
  val lastTwo = getLastNInteractions(2, state5)
  lastTwo.foreach { entry =>
    println(s"  [${entry.sequenceNo}] ${entry.userInput}")
  }
  // Expected: last 2 interactions

  // ── Test 4: detectRepeatedQuery ───────────────
  println("\n=== Test 4: detectRepeatedQuery ===")
  val repeated    = detectRepeatedQuery("What is Pomodoro technique?", state5.history)
  val notRepeated = detectRepeatedQuery("Tell me about kanban board",  state5.history)
  println(s"Is repeated (Pomodoro)    : $repeated")
  println(s"Is repeated (Kanban)      : $notRepeated")
  // Expected: true, false

  // ── Test 5: extractTopics ─────────────────────
  println("\n=== Test 5: extractTopics ===")
  val topics = extractTopics(state5.history)
  println(s"Topics discussed: $topics")
  // Expected: List with Focus, Pomodoro, Recommendations, Energy Management

  // ── Test 6: summarizeConversation ─────────────
  println("\n=== Test 6: summarizeConversation ===")
  println(summarizeConversation(state5.history))
  // Expected: summary with counts and topics

  // ── Test 7: summarizeConversation empty ───────
  println("\n=== Test 7: summarizeConversation (empty) ===")
  println(summarizeConversation(List.empty))
  // Expected: no history message

  // ── Test 8: getMostDiscussedTopics ────────────
  println("\n=== Test 8: getMostDiscussedTopics ===")
  val rankedTopics = getMostDiscussedTopics(state5.history)
  rankedTopics.foreach { case (topic, count) =>
    println(s"  $topic → $count time(s)")
  }
  // Expected: topics sorted by frequency

  // ── Test 9: getUserMood positive ──────────────
  println("\n=== Test 9: getUserMood (positive) ===")
  println(s"Mood: ${getUserMood(state5.history)}")
  // Expected: positive (because "I love this technique")

  // ── Test 10: getUserMood negative ─────────────
  println("\n=== Test 10: getUserMood (negative) ===")
  val negState1 = logInteraction("This is boring",     "I am sorry to hear that.", emptyState)
  val negState2 = logInteraction("Not helpful at all", "Let me try again.",        negState1)
  println(s"Mood: ${getUserMood(negState2.history)}")
  // Expected: negative

  // ── Test 11: getUserMood neutral ──────────────
  println("\n=== Test 11: getUserMood (neutral) ===")
  println(s"Mood: ${getUserMood(emptyState.history)}")
  // Expected: neutral

  // ── Test 12: handleSummaryRequest ─────────────
  println("\n=== Test 12: handleSummaryRequest [SUMMARY_REQUEST] ===")
  println(handleSummaryRequest("[SUMMARY_REQUEST]", state5))

  // ── Test 13: handleSummaryRequest analysis ────
  println("\n=== Test 13: handleSummaryRequest [ANALYSIS_REQUEST] ===")
  println(handleSummaryRequest("[ANALYSIS_REQUEST]", state5))

  println("\n╔══════════════════════════════════════════════════╗")
  println("║              ALL TESTS COMPLETE ✅               ║")
  println("╚══════════════════════════════════════════════════╝")
}
