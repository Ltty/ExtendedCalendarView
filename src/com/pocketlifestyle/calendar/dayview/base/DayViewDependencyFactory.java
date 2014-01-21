package com.pocketlifestyle.calendar.dayview.base;

import com.google.common.eventbus.EventBus;
import com.pocketlifestyle.calendar.dayview.base.util.CalendarDateUtils;
import com.pocketlifestyle.calendar.dayview.base.util.EventRenderingUtils;
import com.pocketlifestyle.calendar.dayview.base.util.PreferencesUtils;
import com.pocketlifestyle.calendar.dayview.base.util.TimeZoneUtils;

public interface DayViewDependencyFactory {
    
    public DayViewRenderer buildDayViewRenderer();
    
    public EventRenderer buildEventRenderer();
    
    public DayViewScrollingController buildScrollingController(EventBus eventBus);
    
    public TimeZoneUtils buildTimezoneUtils();

    public PreferencesUtils buildPreferencesUtils();

    public CalendarDateUtils buildDateUtils();

    public EventRenderingUtils buildRenderingUtils();


}
