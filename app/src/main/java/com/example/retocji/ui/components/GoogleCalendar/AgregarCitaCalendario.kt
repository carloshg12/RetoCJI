package com.example.retocji.ui.components.GoogleCalendar

import android.content.Intent
import android.provider.CalendarContract
import androidx.core.content.ContextCompat

fun agreagarCitaCalendario(
    title: String,
    location: String,
    description: String,
    beginTimeMillis: Long,
    endTimeMillis: Long,
    context: android.content.Context
) {

    val intent: Intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTimeMillis)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeMillis)
        .putExtra(CalendarContract.Events.TITLE, title)
        .putExtra(CalendarContract.Events.DESCRIPTION, description)
        .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)

    ContextCompat.startActivity(context, intent, null)
}