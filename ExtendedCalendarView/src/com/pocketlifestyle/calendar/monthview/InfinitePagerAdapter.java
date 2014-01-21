package com.pocketlifestyle.calendar.monthview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class InfinitePagerAdapter extends FragmentPagerAdapter {

	CalendarView[] fragList;

	public InfinitePagerAdapter(FragmentManager fm, CalendarView[] fragList) {
		super(fm);
		this.fragList = fragList;
	}

	@Override
	public Fragment getItem(int position) {
		return fragList[position];
	}

	@Override
	public int getCount() {
		return fragList.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ((CalendarView) getItem(position)).getTitle();
	}
}