/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.stubs;

import java.util.Collection;

import org.drools.definition.KnowledgePackage;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.runtime.StatelessKnowledgeSession;

import com.ericsson.cea.rca.Explanation;
import com.ericsson.cea.rca.RootCauseAnalyser;

public class MockedRootCauseAnalsyer extends RootCauseAnalyser {

    private final RulesCounter ruleCounter;

    public MockedRootCauseAnalsyer(final RulesCounter counter) {
        super();
        this.ruleCounter = counter;

    }

    /**
     * Method overridden from objUnderTest to allow us to insert an EventListener to see that all rules are fired. 
     */
    @Override
    protected StatelessKnowledgeSession getKnowledgeSession(final Explanation explanation) {

        final StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

        ksession.setGlobal("explanation", explanation);

        ksession.addEventListener(new DefaultAgendaEventListener() {

            @Override
            public void afterActivationFired(final AfterActivationFiredEvent event) {
                ruleCounter.removeRule(event.getActivation().getRule().getName());
            }
        });
        return ksession;
    }

    @Override
    public void setup() {
        super.setup();
        //Get all the rules from the KnowledgeBase and store for comparison later
        findAndStoreAllRules();
    }

    /**
     * 
     */
    private void findAndStoreAllRules() {
        final Collection<KnowledgePackage> knowledgePackages = kbase.getKnowledgePackages();
        for (final KnowledgePackage kp : knowledgePackages) {
            final Collection<org.drools.definition.rule.Rule> rules = kp.getRules();
            for (final org.drools.definition.rule.Rule rule : rules) {
                ruleCounter.registerRule(rule.getName());
            }
        }
    }
}