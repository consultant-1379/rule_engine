/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca;

import static com.ericsson.cea.rca.constants.ApplicationTestConstants.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;

import com.ericsson.cea.rca.stubs.RulesCounter;
import com.ericsson.cea.rca.stubs.StubbedCustomerCareTimelines;
import com.zhilabs.cc.CustomerCareHandset;
import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.CustomerCareUser;
import com.zhilabs.cc.Location;
import com.zhilabs.cc.alarm.CEIAlarm;
import com.zhilabs.cc.alarm.Cause;
import com.zhilabs.cc.cei.CEITimeline;
import com.zhilabs.cc.cei.CEIType;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class BaseRCATestClass {

    protected static CustomerCareHandset handset;

    protected static CustomerCareUser user;

    protected static RulesCounter rulesInDRL = new RulesCounter();

    @AfterClass
    public static void verify() {

        //Probably breaking some JUnit guidelines, but this is here to catch a situation where someone adds a rule and doesn't write a test for it
        if (!allRulesFired()) {
            final StringBuilder sb = new StringBuilder();

            for (final String ruleName : rulesInDRL.getUnfiredRules()) {
                sb.append(ruleName + "\n");
            }
            fail("Not all rules are covered\nUncovered Rules:\n" + sb.toString());

        }
    }

    /**
     * @return
     */
    private static boolean allRulesFired() {
        return rulesInDRL.wereAllRulesFired();
    }

    @Before
    public void beforetest() {
        rulesInDRL.logTestInvocation();
    }

    /**
     * Create the CusomterCareTimelines for the given alarms, with the given values
     * 
     * @param alarms The CEIAlarms to be inserted into the timeline
     * @param highSpeedUsers number of highspeed users
     * @param signalStrength signal strength in db
     * @param interference interference in db
     * @return a CustomerCareTimelines containing CEITimelines corresponding to the values supplied
     */
    protected CustomerCareTimelines getCEITimeLine(final CEIAlarm[] alarms, final double highSpeedUsers,
            final double signalStrength, final double interference) {
        final double[] n_hsusers = new double[1];
        n_hsusers[0] = highSpeedUsers;

        final double[] rscp = new double[1];
        rscp[0] = signalStrength;

        final double[] ecn0 = new double[1];
        ecn0[0] = interference;

        final double[] n_meas = new double[1];
        n_meas[0] = 4.0;

        final CEITimeline signal = new CEITimeline(CEIType.rscp, null, null, null, rscp);
        final CEITimeline users = new CEITimeline(CEIType.n_hsusers, null, null, null, n_hsusers);
        final CEITimeline enc0 = new CEITimeline(CEIType.ecn0, null, null, null, ecn0);
        final CEITimeline measurements = new CEITimeline(CEIType.n_meas, null, null, null, n_meas);
        final CustomerCareTimelines timelines = new StubbedCustomerCareTimelines();
        timelines.addCei(measurements);
        timelines.addCei(enc0);
        timelines.addCei(signal);
        timelines.addCei(users);

        timelines.setAlarms(alarms);
        return timelines;
    }

    /**
     * Create the CusomterCareTimelines for the given alarms, with the given values
     * 
     * @param alarms
     * @return a default CustomerCareTimelines containingonly alarms, and zero values for the CEITimelines
     */
    protected CustomerCareTimelines getCEITimeLine(final CEIAlarm[] alarms) {
        return getCEITimeLine(alarms, 0.0, 0.0, 0.0);
    }

    /**
     * Create the CEIAlarams for the given result, causeCode and subCauseCode. The date it hardcoded to be now. May need
     * additional functionality to calculate the dates etc. Will know more when the sdk is delivered
     * 
     * @return a CEIAlarm with the given result, causes and CEIType
     */
    protected CEIAlarm[] getCEIAlarms(final int result, final int causeCode, final int subCauseCode,
            final CEIType ceiType) {
        final CEIAlarm[] alarms = new CEIAlarm[1];
        final Date date = new Date(System.currentTimeMillis());
        final double value = 3.0;

        final Cause cause = new Cause(0, causeCode, subCauseCode);
        final Location location = new Location(0, 0, 0, 0, 0);
        alarms[0] = new CEIAlarm(ceiType, date, value, cause, location);
        return alarms;
    }

    /**
     * Create the CEIAlarams for the given result, causeCode and subCauseCode. The date it hardcoded to be now. May need
     * additional functionality to calculate the dates etc. Will know more when the sdk is delivered
     * 
     * @return a default CEIAlarm with the given result, and CEIType and 0 values for the causes
     */
    protected CEIAlarm[] getCEIAlarms(final int result, final CEIType ceiType) {
        final CEIAlarm[] alarms = new CEIAlarm[1];
        final Date date = new Date(System.currentTimeMillis());
        final double value = 3.0;

        final Cause cause = new Cause(0, 0, 0);
        final Location location = new Location(0, 0, 0, 0, 0);
        alarms[0] = new CEIAlarm(ceiType, date, value, cause, location);
        return alarms;
    }

    /**
     * @param alarms The alarms to include the in the CustomerCareTimeline
     * @return a CustomerCareTimeline that indicates congestion
     */
    protected CustomerCareTimelines createTimeLineForCongestion(final CEIAlarm[] alarms) {

        final CustomerCareTimelines timelines = getCEITimeLine(alarms, LOTS_OF_HIGH_SPEED_USERS, GOOD_SIGNAL_STRENGTH,
                LOW_INTERFERENCE);
        return timelines;
    }

    /**
     * @param alarms The alarms to include the in the CustomerCareTimeline
     * @return a CustomerCareTimeline that indicates Poor Signal Strength
     */
    protected CustomerCareTimelines createTimeLineForPoorSignalStrength(final CEIAlarm[] alarms) {
        final CustomerCareTimelines timelines = getCEITimeLine(alarms, FEW_HIGH_SPEED_USERS, POOR_SIGNAL_STRENGTH,
                LOW_INTERFERENCE);
        return timelines;
    }

    /**
     * @param alarms The alarms to include the in the CustomerCareTimeline
     * @return a CustomerCareTimeline that indicates HighInterference
     */
    protected CustomerCareTimelines createTimeLineForHighinterference(final CEIAlarm[] alarms) {
        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms, FEW_HIGH_SPEED_USERS, GOOD_SIGNAL_STRENGTH,
                HIGH_INTERFERENCE);
        return ceiTimeLine;
    }

    /**
     * @param alarms The alarms to include the in the CustomerCareTimeline
     * @return
     */
    protected CEIAlarm[] createAlarmForFileDownloadRate() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, CEIType.FILE_DOWNLOAD_EFFECTIVE_DATA_RATE);
        return alarms;
    }

    protected CEIAlarm[] createAlarmLowWebThroughput() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, CEIType.HTTP_SERVICE_NON_ACCESIBILITY);
        return alarms;
    }

    protected CEIAlarm[] createAlarmHighDrops() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, CEIType.INTERNAL_SYSTEM_BLOCK);
        return alarms;
    }

    protected CEIAlarm[] createAlarmBadServiceQuality() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, CEIType.STREAMING_REBUFFERING_FAILURE_RATIO);
        return alarms;
    }
}