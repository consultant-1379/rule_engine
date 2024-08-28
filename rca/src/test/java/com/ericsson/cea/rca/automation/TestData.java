/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.automation;

import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.CustomerCareUser;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class TestData {

    private CustomerCareUser user;

    private CustomerCareTimelines timeline;

    private String expected;

    private String testName;

    /**
     * @param customerCareUser
     * @param customerCareTimelines
     * @param tempExplanation
     */
    public TestData(final CustomerCareUser customerCareUser, final CustomerCareTimelines customerCareTimelines,
            final String tempExplanation, final String testName) {
        user = customerCareUser;
        timeline = customerCareTimelines;
        expected = tempExplanation;
        this.testName = testName;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(final String expected) {
        this.expected = expected;
    }

    public CustomerCareTimelines getTimeline() {
        return timeline;
    }

    public void setTimeline(final CustomerCareTimelines timeline) {
        this.timeline = timeline;
    }

    public CustomerCareUser getUser() {
        return user;
    }

    public void setUser(final CustomerCareUser user) {
        this.user = user;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
    }
}
