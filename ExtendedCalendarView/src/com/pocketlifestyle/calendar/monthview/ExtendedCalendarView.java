package com.pocketlifestyle.calendar.monthview;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.tyczj.extendedcalendarview.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExtendedCalendarView extends RelativeLayout implements OnItemClickListener, OnCreateContextMenuListener {

	private Context context;
	private OnDayClickListener dayListener;
	private OnDayContextMenuListener contextListener;
	private GridView calendar;
	private CalendarAdapter mAdapter;
	private Calendar cal;
	private TextView month;
	private RelativeLayout base;
	private ImageView next, prev;
	private SimpleDateFormat format;

	public interface OnDayClickListener {
		public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day);
	}

	public interface OnDayContextMenuListener {
		public void onDayContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo);
	}

	public ExtendedCalendarView(Context context) {
		super(context);
		this.context = context;

	}

	public ExtendedCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public ExtendedCalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public void setCalendar(Calendar cal) {
		this.cal = cal;
		refreshCalendar();
	}
	
	public CharSequence getTitle() {
		return format.format(cal.getTime());
	}
	
	public void init(Calendar startDate) {
		this.cal = startDate;
		format = new SimpleDateFormat("MM/yyyy");


		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		GridView days = new GridView(context);
		days.setId(12);
		days.setLayoutParams(params);
		days.setVerticalSpacing(4);
		days.setHorizontalSpacing(4);
		days.setNumColumns(7);
		days.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		days.setPadding(0, 10, 0, 10);
		days.setGravity(Gravity.CENTER);
		days.setAdapter(new DayAdapter());
		days.setBackgroundColor(Color.LTGRAY);
		days.setEnabled(false);

		addView(days);

		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// params.topMargin = 20;
		params.bottomMargin = 20;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.BELOW, days.getId());

		calendar = new GridView(context);
		calendar.setLayoutParams(params);
		calendar.setVerticalSpacing(4);
		calendar.setHorizontalSpacing(4);
		calendar.setNumColumns(7);
		calendar.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		calendar.setDrawSelectorOnTop(false);

		mAdapter = new CalendarAdapter(context, cal);
		calendar.setAdapter(mAdapter);

		addView(calendar);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (dayListener != null) {
			Day d = (Day) mAdapter.getItem(arg2);
			dayListener.onDayClicked(arg0, arg1, arg2, arg3, d);
		}
	}

	/**
	 * 
	 * @param listener
	 * 
	 *            Set a listener for when you press on a day in the month
	 */
	public void setOnDayClickListener(OnDayClickListener listener) {
		if (calendar != null) {
			dayListener = listener;
			calendar.setOnItemClickListener(this);
		}
	}

	/**
	 * 
	 * @param contextListener
	 * 
	 *            Set a context menu for days of the month
	 */
	public void setOnCreateContextMenuListener(OnDayContextMenuListener contextListener) {
		if (calendar != null) {
			this.contextListener = contextListener;
			calendar.setOnCreateContextMenuListener(this);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (contextListener != null) {
			contextListener.onDayContextMenu(menu, v, menuInfo);
		}
	}


	/**
	 * Refreshes the month
	 */
	public void refreshCalendar() {
		mAdapter.refreshDays();
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * @param color
	 * 
	 *            Sets the background color of the month bar
	 */
	public void setMonthTextBackgroundColor(int color) {
		base.setBackgroundColor(color);
	}
	
	public void select(int position) {
		mAdapter.select(position);
		
	}

	@SuppressLint("NewApi")
	/**
	 * 
	 * @param drawable
	 * 
	 * Sets the background color of the month bar. Requires at least API level 16
	 */
	public void setMonthTextBackgroundDrawable(Drawable drawable) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
			base.setBackground(drawable);
		}

	}

	/**
	 * 
	 * @param resource
	 * 
	 *            Sets the background color of the month bar
	 */
	public void setMonehtTextBackgroundResource(int resource) {
		base.setBackgroundResource(resource);
	}

	/**
	 * 
	 * @param recource
	 * 
	 *            change the image of the previous month button
	 */
	public void setPreviousMonthButtonImageResource(int recource) {
		prev.setImageResource(recource);
	}

	/**
	 * 
	 * @param bitmap
	 * 
	 *            change the image of the previous month button
	 */
	public void setPreviousMonthButtonImageBitmap(Bitmap bitmap) {
		prev.setImageBitmap(bitmap);
	}

	/**
	 * 
	 * @param drawable
	 * 
	 *            change the image of the previous month button
	 */
	public void setPreviousMonthButtonImageDrawable(Drawable drawable) {
		prev.setImageDrawable(drawable);
	}

	/**
	 * 
	 * @param recource
	 * 
	 *            change the image of the next month button
	 */
	public void setNextMonthButtonImageResource(int recource) {
		next.setImageResource(recource);
	}

	/**
	 * 
	 * @param bitmap
	 * 
	 *            change the image of the next month button
	 */
	public void setNextMonthButtonImageBitmap(Bitmap bitmap) {
		next.setImageBitmap(bitmap);
	}

	/**
	 * 
	 * @param drawable
	 * 
	 *            change the image of the next month button
	 */
	public void setNextMonthButtonImageDrawable(Drawable drawable) {
		next.setImageDrawable(drawable);
	}

	private class DayAdapter extends BaseAdapter {

		private final int[] IDS = new int[] { R.string.sunday, R.string.monday, R.string.tuesday, R.string.wednesday,
				R.string.thursday, R.string.friday, R.string.saturday };

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(getContext());
				((TextView)convertView).setTypeface(null, Typeface.BOLD);
				 ((TextView)convertView).setTextColor(Color.DKGRAY);
				 ((TextView)convertView).setGravity(Gravity.CENTER);
			}

			((TextView) convertView).setText(IDS[position]);

			return convertView;
		}

	}

	public boolean isSelected(int position) {
		return mAdapter.isSelected(position);
	}

}
