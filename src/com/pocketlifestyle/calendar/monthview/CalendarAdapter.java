package com.pocketlifestyle.calendar.monthview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pocketlifestyle.calendar.R;
import com.pocketlifestyle.calendar.util.Color.EventColor;

public class CalendarAdapter extends BaseAdapter {

	static final int FIRST_DAY_OF_WEEK = 0;
	Context context;
	Calendar cal;
	public String[] days;
	// OnAddNewEventClick mAddEvent;

	ArrayList<Day> dayList = new ArrayList<Day>();

	public CalendarAdapter(Context context, Calendar cal) {
		this.cal = cal;
		this.context = context;
		cal.set(Calendar.DAY_OF_MONTH, 1);
		refreshDays();
	}

	@Override
	public int getCount() {
		return 42;
	}

	@Override
	public Object getItem(int position) {
		return dayList.get(position);
	}
	
	public int getPosition(Day d) {
		for(int i =0; i < dayList.size(); i++) {
			if(dayList.get(i).getDay() == d.getDay()) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public int getPrevMonth() {
		if (cal.get(Calendar.MONTH) == cal.getActualMinimum(Calendar.MONTH)) {
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR - 1));
		} else {

		}
		int month = cal.get(Calendar.MONTH);
		if (month == 0) {
			return month = 11;
		}

		return month - 1;
	}

	public int getMonth() {
		return cal.get(Calendar.MONTH);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.day_view, null);
		}

		TextView dayTV = (TextView) v.findViewById(R.id.textView1);
		RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl);
		ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
		ImageView blue = (ImageView) v.findViewById(R.id.imageView2);
		ImageView purple = (ImageView) v.findViewById(R.id.imageView3);
		ImageView green = (ImageView) v.findViewById(R.id.imageView4);
		ImageView orange = (ImageView) v.findViewById(R.id.imageView5);
		ImageView red = (ImageView) v.findViewById(R.id.imageView6);

		blue.setVisibility(View.VISIBLE);
		purple.setVisibility(View.VISIBLE);
		green.setVisibility(View.VISIBLE);
		purple.setVisibility(View.VISIBLE);
		orange.setVisibility(View.VISIBLE);
		red.setVisibility(View.VISIBLE);

		iv.setVisibility(View.VISIBLE);
		dayTV.setVisibility(View.VISIBLE);
		rl.setVisibility(View.VISIBLE);

		Day day = dayList.get(position);

		if (day.getNumOfEvenets() > 0 || day.enabled) {
			Set<Integer> colors = day.getColors();

			iv.setVisibility(View.INVISIBLE);
			blue.setVisibility(View.INVISIBLE);
			purple.setVisibility(View.INVISIBLE);
			green.setVisibility(View.INVISIBLE);
			purple.setVisibility(View.INVISIBLE);
			orange.setVisibility(View.INVISIBLE);
			red.setVisibility(View.INVISIBLE);

			if (colors.contains(EventColor.DefaultEventIcon.ordinal())) {
				iv.setVisibility(View.VISIBLE);
			}
			if (colors.contains(EventColor.Blue.ordinal())) {
				blue.setVisibility(View.VISIBLE);
			}
			if (colors.contains(EventColor.Purple.ordinal())) {
				purple.setVisibility(View.VISIBLE);
			}
			if (colors.contains(EventColor.Green.ordinal())) {
				green.setVisibility(View.VISIBLE);
			}
			if (colors.contains(EventColor.Orange.ordinal())) {
				orange.setVisibility(View.VISIBLE);
			}
			if (colors.contains(EventColor.Red.ordinal())) {
				red.setVisibility(View.VISIBLE);
			}

		} else {
			iv.setVisibility(View.INVISIBLE);
			blue.setVisibility(View.INVISIBLE);
			purple.setVisibility(View.INVISIBLE);
			green.setVisibility(View.INVISIBLE);
			purple.setVisibility(View.INVISIBLE);
			orange.setVisibility(View.INVISIBLE);
			red.setVisibility(View.INVISIBLE);
		}

		if (!day.enabled) {
			// rl.setVisibility(View.GONE);
			rl.setEnabled(false);
			rl.setClickable(false);
			dayTV.setText(String.valueOf(day.getDay()));
			dayTV.setTextColor(context.getResources().getColor(R.color.gray_light3));
			dayTV.setTypeface(null, Typeface.NORMAL);
		} else {

			dayTV.setVisibility(View.VISIBLE);
			dayTV.setText(String.valueOf(day.getDay()));
			dayTV.setTypeface(null, Typeface.BOLD);

			Calendar cal = Calendar.getInstance();

			if (day.getMonth() == cal.get(Calendar.MONTH) && day.getDay() == cal.get(Calendar.DAY_OF_MONTH)) {
				// v.setBackgroundResource(R.drawable.today_item);
				dayTV.setTextColor(Color.rgb(33, 181, 229));
				select(this.position == position, v, R.drawable.today_selector);
			} else {
				// v.setBackgroundResource(R.drawable.default_item);
				dayTV.setTextColor(Color.BLACK);
				select(this.position == position, v, R.drawable.default_selector);
			}
		}

		return v;
	}

	public void refreshDays() {
		// clear items
		dayList.clear();

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) cal.get(Calendar.DAY_OF_WEEK);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		TimeZone tz = TimeZone.getDefault();

		// figure size of the array
		if (firstDay == 1) {
			days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
		} else {
			days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
		}

		int j = FIRST_DAY_OF_WEEK;

		// populate empty days before first real day
		if (firstDay > 1) {
			Calendar cTemp = (Calendar) cal.clone();
			onPreviousMonth(cTemp);

			int prevDays = cTemp.getActualMaximum(Calendar.DAY_OF_MONTH) - (firstDay - FIRST_DAY_OF_WEEK) + 2;
			for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
				days[j] = "";

				Day d = new Day(context, prevDays, cTemp.get(Calendar.YEAR), cTemp.get(Calendar.MONTH));
				d.setPrev(true);
				dayList.add(d);
				prevDays++;

			}
		} else {
			for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
				days[j] = "";
				Day d = new Day(context, 33, year, month);
				dayList.add(d);
			}
			j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
		}

		// populate days
		int dayNumber = 1;

		if (j > 0 && dayList.size() > 0) {
			dayList.remove(j - 1);
		}

		for (int i = j - 1; i < days.length; i++) {
			Day d = new Day(context, dayNumber, year, month);
			d.enabled = true;
			Calendar cTemp = Calendar.getInstance();
			cTemp.set(year, month, dayNumber);
			int startDay = Time.getJulianDay(cTemp.getTimeInMillis(),
					TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cTemp.getTimeInMillis())));

			d.setAdapter(this);
			d.setStartDay(startDay);

			days[i] = "" + dayNumber;
			dayNumber++;
			dayList.add(d);
		}
		
		Calendar cTemp = (Calendar) cal.clone();
		onNextMonth(cTemp);
		int nextDays = 1;
		for (int i = dayList.size() - 1; i < 42; i++) {
			Day d = new Day(context, nextDays, cTemp.get(Calendar.YEAR), cTemp.get(Calendar.MONTH));
			d.setNext(true);
			dayList.add(d);
			nextDays++;

		}
	}

	private int position;
	
	public void select(Day day) {
		int pos = getPosition(day);
		select(pos);
	}

	public void select(int pos) {
		position = pos;

		notifyDataSetInvalidated();
	}

	private void select(boolean select, View view, int selectorId) {

		view.setBackgroundResource(selectorId);

		if (select) {
			// if this item is checked - set checked state
			view.getBackground().setState(new int[] { android.R.attr.state_checked });
		} else {
			view.getBackground().setState(new int[] { -android.R.attr.state_checked });
		}
	}

	public boolean isSelected(int position) {
		return this.position == position;
	}

	public final void onNextMonth(Calendar cal) {
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER) {
			cal.set((cal.get(Calendar.YEAR) + 1), Calendar.JANUARY, 1);
		} else {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		}
		
	}

	public final void onPreviousMonth(Calendar cal) {
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
			cal.set((cal.get(Calendar.YEAR) - 1), Calendar.DECEMBER, 1);
		} else {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		}
		
	}

}
