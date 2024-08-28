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

import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.cei.CEITimeline;
import com.zhilabs.cc.cei.CEIType;

public class RadioMetrics {

    private final CustomerCareTimelines timelines;

    public RadioMetrics(final CustomerCareTimelines timelines) {
        this.timelines = timelines;
    }

    public double getSignalStrength() {
        final CEITimeline networkCEI = timelines.getCEI(CEIType.rscp);
        final double d = networkCEI.getValues()[0];
        return d;
    }

    public double getInterference() {
        final CEITimeline networkCEI = timelines.getCEI(CEIType.ecn0);
        final double d = networkCEI.getValues()[0];
        return d;
    }

    public int getHighspeedUsers() {
        final CEITimeline networkCEI = timelines.getCEI(CEIType.n_hsusers);
        final double d = networkCEI.getValues()[0];
        return (int) d;
    }

    public int getNumMeasurements() {
        return 4;
    }

    public String getRAT() {
        return "WCDMA";
    }
}
