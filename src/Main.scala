

import models.Models._
import modules.CoreChatbot._
import modules.RecommendationEngine._
import modules.ConversationMemory._

object Main extends App {

  // ─────────────────────────────────────────────

  // ─────────────────────────────────────────────
  var state = ConversationState.empty

  // ─────────────────────────────────────────────

  // ─────────────────────────────────────────────
  println(greetUser())

  // ─────────────────────────────────────────────
  
  // ─────────────────────────────────────────────
  var running = true

  while (running) {

    // Read user input
    print("\nYou: ")
    val userInput = scala.io.StdIn.readLine()

    // Step 1: Handle input through Module 1
    val (response, intent) = handleUserInput(userInput, state)

    // Step 2: Check if exit
    intent match {
      case "exit" =>
        println(s"\nBot: $response")
        running = false

      case "empty_input" =>
        println(s"\nBot: $response")

      case "summary_request" =>
        // Step 3a: Route to Module 3 for summary
        val summaryResponse = handleSummaryRequest("[SUMMARY_REQUEST]", state)
        println(s"\nBot: $summaryResponse")
        state = logInteraction(userInput, summaryResponse, state)

      case "analysis_request" =>
        // Step 3b: Route to Module 3 for analysis
        val analysisResponse = handleSummaryRequest("[ANALYSIS_REQUEST]", state)
        println(s"\nBot: $analysisResponse")
        state = logInteraction(userInput, analysisResponse, state)

      case _ =>
        // Step 3c: Check if response contains a signal tag for Module 2
        val finalResponse = response match {

          case r if r.contains("[RECOMMENDATION_REQUEST:") =>
            // Extract tag and pass to Module 2
            val tag = extractTag(r, "[RECOMMENDATION_REQUEST:")
            handleRecommendationRequest(tag, state)

          case r if r.contains("[EXPLANATION_REQUEST:") =>
            val tag = extractTag(r, "[EXPLANATION_REQUEST:")
            handleRecommendationRequest(tag, state)

          case r if r.contains("[PREFERENCE_UPDATE:") =>
            val tag = extractTag(r, "[PREFERENCE_UPDATE:")
            // Update preferences in state
            val parts = tag.replace("[PREFERENCE_UPDATE:", "").replace("]", "").split(":")
            if (parts.length == 2) {
              state = updatePreferences(parts(0), parts(1), state)
            }
            handleRecommendationRequest(tag, state)

          case r => r
        }

        // Step 4: Check for repeated query using Module 3
        val repeatedMessage = if (detectRepeatedQuery(userInput, state.history)) {
          "\n| (Note: You have asked about this before. Here is my response again.)"
        } else {
          ""
        }

        // Step 5: Adapt response based on mood
        val mood = getUserMood(state.history)
        val moodPrefix = mood match {
          case "negative" => "| I sense some frustration. Let me try to help better.\n"
          case "positive" => "| Great energy! Let us keep going.\n"
          case _          => ""
        }

        val fullResponse = moodPrefix + repeatedMessage + finalResponse
        println(s"\nBot: $fullResponse")

        // Step 6: Log interaction using Module 3
        state = logInteraction(userInput, fullResponse, state)
    }
  }

  // ─────────────────────────────────────────────
  
  // ─────────────────────────────────────────────
  def extractTag(response: String, tagStart: String): String = {
    val startIndex = response.indexOf(tagStart)
    val endIndex   = response.indexOf("]", startIndex)
    if (startIndex >= 0 && endIndex >= 0) {
      response.substring(startIndex, endIndex + 1)
    } else {
      ""
    }
  }
}
