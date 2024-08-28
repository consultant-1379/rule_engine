package com.zhilabs.cc.alarm;

import java.io.Serializable;
import java.util.Date;

import com.zhilabs.cc.Location;
import com.zhilabs.cc.cei.CEIType;

public class CEIAlarm implements Serializable {

    private CEIType type;

    private Date date;

    private double value;

    private Cause cause;

    private Location location;

    public CEIAlarm() {

    }

    public CEIAlarm(final CEIType type, final Date date, final double value) {
        this.setType(type);
        this.setDate(date);
        this.setValue(value);
        cause = new Cause(0, 1, 1);
    }

    public CEIAlarm(final CEIType type, final Date date, final double value, final Cause cause, final Location loc) {
        this.cause = cause;
        location = loc;
        this.setType(type);
        this.setDate(date);
        this.setValue(value);

    }

    public CEIType getType() {
        return type;
    }

    public void setType(final CEIType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    public void setCause(final Cause cause) {
        this.cause = cause;
    }

    public Cause getCause() {
        return cause;
    }

    public Location getloc() {
        return location;
    }

}
