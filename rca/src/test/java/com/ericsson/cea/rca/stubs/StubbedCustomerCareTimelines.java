/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.stubs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.Location;
import com.zhilabs.cc.alarm.CEIAlarm;
import com.zhilabs.cc.cei.CEITimeline;
import com.zhilabs.cc.cei.CEIType;

public class StubbedCustomerCareTimelines implements CustomerCareTimelines {

    private CEIAlarm[] alarms;

    private Date endDate;

    private Date startDate;

    private final Map<CEIType, CEITimeline> ceiTimelines;

    private final Map<CEIType, CEITimeline> locCeiTimelines;

    private final Map<CEIType, CEITimeline> networkCeiTimelines;

    /**
     * 
     */
    public StubbedCustomerCareTimelines() {
        super();
        ceiTimelines = new HashMap<CEIType, CEITimeline>();
        locCeiTimelines = new HashMap<CEIType, CEITimeline>();
        networkCeiTimelines = new HashMap<CEIType, CEITimeline>();
    }

    @Override
    public CEIAlarm[] getAlarms() {
        return alarms;
    }

    @Override
    public CEITimeline getCEI(final CEIType type) {
        return ceiTimelines.get(type);
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public CEITimeline getLocationCEI(final CEIType type, final Location loc) {
        return locCeiTimelines.get(type);
    }

    @Override
    public CEITimeline getNetworkCEI(final CEIType type) {
        return networkCeiTimelines.get(type);
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setEndDate(final String endDate) {
        final Locale locale = new Locale("en");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", locale);
        try {
            this.endDate = df.parse(endDate);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStartDate(final String startDate) {
        final Locale locale = new Locale("en");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", locale);
        try {
            this.startDate = df.parse(startDate);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAlarms(final CEIAlarm[] alarms) {
        this.alarms = alarms;
    }

    @Override
    public void addLocCei(final CEITimeline cei) {
        this.locCeiTimelines.put(cei.getType(), cei);
    }

    @Override
    public void addCei(final CEITimeline cei) {
        this.ceiTimelines.put(cei.getType(), cei);
    }

    @Override
    public void addNetworkCei(final CEITimeline cei) {
        this.networkCeiTimelines.put(cei.getType(), cei);
    }

}