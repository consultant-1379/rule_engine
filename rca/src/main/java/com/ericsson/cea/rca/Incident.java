/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca;

/**
 * @author eeikonl
 * @since 2012
 *
 */

import com.zhilabs.cc.alarm.CEIAlarm;

public class Incident {
    private String name;

    private int cause;

    private int subCause;

    private int result;

    public Incident(final CEIAlarm alarm) {
        this.name = alarm.getType().toString();
        this.cause = alarm.getCause().getCause();
        this.subCause = alarm.getCause().getSubCause();
        this.result = 0;
    }

    public Incident(final String name) {
        this(name, 0, 0, 0);
    }

    public Incident(final String name, final int cause) {
        this(name, cause, 0, 0);
    }

    public Incident(final String name, final int cause, final int subCause) {
        this(name, cause, subCause, 0);
    }

    public Incident(final String name, final int cause, final int subCause, final int result) {
        super();
        this.name = name;
        this.cause = cause;
        this.subCause = subCause;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCause() {
        return cause;
    }

    public void setCause(final int cause) {
        this.cause = cause;
    }

    public int getSubCause() {
        return subCause;
    }

    public void setSubCause(final int subCause) {
        this.subCause = subCause;
    }

    public int getResult() {
        return result;
    }

    public void setResult(final int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Incident [name=" + name + ", cause=" + cause + ", subCause=" + subCause + ", result=" + result + "]";
    }
}
