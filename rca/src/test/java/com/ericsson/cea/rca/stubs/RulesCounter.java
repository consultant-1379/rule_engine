/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.stubs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eeikonl
 * @since 2012
 *
 */
public class RulesCounter {
    final List<String> rulesInDRL = new ArrayList<String>();

    private int numberOfTestsRun = 0;

    /**
     * @param ruleName
     */
    public void registerRule(final String ruleName) {
        rulesInDRL.add(ruleName);
    }

    public void removeRule(final String ruleName) {
        rulesInDRL.remove(ruleName);
    }

    /**
     * If more than 1 tests were run, we assume that all tests were run
     * @return
     */
    public boolean wereAllRulesFired() {
        if (numberOfTestsRun == 1) {
            return true;
        }
        return rulesInDRL.isEmpty();
    }

    public List<String> getUnfiredRules() {
        return rulesInDRL;
    }

    /**
     * 
     */
    public void logTestInvocation() {

        numberOfTestsRun++;

    }

}
