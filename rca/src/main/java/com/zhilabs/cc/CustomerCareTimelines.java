package com.zhilabs.cc;

import java.util.Date;

import com.zhilabs.cc.alarm.CEIAlarm;
import com.zhilabs.cc.cei.CEITimeline;
import com.zhilabs.cc.cei.CEIType;

public interface CustomerCareTimelines {
    public CEIAlarm[] getAlarms();

    public CEITimeline getCEI(CEIType type);

    public Date getEndDate();

    public CEITimeline getLocationCEI(CEIType type, Location loc);

    public CEITimeline getNetworkCEI(CEIType type);

    public Date getStartDate();

    public void setEndDate(String endDate);

    public void setStartDate(String startDate);

    /**
     * @param networkCeiTimeline
     */
    void addNetworkCei(CEITimeline networkCeiTimeline);

    /**
     * @param locCeiTimeline
     */
    void addLocCei(CEITimeline locCeiTimeline);

    /**
     * @param ceiTimeline
     */
    void addCei(CEITimeline ceiTimeline);

    /**
     * @param alarms
     */
    void setAlarms(CEIAlarm[] alarms);
}
