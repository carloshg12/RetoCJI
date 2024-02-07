package com.example.retocji.ui.components.GoogleCalendar

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import android.content.Intent
import android.provider.CalendarContract
import java.util.*



fun addCalendarEvent(account: GoogleSignInAccount,application: Application) {
    val credential = GoogleAccountCredential.usingOAuth2(application, listOf(CalendarScopes.CALENDAR))
    credential.selectedAccount = account.account
    val service = Calendar.Builder(
        AndroidHttp.newCompatibleTransport(),
        JacksonFactory.getDefaultInstance(), credential
    )
        .setApplicationName("My Application")
        .build()

    val event = Event()
        .setSummary("Reserva")
        .setDescription("Descripción de la reserva")

    val startDateTime = DateTime("2023-05-28T09:00:00-07:00")
    val start = EventDateTime()
        .setDateTime(startDateTime)
        .setTimeZone("America/Los_Angeles")
    event.start = start

    val endDateTime = DateTime("2023-05-28T17:00:00-07:00")
    val end = EventDateTime()
        .setDateTime(endDateTime)
        .setTimeZone("America/Los_Angeles")
    event.end = end

    val calendarId = "primary"
    service.events().insert(calendarId, event).execute()
}


fun launchGoogleCalendarToAddEvent(title: String, location: String, description: String, beginTime: Long, endTime: Long, context: Application) {
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        putExtra(CalendarContract.Events.DESCRIPTION, description)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}


