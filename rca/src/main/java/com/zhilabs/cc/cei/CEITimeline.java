package com.zhilabs.cc.cei;

import java.io.Serializable;
import java.util.Date;

public class CEITimeline implements Serializable {
    private CEIType type;

    private Date startDate;

    private Date endDate;

    private long[] timestamps;

    private double[] values;

    public CEITimeline(final CEIType type, final Date startDate, final Date endDate, final long[] timestamps,
            final double[] values) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timestamps = timestamps;
        this.values = values;

    }

    /**
     * 
     */
    public CEITimeline() {
        // TODO Auto-generated constructor stub
    }

    public CEIType getType() {
        return type;
    }

    public void setType(final CEIType type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public long[] getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(final long[] timestamps) {
        this.timestamps = timestamps;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(final double[] values) {
        this.values = values;
    }

}
