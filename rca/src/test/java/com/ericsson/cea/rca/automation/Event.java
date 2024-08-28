/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.automation;

/**
 * @author eeikonl
 * @since 2012
 * 
 */

public class Event {

    private String name;

    private String age;

    private String id;

    private RabCorr rabCorr;

    public Event() {

    }

    public Event(final String imsi, final String imeisv, final String msisdn) {
        this.name = imsi;
        this.age = msisdn;
        this.id = imeisv;

    }

    public String getAge() {
        return age;
    }

    public void setMsisdn(final String tempVal) {
        this.age = tempVal;
    }

    public String getId() {
        return id;
    }

    public void setImeisv(final String id) {
        this.id = id;
    }

    public String getImsi() {
        return name;
    }

    public void setImsi(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Employee Details - ");
        sb.append("Name:" + getImsi());
        sb.append(", ");
        sb.append("Id:" + getId());
        sb.append(", ");
        sb.append("Age:" + getAge());
        sb.append(".");

        return sb.toString();
    }

    /**
     * @return the rabCorr
     */
    public RabCorr getRabCorr() {
        return rabCorr;
    }

    /**
     * @param rabCorr
     *            the rabCorr to set
     */
    public void setRabCorr(final RabCorr rabCorr) {
        this.rabCorr = rabCorr;
    }
}
