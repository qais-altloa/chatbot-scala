package models


object Models {

  // ─────────────────────────────────────────────
  // Technique
  // Represents one productivity technique in the knowledge base.
  // Immutable by default (case class).
  // ─────────────────────────────────────────────
  case class Technique(
    name        : String,       // e.g. "Pomodoro Technique"
    category    : String,       // focus | time-management | energy | planning | prioritization
    duration    : Int,          // estimated duration in minutes
    difficulty  : String,       // beginner | intermediate | advanced
    description : String,       // full explanation of the technique
    tags        : List[String]  // e.g. List("timer", "breaks", "focus")
  )

  // ─────────────────────────────────────────────
  // InteractionEntry
  // Represents one turn in the conversation (user + bot).
  // Used by Module 3 (Conversation Memory).
  // ─────────────────────────────────────────────
  case class InteractionEntry(
    sequenceNo     : Int,     // turn number (1, 2, 3, ...)
    timestamp      : String,  // time of interaction (ISO string)
    userInput      : String,  // what the user typed
    botResponse    : String,  // what the bot replied
    detectedIntent : String   // classified intent (e.g. "recommendation_request")
  )

  // ─────────────────────────────────────────────
  // ConversationState
  // Holds the full immutable state of the conversation.
  // Never mutated — each update returns a NEW state.
  // ─────────────────────────────────────────────
  case class ConversationState(
    history      : List[InteractionEntry],  // full conversation log
    preferences  : Map[String, String],     // user preferences (e.g. goal -> focus)
    sessionCount : Int                      // how many sessions the user has had
  )

  // ─────────────────────────────────────────────
  // Companion object: ConversationState
  // Provides a clean empty starting state.
  // ─────────────────────────────────────────────
  object ConversationState {
    def empty: ConversationState = ConversationState(
      history      = List.empty,
      preferences  = Map.empty,
      sessionCount = 1
    )
  }

  // ─────────────────────────────────────────────
  // Intent (ADT — Algebraic Data Type)
  // Models all possible user intents as sealed traits.
  // Used with pattern matching for exhaustive handling.
  // ─────────────────────────────────────────────
  sealed trait Intent
  case object Greeting            extends Intent
  case object RecommendationReq   extends Intent
  case object ExplanationReq      extends Intent
  case object PreferenceUpdate    extends Intent
  case object SummaryRequest      extends Intent
  case object AnalysisRequest     extends Intent
  case object ExitRequest         extends Intent
  case object UnknownIntent       extends Intent
}
