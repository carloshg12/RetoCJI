package com.example.retocji.ui.components.GoogleCalendar

import android.app.Application
import android.content.Intent
import android.provider.CalendarContract
import java.util.Calendar
import java.util.Date


fun launchGoogleCalendarToAddEvent(
    title: String,
    location: String,
    description: String,
    beginTimeMillis: Long,
    endTimeMillis: Long,
    context: Application
) {
    val beginTime = Calendar.getInstance().apply { time = Date(beginTimeMillis) }
    val endTime = Calendar.getInstance().apply { time = Date(endTimeMillis) }

    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        putExtra(CalendarContract.Events.DESCRIPTION, description)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}



