
package modules

import models.Models._


/* process 
  Step 1:
  
    handleUserInput
  
  ↓
  
  Step 2:
  
    parseInput
  → ["i", "want", "help", "focusing"]

  ↓
  
  Step 3:
  
    detectIntent
      → "recommendation_request"

  ↓
  
  Step 4:
  
    generateResponse
    
  ↓
  Step 5:
  
    generateRecommendationPrompt */ 

object CoreChatbot {

  // ─────────────────────────────────────────────
  // greetUser()
  // Purpose: Returns an initial greeting when the chatbot starts.
  // ─────────────────────────────────────────────
  def greetUser(): String =
    """
      |╔══════════════════════════════════════════════════════╗
      |║       Welcome to the Productivity Techniques Bot     ║
      |╚══════════════════════════════════════════════════════╝
      |
      | Good day! I am your personal Productivity Assistant.
      | I am here to help you work smarter, manage your time
      | effectively, and achieve your goals with proven techniques.
      |
      | Here is what I can assist you with:
      |  • Recommending productivity techniques tailored to your needs
      |  • Explaining methods such as Pomodoro, Deep Work, GTD, and more
      |  • Tracking your preferences and conversation history
      |  • Summarizing our conversation at any point
      |
      | To get started, you may type something like:
      |  → "I need help focusing"
      |  → "Suggest a technique for time management"
      |  → "What is the Pomodoro technique?"
      |  → "Show my preferences"
      |  → "Summarize our conversation"
      |  → "quit" to exit
      |
      | How may I assist you today?
    """.stripMargin

  // ─────────────────────────────────────────────
  
  // ─────────────────────────────────────────────
  def parseInput(input: String): List[String] =
    input
      .toLowerCase
      .replaceAll("[^a-z0-9 ]", "")  // remove punctuation
      .split("\\s+")                 // split on whitespace
      .toList
      .filter(_.nonEmpty)            // remove empty strings

  // ─────────────────────────────────────────────
  
  // ─────────────────────────────────────────────
  def containsAny(tokens: List[String], keywords: List[String]): Boolean =
    keywords.exists(kw => tokens.exists(t => t.contains(kw)))

  // ─────────────────────────────────────────────
  
  // ─────────────────────────────────────────────
  def detectIntent(tokens: List[String]): String =
    tokens match {
      case t if containsAny(t, List("hi", "hello", "hey", "greetings", "good"))
      => "greeting"

      case t if containsAny(t, List("recommend", "suggest", "give", "best", "help me choose"))
      => "recommendation_request"

      case t if containsAny(t, List("what", "explain", "tell", "how", "describe", "define"))
      => "explanation_request"

      case t if containsAny(t, List("prefer", "like", "want", "goal", "need", "update", "set"))
      => "preference_update"

      case t if containsAny(t, List("history", "summary", "summarize", "recap", "so far"))
      => "summary_request"

      case t if containsAny(t, List("mood", "topics", "discussed", "most talked"))
      => "analysis_request"

      case t if containsAny(t, List("quit", "exit", "bye", "goodbye", "stop"))
      => "exit"

      case _
      => "unknown"
    }

  
  // ─────────────────────────────────────────────
  def generateResponse(query: String, state: ConversationState): String = {
    val tokens = parseInput(query)
    val intent = detectIntent(tokens)

    intent match {
      case "greeting"               => generateGreetingResponse(state)
      case "recommendation_request" => generateRecommendationPrompt(tokens, state)
      case "explanation_request"    => generateExplanationResponse(tokens)
      case "preference_update"      => generatePreferenceUpdateResponse(tokens, state)
      case "summary_request"        => "[SUMMARY_REQUEST]"   // Module 3 handles this
      case "analysis_request"       => "[ANALYSIS_REQUEST]"  // Module 3 handles this
      case "exit"                   => generateFarewellResponse(state)
      case _                        => generateFallbackResponse(tokens)
    }
  }

  
  def handleUserInput(input: String, state: ConversationState): (String, String) = {

    val safeInput: Option[String] = Option(input).filter(_.trim.nonEmpty)

    safeInput match {
      case None =>
        ("I did not receive any input. Please type your query and press Enter.", "empty_input")

      case Some(userText) =>
        val tokens   = parseInput(userText)
        val intent   = detectIntent(tokens)
        val response = generateResponse(userText, state)
        (response, intent)
    }
  }

  

  private def generateGreetingResponse(state: ConversationState): String = {

    // ✅ Pattern matching on session count
    val sessionInfo = state.sessionCount match {
      case 1 => "It is a pleasure to meet you."
      case 2 => "Welcome back! Great to see you again."
      case n => s"Welcome back! This is session $n. Let us continue making progress."
    }

    s"""
       | $sessionInfo
       | I am your Productivity Techniques Assistant.
       | How may I assist you today? You may ask me to:
       |   • Recommend a productivity technique
       |   • Explain a specific method (e.g., "What is Deep Work?")
       |   • Update your preferences (e.g., "I prefer short sessions")
       |   • Summarize our conversation
    """.stripMargin
  }

  private def generateRecommendationPrompt(tokens: List[String], state: ConversationState): String = {
    
    val goalHint: Option[String] = tokens match {
      case t if containsAny(t, List("focus", "concentrate", "distraction")) => Some("focus")
      case t if containsAny(t, List("time", "schedule", "plan", "organiz")) => Some("time_management")
      case t if containsAny(t, List("energy", "motivation", "burnout", "tired")) => Some("energy_management")
      case t if containsAny(t, List("priorit", "important", "urgent", "task")) => Some("prioritization")
      case _ => None
    }


    goalHint match {
      case Some(goal) =>
        s"""
           | I understand you are looking for assistance with ${goal.replace("_", " ")}.
           | Let me retrieve the most suitable techniques for you.
           | [RECOMMENDATION_REQUEST:$goal]
        """.stripMargin

      case None =>
        """
          | I would be happy to recommend a productivity technique.
          | To provide the most relevant suggestion, could you tell me:
          |   • What is your primary goal?
          |     (focus / time management / energy / prioritization)
          |   • How much time do you have available?
          |     (short: <15min / medium: 25-30min / long: 60min+)
          |   • What is your experience level?
          |     (beginner / intermediate / advanced)
        """.stripMargin
    }
  }

  private def generateExplanationResponse(tokens: List[String]): String = {
    
    val technique: Option[String] = tokens match {
      case t if containsAny(t, List("pomodoro"))              => Some("Pomodoro")
      case t if containsAny(t, List("deep", "work"))          => Some("Deep Work")
      case t if containsAny(t, List("gtd", "getting", "done"))=> Some("GTD")
      case t if containsAny(t, List("time", "block"))         => Some("Time Blocking")
      case t if containsAny(t, List("eisenhower"))            => Some("Eisenhower Matrix")
      case t if containsAny(t, List("2", "minute", "two"))    => Some("2-Minute Rule")
      case t if containsAny(t, List("eat", "frog"))           => Some("Eat The Frog")
      case t if containsAny(t, List("mind", "map"))           => Some("Mind Mapping")
      case t if containsAny(t, List("pareto", "80", "20"))    => Some("Pareto Principle")
      case t if containsAny(t, List("kanban"))                => Some("Kanban")
      case _                                                   => None
    }

    technique match {
      case Some(name) =>
        s"[EXPLANATION_REQUEST:$name]" // Passed to RecommendationEngine

      case None =>
        """
          | I would be glad to explain a productivity technique.
          | Could you please specify which technique you mean?
          |   • "What is the Pomodoro Technique?"
          |   • "Explain Deep Work"
          |   • "Tell me about the Eisenhower Matrix"
          |   • "What is GTD?"
          |   • "How does Time Blocking work?"
        """.stripMargin
    }
  }

  private def generatePreferenceUpdateResponse(tokens: List[String], state: ConversationState): String = {
    
    val prefKey: Option[(String, String)] = tokens match {
      case t if containsAny(t, List("focus", "concentrate"))      => Some("goal", "focus")
      case t if containsAny(t, List("time", "schedule", "plan"))  => Some("goal", "time_management")
      case t if containsAny(t, List("energy", "motivation"))      => Some("goal", "energy_management")
      case t if containsAny(t, List("priorit", "urgent"))         => Some("goal", "prioritization")
      case t if containsAny(t, List("beginner"))                  => Some("difficulty", "beginner")
      case t if containsAny(t, List("intermediate"))              => Some("difficulty", "intermediate")
      case t if containsAny(t, List("advanced"))                  => Some("difficulty", "advanced")
      case t if containsAny(t, List("short", "quick", "5", "10")) => Some("duration", "short")
      case t if containsAny(t, List("medium", "25", "30"))        => Some("duration", "medium")
      case t if containsAny(t, List("long", "hour", "60", "90"))  => Some("duration", "long")
      case _                                                       => None
    }

    prefKey match {
      case Some((key, value)) =>
        s"""
           | [PREFERENCE_UPDATE:$key:$value]
           | Noted. I have updated your preference:
           |   ${key.capitalize} → ${value.replace("_", " ")}
           | Your future recommendations will reflect this preference.
        """.stripMargin

      case None =>
        """
          | I would like to update your preferences.
          | Could you clarify what you would like to set?
          |   • "I prefer beginner-level techniques"
          |   • "My goal is to improve focus"
          |   • "I have short sessions available"
          |   • "I am an advanced user"
        """.stripMargin
    }
  }

  private def generateFarewellResponse(state: ConversationState): String = {
    
    val message = state.history.length match {
      case 0 => "It seems we did not get to interact much. I hope to assist you next time!"
      case n if n <= 5 => s"We covered $n interaction(s). I hope our session was useful!"
      case n => s"We had a productive session with $n interactions. Excellent engagement!"
    }

    s"""
       | $message
       | Thank you for using the Productivity Techniques Assistant.
       | Goodbye, and best of luck with your productivity journey!
    """.stripMargin
  }

  private def generateFallbackResponse(tokens: List[String]): String = {

    val hint: String = tokens match {
      case t if containsAny(t, List("pomodoro", "deep", "gtd", "block", "eisenhower", "frog", "mind", "kanban", "pareto"))
      => "It appears you may be asking about a technique. Try: \"What is [technique name]?\""
      case t if containsAny(t, List("help", "can", "able"))
      => "You may ask me to recommend a technique, explain a method, or update your preferences."
      case Nil
      => "Please provide a query and I will do my best to assist you."
      case t
      => s"I noticed: ${t.take(3).mkString(", ")}. Could you elaborate on what you need?"
    }

    s"""
       | I was unable to fully interpret your request.
       | $hint
       |
       | Here are some examples of what you can ask:
       |   • "Recommend a technique for focus"
       |   • "What is the Pomodoro Technique?"
       |   • "I prefer advanced techniques"
       |   • "Summarize our conversation"
    """.stripMargin
  }
}
