/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.performancetests;

import static com.ericsson.cea.rca.constants.ApplicationTestConstants.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.ericsson.cea.rca.BaseRCATestClass;
import com.ericsson.cea.rca.Causes;
import com.ericsson.cea.rca.ConsoleExecutionLogger;
import com.ericsson.cea.rca.RootCauseAnalyser;
import com.ericsson.cea.rca.stubs.StubbedCustomerCareTimelines;
import com.zhilabs.cc.CustomerCareExplanation;
import com.zhilabs.cc.CustomerCareHandset;
import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.CustomerCareUser;
import com.zhilabs.cc.alarm.CEIAlarm;
import com.zhilabs.cc.cei.CEIType;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class RootCauseAnalyserPerfTest extends BaseRCATestClass {

    @Rule
    public ContiPerfRule i = new ContiPerfRule(new ConsoleExecutionLogger());

    private static RootCauseAnalyser objUnderTest;

    @BeforeClass
    public static void setup() {
        objUnderTest = new RootCauseAnalyser();
        objUnderTest.setup();
        handset = new CustomerCareHandset("HTC", "Desire", "Android");
        user = new CustomerCareUser("123456789012345", "380561234567", "", handset);
    }

    @Test
    @PerfTest(duration = 5000, threads = 1)
    @Required(average = 50)
    //The average execution time should not exceed 30 ms
    public void testSameCause1Threads() {

        final CustomerCareTimelines timelines = createTimeLineForCongestion(createAlarmForFileDownloadRate());
        final CustomerCareExplanation analyse = objUnderTest.analyse(user, timelines);

        final String expectedCause = "Congestion";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    @PerfTest(duration = 5000, threads = 10)
    @Required(average = 50)
    //The average execution time should not exceed 30 ms
    public void testSameCause10Threads() {

        final CustomerCareTimelines timelines = createTimeLineForCongestion(createAlarmForFileDownloadRate());
        final CustomerCareExplanation analyse = objUnderTest.analyse(user, timelines);

        final String expectedCause = "Congestion";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    @PerfTest(duration = 5000, threads = 100)
    @Required(average = 300)
    //The average execution time should not exceed 30 ms
    public void testSameCause100Threads() {

        final CustomerCareTimelines timelines = createTimeLineForCongestion(createAlarmForFileDownloadRate());
        final CustomerCareExplanation analyse = objUnderTest.analyse(user, timelines);

        final String expectedCause = "Congestion";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    @PerfTest(duration = 5000, threads = 1)
    @Required(average = 50)
    //The average execution time should not exceed 30 ms 
    public void testRandomCause1Threads() {
        final CEIAlarm[] createAlarmForFileDownloadRate = createAlarmForFileDownloadRate();
        final CEIAlarm[] networkFailure = getCEIAlarms(NO_RESULT, Causes.NETWORK_FAILURE, Causes.NETWORK_FAILURE,
                CEIType.ATTACH_FAILURE_RATIO);

        final CEIAlarm[] userAutFailed = getCEIAlarms(NO_RESULT, Causes.USER_AUTHENTICATION_FAILED,
                Causes.USER_AUTHENTICATION_FAILED, CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE,
                CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN,
                Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN, CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICE_NOT_ALLOWED, Causes.GPRS_SERVICE_NOT_ALLOWED,
                CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.END_TO_END_LATENCY);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines[] timeLines = new StubbedCustomerCareTimelines[5];

        timeLines[0] = createTimeLineForCongestion(createAlarmForFileDownloadRate);
        timeLines[1] = createTimeLineForHighinterference(createAlarmForFileDownloadRate);
        timeLines[2] = createTimeLineForPoorSignalStrength(createAlarmForFileDownloadRate);
        timeLines[3] = getCEITimeLine(networkFailure);
        timeLines[4] = getCEITimeLine(userAutFailed);

        final Random rand = new Random(System.currentTimeMillis());
        objUnderTest.analyse(user, timeLines[rand.nextInt(timeLines.length)]);

    }

    @Test
    @PerfTest(duration = 5000, threads = 10)
    @Required(average = 50)
    //The average execution time should not exceed 30 ms 
    public void testRandomCause10Threads() {
        final CEIAlarm[] createAlarmForFileDownloadRate = createAlarmForFileDownloadRate();
        final CEIAlarm[] networkFailure = getCEIAlarms(NO_RESULT, Causes.NETWORK_FAILURE, Causes.NETWORK_FAILURE,
                CEIType.ATTACH_FAILURE_RATIO);

        final CEIAlarm[] userAutFailed = getCEIAlarms(NO_RESULT, Causes.USER_AUTHENTICATION_FAILED,
                Causes.USER_AUTHENTICATION_FAILED, CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE,
                CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN,
                Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN, CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICE_NOT_ALLOWED, Causes.GPRS_SERVICE_NOT_ALLOWED,
                CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.END_TO_END_LATENCY);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines[] timeLines = new StubbedCustomerCareTimelines[5];

        timeLines[0] = createTimeLineForCongestion(createAlarmForFileDownloadRate);
        timeLines[1] = createTimeLineForHighinterference(createAlarmForFileDownloadRate);
        timeLines[2] = createTimeLineForPoorSignalStrength(createAlarmForFileDownloadRate);
        timeLines[3] = getCEITimeLine(networkFailure);
        timeLines[4] = getCEITimeLine(userAutFailed);

        final Random rand = new Random(System.currentTimeMillis());
        objUnderTest.analyse(user, timeLines[rand.nextInt(timeLines.length)]);

    }

    @Test
    @PerfTest(duration = 5000, threads = 100)
    @Required(average = 300)
    //The average execution time should not exceed 30 ms 
    public void testRandomCause100Threads() {
        final CEIAlarm[] createAlarmForFileDownloadRate = createAlarmForFileDownloadRate();
        final CEIAlarm[] networkFailure = getCEIAlarms(NO_RESULT, Causes.NETWORK_FAILURE, Causes.NETWORK_FAILURE,
                CEIType.ATTACH_FAILURE_RATIO);

        final CEIAlarm[] userAutFailed = getCEIAlarms(NO_RESULT, Causes.USER_AUTHENTICATION_FAILED,
                Causes.USER_AUTHENTICATION_FAILED, CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE,
                CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN,
                Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN, CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICE_NOT_ALLOWED, Causes.GPRS_SERVICE_NOT_ALLOWED,
                CEIType.ATTACH_FAILURE_RATIO);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.END_TO_END_LATENCY);

        getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines[] timeLines = new StubbedCustomerCareTimelines[5];

        timeLines[0] = createTimeLineForCongestion(createAlarmForFileDownloadRate);
        timeLines[1] = createTimeLineForHighinterference(createAlarmForFileDownloadRate);
        timeLines[2] = createTimeLineForPoorSignalStrength(createAlarmForFileDownloadRate);
        timeLines[3] = getCEITimeLine(networkFailure);
        timeLines[4] = getCEITimeLine(userAutFailed);

        final Random rand = new Random(System.currentTimeMillis());
        objUnderTest.analyse(user, timeLines[rand.nextInt(timeLines.length)]);

    }

}
