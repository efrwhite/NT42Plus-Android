package com.example.n42.study

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Title and subtitle at the top of a screen
@Composable
fun StudyHeader(title: String, subtitle: String){
    val colors = studyColors()

    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            text = title,
            modifier = Modifier.padding(start = 40.dp, top = 24.dp),
            color = colors.screenTitle,
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = subtitle,
            modifier = Modifier.padding(start = 40.dp),
            color = colors.subtitle,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))
        //divider line
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(colors.divider)
        )
    }
}

//a full screen page with a back button, used by both detail pages
@Composable
fun DetailPage(onBackClick: () -> Unit, content: @Composable () -> Unit){
    val colors = studyColors()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 25.dp)
    )
    {
        TextButton(onClick = onBackClick){
            Text("← Back", color = colors.screenTitle, fontSize = 16.sp)
        }
        content()
        Spacer(modifier=Modifier.height(32.dp))
    }
}

//Reminders
fun iconFor(type: ReminderType): ImageVector{
    return when(type){
        ReminderType.WeeklySurvey -> Icons.Outlined.AssignmentTurnedIn
        ReminderType.CookingClass -> Icons.Outlined.Restaurant
        ReminderType.Appointment -> Icons.Outlined.CalendarMonth
        ReminderType.StudyEvent -> Icons.Outlined.HourglassEmpty
    }
}

//Reminders list screen
@Composable
fun RemindersScreen(
    onReminderClick: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val reminders = remember { getReminders() }

    Column(modifier.fillMaxSize().background(studyColors().background)) {
        StudyHeader(title = "Reminders", subtitle = "Sent by the study team")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        )
        {
            items(reminders) { reminder -> ReminderRow(
                reminder = reminder,
                onClick = {onReminderClick(reminder)}
            )
            }
        }
    }
}

@Composable
fun ReminderRow(reminder: Reminder, onClick: () -> Unit){
    val colors = studyColors()
    val shape = RoundedCornerShape(15.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(shape)
            .background(colors.card)
            .border(1.dp, colors.cardBorder,shape)
            .clickable(onClick = onClick)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Box(
            modifier= Modifier
                .size(80.dp)
                .background(colors.tile, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = iconFor(reminder.type),
                contentDescription = reminder.type.label,
                tint = colors.onTile,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Column{
            Text(
                text = reminder.title,
                color = colors.cardTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = reminder.subtitle,
                color = colors.subtitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

//the page you see after clicking a reminder
@Composable
fun ReminderDetailScreen(reminder: Reminder, onBackClick: () -> Unit){
    val colors = studyColors()

    DetailPage(onBackClick = onBackClick){
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(colors.tile, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = iconFor(reminder.type),
                contentDescription = null,
                tint = colors.onTile,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(reminder.type.label, color = colors.subtitle, fontSize = 14.sp)
        Text(
            text = reminder.title,
            color = colors.screenTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        DetailLine(label = "When", value = reminder.whenText)
        DetailLine(label = "Where", value = reminder.where)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = reminder.details,
            color = colors.bodyText,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun DetailLine(label: String, value: String){
    val colors = studyColors()

    Column(modifier = Modifier.padding(bottom = 12.dp)){
        Text(label,color = colors.subtitle, fontSize = 14.sp)
        Text(value, color = colors.bodyText, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

//Tips list screen
@Composable
fun TipsScreen(
    onTipClick: (Tip) -> Unit,
    modifier: Modifier = Modifier
) {
    val tips = remember { getTips() }

    Column(modifier.fillMaxSize().background(studyColors().background)) {
        StudyHeader(title = "Weekly Tips", subtitle = "From the Research Team")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        )
        {
            items(tips){
                tip -> TipCard(
                    tip = tip,
                    onClick = {onTipClick(tip)}
                )
            }
        }
    }
}


@Composable
fun TipCard(tip: Tip, onClick: () -> Unit){
    val colors = studyColors()
    val shape = RoundedCornerShape(15.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(4.dp, shape)
            .clip(shape)
            .background(colors.tipCard)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    )
    {
        Text(
            text = tip.title,
            color = colors.onTipCard,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = tip.summary,
            color = colors.onTipCard,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//the page you see after tapping a tip. Recipes also show ingredients and steps
@Composable
fun TipDetailScreen(tip: Tip, onBackClick: () -> Unit){
    val colors = studyColors()

    DetailPage(onBackClick = onBackClick){
        Text(tip.type.label, color = colors.subtitle, fontSize = 14.sp)
        Text(
            text = tip.title,
            color = colors.screenTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = tip.body,
            color = colors.bodyText,
            fontSize = 16.sp
        )

        //only recipes have ingredients
        if(tip.ingredients.isNotEmpty()){
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Ingredients",
                color = colors.screenTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            for(ingredient in tip.ingredients){
                Text(
                    text = "•  " + ingredient,
                    color = colors.bodyText,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        //only recipes have steps
        if(tip.steps.isNotEmpty()){
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Steps",
                color = colors.screenTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            tip.steps.forEachIndexed{
                index, step -> Text(
                    text = "${index+1}. " + step,
                    color = colors.bodyText,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}


//Previews
//Light and dark versions of each screen
@Preview(showBackground = true, widthDp = 390, heightDp = 700)
@Composable
fun RemindersPreviewLight(){
    Box(modifier = Modifier.fillMaxSize().background(LightStudyColors.background)){
        RemindersScreen(onReminderClick = {})
    }
}

@Preview(
    showBackground = true, widthDp = 390, heightDp = 700,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun RemindersPreviewDark(){
    Box(modifier = Modifier.fillMaxSize().background(DarkStudyColors.background)){
        RemindersScreen(onReminderClick = {})
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 700)

@Composable
fun TipsPreviewLight(){
    Box(modifier = Modifier.fillMaxSize().background(LightStudyColors.background)){
        TipsScreen(onTipClick = {})
    }
}

@Preview(
    showBackground = true, widthDp = 390, heightDp = 700,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun TipsPreviewDark(){
    Box(modifier = Modifier.fillMaxSize().background(DarkStudyColors.background)){
        TipsScreen(onTipClick = {})
    }
}
