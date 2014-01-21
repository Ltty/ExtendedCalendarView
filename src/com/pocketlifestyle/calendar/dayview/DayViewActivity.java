/*
Copyright 2013 Chris Pope

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
 */

package com.pocketlifestyle.calendar.dayview;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.pocketlifestyle.calendar.R;
import com.pocketlifestyle.calendar.dayview.base.DayView;
import com.pocketlifestyle.calendar.dayview.base.Event;
import com.pocketlifestyle.calendar.dayview.base.EventResource;
import com.pocketlifestyle.calendar.dayview.base.events.EventSelectionListener;

public class DayViewActivity extends Activity implements EventSelectionListener {

	private static final String TAG = "MainActivity";
	public static final int EVENT_DETAIL_REQUEST = 0;
	private TextView mDateView = null;
	private EventResource mEventResource;
	private Event mLastEvent = null;
	// private YadviewHarnessDayViewFactory mViewFactory;
	private YadviewHarnessDayViewFactory mViewFactory;
	private Date mDate = null;

	public static final String HISTORY_DETAIL_DATE_KEY = "date";
	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

	public DayViewActivity() {
		// mEventResource = new DefaultEventResource(this, new
		// DefaultUtilFactory("yadview_harness.prefs"));

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dayview);
		mEventResource = new BasicEventResource(getApplicationContext());
		GregorianCalendar date = new GregorianCalendar();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			date = (GregorianCalendar) extras.getSerializable(HISTORY_DETAIL_DATE_KEY);
		}
		TextView dateView = (TextView) findViewById(R.id.dayViewActivityDateLabel);
		Time day = new Time();
		day.set(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));

		dateView.setText(DateFormat.getDateFormat(getApplicationContext()).format(date.getTime()));

		ViewSwitcher vs = (ViewSwitcher) findViewById(R.id.view_switcher);
		// // mViewFactory = new YadviewHarnessDayViewFactory(vs,
		// mEventResource, this);
		mViewFactory = new YadviewHarnessDayViewFactory(vs, mEventResource, this);
		vs.setFactory(mViewFactory);
		DayView dv = (DayView) vs.getCurrentView();
		dv.setEventSelectionListener(this);
		dv.setSelected(day, false, true);
		dv.clearCachedEvents();
		dv.reloadEvents();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewFactory.getEventLoader().startBackgroundThread();

	}

	@Override
	protected void onPause() {
		Log.e("Peter", "onPause");
		super.onPause();
		mViewFactory.getEventLoader().stopBackgroundThread();
	}

	@Override
	public void notifyEventSelected(Event _e) {
		if (null != _e && !_e.equals(mLastEvent))// event selection is done
													// twice ==> only show event
													// details once
		{
			// Intent i = new Intent();
			// i.setClass(this, HistoryDetailActivity.class);
			// GregorianCalendar cal = new GregorianCalendar();
			// cal.setTimeInMillis(_e.getStartMillis());
			// i.putExtra("date", cal);
			// startActivityForResult(i, EVENT_DETAIL_REQUEST);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mLastEvent = null;// reset last event after event details were shown.
		Log.d("Peter", "received result...");
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}
