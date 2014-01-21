package com.pocketlifestyle.calendar.dayview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.google.code.yadview.Event;
import com.google.code.yadview.EventResource;
import com.google.code.yadview.Predicate;
import com.pocketlifestyle.calendar.monthview.CalendarProvider;
import com.pocketlifestyle.calendar.util.Color;
import com.pocketlifestyle.calendar.util.Color.EventColor;

public class BasicEventResource implements EventResource {

	private Context context;

	public BasicEventResource(Context context) {
		this.context = context;
	}

	@Override
	public List<Event> get(int startJulianDay, int numDays, Predicate continueLoading) {
		final String QUERY = "? >= " + CalendarProvider.START_DAY + " AND " + CalendarProvider.END_DAY + " <= ?";
		Cursor c = context.getContentResolver().query(CalendarProvider.CONTENT_URI, null, QUERY,
				new String[] { String.valueOf(startJulianDay+1), String.valueOf(startJulianDay+1 + numDays) },
				CalendarProvider.START + " ASC");

		List<Event> events = new ArrayList<Event>();

		if (c != null && c.moveToFirst()) {
			try {
				do {
					Event event = new Event();
					event.setAllDay(false);
					event.setId(c.getLong(0));
					event.setColor(Color.getColor(EventColor.values()[c.getInt(1)]));
					event.setLocation(c.getString(2));
					event.setTitle(c.getString(5));
					
					Date startTime = new Date(c.getLong(3));
					event.setStartMillis(c.getLong(3));
					event.setStartTime(startTime.getHours() * 60 + startTime.getMinutes());
					event.setStartDay(c.getInt(7));
					
					Date endTime = new Date(c.getLong(8));
					event.setEndMillis(c.getLong(8));
					event.setEndTime(endTime.getHours() * 60 + endTime.getMinutes());
					event.setEndDay(c.getInt(6));

					events.add(event);
				} while (c.moveToNext());
			} finally {
				c.close();
			}
		}

		return events;
	}

	@Override
	public int getEventAccessLevel(Event e) {
		return ACCESS_LEVEL_NONE;
	}

}
