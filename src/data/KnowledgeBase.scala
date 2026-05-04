

package data

import models.Models.Technique

object KnowledgeBase {

  val allTechniques: List[Technique] = List(

    // ── FOCUS ──────────────────────────────────
    Technique(
      name        = "Pomodoro Technique",
      category    = "focus",
      duration    = 25,
      difficulty  = "beginner",
      description = "Work in focused 25-minute intervals followed by a 5-minute break. After 4 intervals, take a longer 15-30 minute break. Leverages time-boxing to maintain high concentration.",
      tags        = List("timer", "breaks", "focus", "intervals", "beginner")
    ),

    Technique(
      name        = "Deep Work",
      category    = "focus",
      duration    = 90,
      difficulty  = "advanced",
      description = "Schedule long uninterrupted blocks of cognitively demanding work. Eliminate all distractions and enter a state of complete focus. Coined by Cal Newport.",
      tags        = List("focus", "concentration", "distraction-free", "advanced")
    ),

    Technique(
      name        = "Single-Tasking",
      category    = "focus",
      duration    = 45,
      difficulty  = "beginner",
      description = "Commit to working on one task at a time until completion. Multitasking reduces efficiency by up to 40%. Single-tasking restores full cognitive capacity.",
      tags        = List("focus", "one-task", "beginner", "concentration")
    ),

    Technique(
      name        = "Flow State Method",
      category    = "focus",
      duration    = 60,
      difficulty  = "advanced",
      description = "Deliberately engineer conditions that trigger complete immersion. Match task difficulty to your skill level and eliminate all interruptions.",
      tags        = List("flow", "immersion", "peak-performance", "focus", "advanced")
    ),

    // ── TIME MANAGEMENT ────────────────────────
    Technique(
      name        = "Getting Things Done",
      category    = "time_management",
      duration    = 60,
      difficulty  = "intermediate",
      description = "A 5-step system: Capture all tasks, Clarify what they mean, Organize into lists, Reflect weekly, Engage with the right task. Developed by David Allen.",
      tags        = List("gtd", "organize", "capture", "planning", "intermediate")
    ),

    Technique(
      name        = "Time Blocking",
      category    = "time_management",
      duration    = 30,
      difficulty  = "intermediate",
      description = "Divide your day into dedicated time blocks each assigned to a specific task. Prevents multitasking and ensures every hour is intentionally allocated.",
      tags        = List("schedule", "calendar", "planning", "blocks", "intermediate")
    ),

    Technique(
      name        = "2-Minute Rule",
      category    = "time_management",
      duration    = 2,
      difficulty  = "beginner",
      description = "If a task takes less than 2 minutes to complete, do it immediately. Prevents small tasks from accumulating into overwhelming backlogs.",
      tags        = List("quick", "immediate", "small-tasks", "beginner", "time_management")
    ),

    Technique(
      name        = "Batch Processing",
      category    = "time_management",
      duration    = 45,
      difficulty  = "intermediate",
      description = "Group similar tasks together and complete them in a single dedicated session. Reduces context-switching overhead and improves efficiency.",
      tags        = List("batching", "grouping", "efficiency", "intermediate")
    ),

    Technique(
      name        = "Weekly Review",
      category    = "time_management",
      duration    = 60,
      difficulty  = "beginner",
      description = "Dedicate one hour each week to review completed tasks, update your lists, plan the upcoming week, and reflect on progress toward your goals.",
      tags        = List("review", "weekly", "reflection", "planning", "beginner")
    ),

    // ── PRIORITIZATION ─────────────────────────
    Technique(
      name        = "Eisenhower Matrix",
      category    = "prioritization",
      duration    = 20,
      difficulty  = "beginner",
      description = "Categorize tasks into 4 quadrants: Urgent and Important, Not Urgent and Important, Urgent and Not Important, Not Urgent and Not Important.",
      tags        = List("matrix", "urgent", "important", "quadrant", "beginner")
    ),

    Technique(
      name        = "Eat The Frog",
      category    = "prioritization",
      duration    = 60,
      difficulty  = "intermediate",
      description = "Tackle your most important and most dreaded task first thing in the morning. Eliminates procrastination and builds momentum for the rest of the day.",
      tags        = List("morning", "priority", "procrastination", "intermediate")
    ),

    Technique(
      name        = "Pareto Principle",
      category    = "prioritization",
      duration    = 30,
      difficulty  = "intermediate",
      description = "Identify the 20% of tasks that produce 80% of your results. Focus energy on high-impact activities and ruthlessly deprioritize the rest.",
      tags        = List("pareto", "high-impact", "prioritization", "intermediate")
    ),

    Technique(
      name        = "Ivy Lee Method",
      category    = "prioritization",
      duration    = 15,
      difficulty  = "beginner",
      description = "Each evening write down the 6 most important tasks for the next day. Rank them in order of priority and work through them sequentially.",
      tags        = List("ivy-lee", "evening", "priority", "simple", "beginner")
    ),

    Technique(
      name        = "MoSCoW Method",
      category    = "prioritization",
      duration    = 20,
      difficulty  = "beginner",
      description = "Classify tasks as Must Have, Should Have, Could Have, or Won't Have. Ensures critical work is always completed first.",
      tags        = List("moscow", "must", "should", "prioritization", "beginner")
    ),

    // ── ENERGY MANAGEMENT ──────────────────────
    Technique(
      name        = "Ultradian Rhythm Method",
      category    = "energy_management",
      duration    = 90,
      difficulty  = "intermediate",
      description = "Work with your body's natural 90-minute energy cycles. Work intensely for 90 minutes then rest for 20 minutes.",
      tags        = List("energy", "rhythm", "rest", "cycles", "intermediate")
    ),

    Technique(
      name        = "Energy Audit",
      category    = "energy_management",
      duration    = 30,
      difficulty  = "beginner",
      description = "Track your energy levels throughout the day for one week. Identify your peak energy windows and schedule high-priority tasks accordingly.",
      tags        = List("energy", "audit", "tracking", "peak-hours", "beginner")
    ),

    Technique(
      name        = "Strategic Rest",
      category    = "energy_management",
      duration    = 20,
      difficulty  = "beginner",
      description = "Schedule deliberate rest periods throughout your workday. Includes micro-breaks, short breaks, and naps. Rest restores cognitive capacity.",
      tags        = List("rest", "breaks", "nap", "recovery", "beginner")
    ),

    // ── PLANNING ───────────────────────────────
    Technique(
      name        = "Mind Mapping",
      category    = "planning",
      duration    = 30,
      difficulty  = "beginner",
      description = "Create a visual diagram starting with a central idea branching into subtopics. Engages both logical and creative thinking for brainstorming and planning.",
      tags        = List("mind-map", "visual", "brainstorm", "planning", "beginner")
    ),

    Technique(
      name        = "Kanban Board",
      category    = "planning",
      duration    = 15,
      difficulty  = "beginner",
      description = "Visualize your workflow using three columns: To Do, In Progress, Done. Limit work-in-progress to avoid overloading yourself.",
      tags        = List("kanban", "board", "visual", "workflow", "planning", "beginner")
    ),

    Technique(
      name        = "SMART Goals",
      category    = "planning",
      duration    = 30,
      difficulty  = "intermediate",
      description = "Define goals that are Specific, Measurable, Achievable, Relevant, and Time-bound. Transforms vague intentions into clear actionable targets.",
      tags        = List("smart", "goals", "framework", "planning", "intermediate")
    )
  )
}
