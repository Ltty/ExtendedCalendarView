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

package com.pocketlifestyle.calendar.dayview.base.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

import com.google.common.eventbus.EventBus;
import com.pocketlifestyle.calendar.dayview.base.DayView;
import com.pocketlifestyle.calendar.dayview.base.DayViewDependencyFactory;
import com.pocketlifestyle.calendar.dayview.base.DayViewEventLoader;
import com.pocketlifestyle.calendar.dayview.base.DayViewRenderer;
import com.pocketlifestyle.calendar.dayview.base.DayViewResources;
import com.pocketlifestyle.calendar.dayview.base.DayViewScrollingController;
import com.pocketlifestyle.calendar.dayview.base.EventRenderer;
import com.pocketlifestyle.calendar.dayview.base.EventResource;
import com.pocketlifestyle.calendar.dayview.base.util.CalendarDateUtils;
import com.pocketlifestyle.calendar.dayview.base.util.EventRenderingUtils;
import com.pocketlifestyle.calendar.dayview.base.util.PreferencesUtils;
import com.pocketlifestyle.calendar.dayview.base.util.TimeZoneUtils;

public class DefaultDayViewFactory implements ViewFactory, DayViewDependencyFactory {

    private final ViewSwitcher mViewSwitcher;
    private final Context mContext;
    private final EventResource mEventResource;
    private final DayViewEventLoader mEventLoader;
    private String mPrefsName;
    private DayViewResources mResources;


    public DefaultDayViewFactory(ViewSwitcher vs, EventResource eventResource, Context context, String prefsName) {
        this(context, vs, eventResource, prefsName, new DefaultDayViewResources(context));
        
    }
    
    
    public DefaultDayViewFactory(Context context, ViewSwitcher vs, EventResource eventResource, String prefsName, DayViewResources defaultDayViewResources) {
        mContext = context;
        mViewSwitcher = vs;
        mEventResource = eventResource;
        mPrefsName = prefsName;
        mResources = defaultDayViewResources;
        
        mEventLoader = new DayViewEventLoader(mEventResource);
    }


    @Override
    public View makeView() {
        //using alternate renderer - use alternate dayview resources
        DayView dv = new DayView(mContext, mViewSwitcher, 1, mEventLoader, mResources, this);
        dv.setLayoutParams(new ViewSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        //matching previous behaviour
//        dv.setOnCreateContextMenuListener(new DayViewOnCreateContextMenuListener(mContext, dv, this, mResources, mEventResource));
//        dv.setOnLongClickListener(new DayViewOnLongClickListener(mContext, dv, mResources, this));
//        dv.setOnKeyListener(new DayViewOnKeyListener(mContext, dv));
        return dv;
    }
    
    public Context getContext() {
        return mContext;
    }
    
    public DayViewEventLoader getEventLoader() {
        return mEventLoader;
    }
    
    public EventResource getEventResource() {
        return mEventResource;
    }
    
    public ViewSwitcher getViewSwitcher() {
        return mViewSwitcher;
    }
    
    public DayViewResources getResources() {
        return mResources;
    }


    @Override
    public DayViewRenderer buildDayViewRenderer() {
        return new DefaultDayViewRenderer(mResources);
    }


    @Override
    public EventRenderer buildEventRenderer() {
        return new DefaultEventRenderer(mResources, this);
    }


    @Override
    public DayViewScrollingController buildScrollingController(EventBus eventBus) {
        return new DayViewScrollingController(eventBus);
    }


    public TimeZoneUtils buildTimezoneUtils(){
        return new TimeZoneUtils(mPrefsName);
    }

    public PreferencesUtils buildPreferencesUtils() {
        return new PreferencesUtils(mPrefsName);
    }

    public CalendarDateUtils buildDateUtils() {
        return new CalendarDateUtils(buildPreferencesUtils());
    }

    public EventRenderingUtils buildRenderingUtils() {
        return new EventRenderingUtils();
    }
    
    

}
