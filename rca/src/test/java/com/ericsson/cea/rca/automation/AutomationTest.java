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

import java.text.ParseException;
import java.util.ArrayList;

import com.ericsson.cea.rca.RootCauseAnalyser;
import com.zhilabs.cc.CustomerCareExplanation;

public class AutomationTest {

    private static AutomatedTestParser automatedTestParser;

    //These incidents are what are passed to analyse
    private static ArrayList<TestData> incidentMap;

    private void setup() {
        automatedTestParser = new AutomatedTestParser();
        automatedTestParser.parseDocument();
        incidentMap = automatedTestParser.getIncidentMap();
    }

    public static void main(final String[] args) throws ParseException {
        final AutomationTest tester = new AutomationTest();
        tester.start();
    }

    private void start() {
        setup();
        final RootCauseAnalyser analyser = new RootCauseAnalyser();
        analyser.setup();
        for (final TestData data : incidentMap) {
            final CustomerCareExplanation analyse = analyser.analyse(data.getUser(), data.getTimeline());

            String passOrFail = "Failed";
            if (data.getExpected().equals(analyse.getExplanation())) {
                passOrFail = "Passed";
            }
            System.out.println("Test " + data.getTestName() + " =>> " + passOrFail);
        }

    }
}