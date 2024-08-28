package com.ericsson.cea.rca;

import static com.ericsson.cea.rca.constants.ApplicationTestConstants.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ericsson.cea.rca.stubs.MockedRootCauseAnalsyer;
import com.zhilabs.cc.CustomerCareExplanation;
import com.zhilabs.cc.CustomerCareHandset;
import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.CustomerCareUser;
import com.zhilabs.cc.alarm.CEIAlarm;
import com.zhilabs.cc.cei.CEIType;

public class RootCauseAnalyserUnitTest extends BaseRCATestClass {

    private static MockedRootCauseAnalsyer objUnderTest;

    @BeforeClass
    public static void setup() {
        objUnderTest = new MockedRootCauseAnalsyer(rulesInDRL);
        objUnderTest.setup();
        handset = new CustomerCareHandset("HTC", "Desire", "Android");
        user = new CustomerCareUser("123456789012345", "380561234567", "", handset);
    }

    @Test
    public void testLowFtpThroughput_Congestion() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForCongestion(createAlarmForFileDownloadRate()));

        final String expectedCause = "Congestion";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testLowFtpThroughput_SignalStrength() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForPoorSignalStrength(createAlarmForFileDownloadRate()));

        final String expectedCause = "Poor radio signal strength";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testLowFtpThroughput_HighInterference() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForHighinterference(createAlarmForFileDownloadRate()));

        final String expectedCause = "High interference";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testRulesInConflictExpectHighestRuleToFire() {
        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(createAlarmForFileDownloadRate(),
                LOTS_OF_HIGH_SPEED_USERS, POOR_SIGNAL_STRENGTH, HIGH_INTERFERENCE);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Poor radio signal strength";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testLowWebThroughput_Congestion() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForCongestion(createAlarmLowWebThroughput()));

        final String expectedCause = "Congestion";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testLowWebThroughput_HighInterference() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForHighinterference(createAlarmLowWebThroughput()));

        final String expectedCause = "High interference";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testLowWebThroughput_PoorSignalStrength() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForPoorSignalStrength(createAlarmLowWebThroughput()));

        final String expectedCause = "Poor radio signal strength";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testBadServiceQuality_Congestion() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForCongestion(createAlarmBadServiceQuality()));

        final String expectedCause = "Congestion";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testBadServiceQuality_HighInterference() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForHighinterference(createAlarmBadServiceQuality()));

        final String expectedCause = "High interference";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testBadServiceQuality_PoorSignalStrength() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForPoorSignalStrength(createAlarmBadServiceQuality()));

        final String expectedCause = "Poor radio signal strength";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testHighDrops_HighInterference() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForHighinterference(createAlarmHighDrops()));

        final String expectedCause = "High interference";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testHighDrops_PoorSignalStrength() {
        final CustomerCareExplanation analyse = objUnderTest.analyse(user,
                createTimeLineForPoorSignalStrength(createAlarmHighDrops()));

        final String expectedCause = "Poor radio signal strength";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    //The rules below all have a cause and subcause code in the alarm which identifies the root cause.
    @Test
    public void testAttachRejected_SubscriptionAccessibilityProblem() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICE_NOT_ALLOWED,
                Causes.GPRS_SERVICE_NOT_ALLOWED, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Subscription accessibility problem";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testAttachRejected_NetworkProblem() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN,
                Causes.GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Attach rejected: undefined IMSI or roaming restrictions";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testAttachRejected_NetworkFailure() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.NETWORK_FAILURE, Causes.NETWORK_FAILURE,
                CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Network problem";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testActivateRejected_ConfigurationProblem_UserAuthentication() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.USER_AUTHENTICATION_FAILED,
                Causes.USER_AUTHENTICATION_FAILED, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Configuration problem";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testActivateRejected_ConfigurationProblem_UnknownPDP() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE,
                Causes.UNKNOWN_PDP_ADDRESS_OR_PDP_TYPE, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Configuration problem";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testActivateRejected_ConfigurationProblem_UnknownAP() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.ATTACH_FAILURE_RATIO);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Configuration problem";
        assertEquals(expectedCause, analyse.getExplanation());
    }

    @Test
    public void testExcessivePingPong() {
        final CEIAlarm[] alarms = getCEIAlarms(NO_RESULT, Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME,
                Causes.UNKNOWN_OR_MISSING_ACCESS_POINT_NAME, CEIType.END_TO_END_LATENCY);

        final CustomerCareTimelines ceiTimeLine = getCEITimeLine(alarms);

        final CustomerCareExplanation analyse = objUnderTest.analyse(user, ceiTimeLine);

        final String expectedCause = "Network problem";
        assertEquals(expectedCause, analyse.getExplanation());
    }

}
