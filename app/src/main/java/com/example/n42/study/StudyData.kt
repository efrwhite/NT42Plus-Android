package com.example.n42.study


enum class ReminderType(val label: String){
    WeeklySurvey("Weekly survey"),
    CookingClass("Cooking class"),
    Appointment("Study appointment"),
    StudyEvent("Study event")
}

data class Reminder(
    val id: Int,
    val title: String,
    val subtitle: String,
    val type: ReminderType,
    val whenText: String,
    val where: String,
    val details: String
)

enum class TipType(val label: String){
    Recipe("Recipe"),
    CookingTip("Cooking tip"),
    GroceryTip("Grocery tip"),
    Education("Education")
}

data class Tip(
    val id: Int,
    val title: String,
    val summary: String,
    val type: TipType,
    val body: String,
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList()
)

//sample data. The screens only call these two functions.
//when backend exists, only the inside of these two functions changes

fun getReminders(): List<Reminder>{
    return listOf(
        Reminder(
            id =1,
            title = "Weekly check-in is open",
            subtitle = "Your week 10 check-in is ready",
            type = ReminderType.WeeklySurvey,
            whenText = "Open now, closes Sunday at 11:59 PM",
            where = "In the app",
            details = "Please answer the short weekly survey about your meals, energy, and mood this week."
        ),
        Reminder(
            id =2,
            title = "Vitamin reminder",
            subtitle = "Don't forget todays dose",
            type = ReminderType.StudyEvent,
            whenText = "Today",
            where = "At home",
            details = "Take your vitamins with a meal, Do not double up if you miss a day."
        ),
        Reminder(
            id = 3,
            title = "Appointment",
            subtitle = "Thursday at 1:30 PM.",
            type = ReminderType.Appointment,
            whenText = "Thursday at 1:30 PM.",
            where = "Study clinic, Room 204",
            details = "Your regular visit with your doctor."
        ),
        Reminder(
            id = 4,
            title = "Cooking class: Easy breakfasts",
            subtitle = "Saturday at 10:00 AM",
            type = ReminderType.CookingClass,
            whenText = "Saturday at 10:00 AM",
            where = "Community kitchen",
            details = "Learn three quick, healthy breakfasts. All ingredients are provided."
        ),
        Reminder(
            id = 5,
            title = "Study group meeting",
            subtitle = "Friday at 5:00 PM",
            type = ReminderType.StudyEvent,
            whenText = "Friday at 5:00 PM",
            where = "Online video call",
            details = "Meet other participants and the study team to share tips and ask questions"
        )

    )
}

fun getTips(): List<Tip>{
    return listOf(
        Tip(
            id = 1,
            title = "Ginger tea for nausea",
            summary = "Steep fresh ginger in hot water for 5-10 mintes.",
            type = TipType.CookingTip,
            body = "Ginger is a gentle, popular home remedy for an upset stomach. Slice a thumb-sized piece of fresh ginger, pour hot water over it and let it steep for 5 to 10 minutes. Sip slowly. Add a little lemon or honey if you like."
        ),
        Tip(
            id = 2,
            title = "Overnight oats",
            summary = "A no-cook breakfast you make the night before",
            type = TipType.Recipe,
            body = "A creamy, filling breakfast that is ready when you wake up.",
            ingredients = listOf(
                "1/2 cup of rolled oats",
                "1/2 cup milk or plant milk",
                "1/4 cup of plain yogurt",
                "1 tablespoon chia seeds",
                "Fresh fruit to top"
            ),
            steps = listOf(
                "Mix the oats, milk, yogurt and chia seeds in a jar.",
                "Cover and put it in the fridge overnight.",
                "In the morning, stir and add fresh fruit."
            )
    ),
        Tip(
            id = 3,
            title = "Read the unit price",
            summary = "Compare teh price per ounce, not the sticker price.",
            type = TipType.GroceryTip,
            body = "The small label under an item shows its unit price (price per ounce or per 100g). Use it to compare different sizes and brands fairly, the bigger package is not always the cheaper one."
        ),
        Tip(
            id = 4,
            title = "What is fibre?",
            summary = "Why fibre helps your digestion and keeps you full",
            type = TipType.Education,
            body = "Fibre is the part of plant foods your body cannot fully digest. It helps digestion, keeps you feeling full and supports steady energy. Good sources are beans, lentils, oats, and vegetables, fruits and whole grains. Increase fibre slowly and drink plenty of water."
        )
    )
}
