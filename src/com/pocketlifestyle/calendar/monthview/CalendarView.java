package com.pocketlifestyle.calendar.monthview;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pocketlifestyle.calendar.dayview.DayViewActivity;
import com.pocketlifestyle.calendar.monthview.ExtendedCalendarView.OnDayClickListener;
import com.pocketlifestyle.calendar.util.Color.EventColor;

public class CalendarView extends Fragment implements OnDayClickListener {

	public static final int PAGE_LEFT = 0;
	public static final int PAGE_MIDDLE = 1;
	public static final int PAGE_RIGHT = 2;
	private ExtendedCalendarView calendarLayout;
	private Calendar month;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		month = (Calendar) getArguments().get("calendar");

		calendarLayout = new ExtendedCalendarView(getActivity());
		calendarLayout.init(month);
		calendarLayout.setOnDayClickListener(this);
		
		return calendarLayout;
	}

	public final void onNextMonth() {
		if (month.get(Calendar.MONTH) == Calendar.DECEMBER) {
			month.set((month.get(Calendar.YEAR) + 1), Calendar.JANUARY, 1);
		} else {
			month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
		}

		calendarLayout.setCalendar(month);
	}

	public final void onPreviousMonth() {
		if (month.get(Calendar.MONTH) == Calendar.JANUARY) {
			month.set((month.get(Calendar.YEAR) - 1), Calendar.DECEMBER, 1);
		} else {
			month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
		}
		calendarLayout.setCalendar(month);
	}

	public static CalendarView newInstance(Calendar month) {
		CalendarView f = new CalendarView();
		Bundle temp = new Bundle();
		temp.putSerializable("calendar", month);
		f.setArguments(temp);
		return f;
	}
	
	public CharSequence getTitle() {
		if(calendarLayout == null) {
			return "";
		}
		return calendarLayout.getTitle();
	}

	public CalendarView() {

	}

	@Override
	public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day) {
		

		if(calendarLayout.isSelected(position)) {
			ContentValues values = new ContentValues();
			values.put(CalendarProvider.COLOR, EventColor.Orange.ordinal());
			values.put(CalendarProvider.DESCRIPTION, "Some Description");
			values.put(CalendarProvider.LOCATION, "Some location");
			values.put(CalendarProvider.EVENT, "Event name");

			TimeZone tz = TimeZone.getDefault();
			Calendar cal = Calendar.getInstance();

			cal.set(day.getYear(), day.getMonth(), day.getDay(), 10, 15);

			values.put(CalendarProvider.START, cal.getTimeInMillis());
			values.put(CalendarProvider.START_DAY, day.getStartDay());

			cal.set(day.getYear(), day.getMonth(), day.getDay(), 12, 20);
			int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(),
					TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

			values.put(CalendarProvider.END, cal.getTimeInMillis());
			values.put(CalendarProvider.END_DAY, day.getStartDay());

			getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, values);
			
			GregorianCalendar d = new GregorianCalendar(day.getYear(), day.getMonth(), day.getDay());
			
			Intent i = new Intent();
			i.putExtra("date", d);
			i.setClass(getActivity(), DayViewActivity.class);
			startActivity(i);
		} else {
			calendarLayout.select(position);
		}
		
	}
}