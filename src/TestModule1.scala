import models.Models._
import modules.CoreChatbot._

object TestModule1 extends App {

  // Test 1: greetUser
  println("=== Test greetUser ===")
  println(greetUser())

  // Test 2: parseInput
  println("=== Test parseInput ===")
  println(parseInput("Hello! I need help with FOCUS."))
  // Expected: List(hello, i, need, help, with, focus)

  // Test 3: detectIntent
  println("=== Test detectIntent ===")
  println(detectIntent(List("recommend", "technique")))
  // Expected: recommendation_request

  println(detectIntent(List("what", "is", "pomodoro")))
  // Expected: explanation_request

  println(detectIntent(List("i", "prefer", "beginner")))
  // Expected: preference_update

  // Test 4: handleUserInput
  println("=== Test handleUserInput ===")
  val state = ConversationState.empty

  val (res1, intent1) = handleUserInput("Hello!", state)
  println(s"Intent: $intent1")
  println(res1)

  val (res2, intent2) = handleUserInput("", state)
  println(s"Intent: $intent2")
  println(res2)

  val (res3, intent3) = handleUserInput("Suggest a focus technique", state)
  println(s"Intent: $intent3")
  println(res3)
}