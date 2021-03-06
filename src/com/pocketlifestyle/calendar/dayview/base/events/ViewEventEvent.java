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


package com.pocketlifestyle.calendar.dayview.base.events;

import com.pocketlifestyle.calendar.dayview.base.Event;


public class ViewEventEvent {

    private final Event mEvent;
    private long mSelectedTimeInMillis;

    public ViewEventEvent(Event event, long selectedTimeInMillis) {
        mEvent = event;
        mSelectedTimeInMillis = selectedTimeInMillis;
    }
    
     public Event getEvent() {
        return mEvent;
    }
     
     public long getSelectedTimeInMillis() {
        return mSelectedTimeInMillis;
    }

}
