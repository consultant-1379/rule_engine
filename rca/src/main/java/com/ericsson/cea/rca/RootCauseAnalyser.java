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

import java.util.ArrayList;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

import com.zhilabs.cc.CustomerCareAnalyser;
import com.zhilabs.cc.CustomerCareExplanation;
import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.CustomerCareUser;
import com.zhilabs.cc.alarm.CEIAlarm;

public class RootCauseAnalyser implements CustomerCareAnalyser {

    private KnowledgeBuilder kbuilder;

    protected KnowledgeBase kbase;

    /* (non-Javadoc)
     * @see com.zhilabs.cc.CustomerCareAnalyser#analyse(com.zhilabs.cc.CustomerCareUser, com.zhilabs.cc.CustomerCareTimelines)
     */
    @Override
    public CustomerCareExplanation analyse(final CustomerCareUser user, final CustomerCareTimelines timelines) {

        //Initialise the facts
        final ArrayList<Object> sessionObjects = new ArrayList<Object>();
        final Explanation explanation = new Explanation();
        for (final CEIAlarm alarm : timelines.getAlarms()) {
            final Incident incident = new Incident(alarm);
            sessionObjects.add(incident);

        }
        //Initialise metrics objects
        final RadioMetrics metrics = new RadioMetrics(timelines);
        sessionObjects.add(metrics);

        //Get the StatelessKnowledgeSession
        final StatelessKnowledgeSession ksession = getKnowledgeSession(explanation);

        //Evaluate the facts
        ksession.execute(sessionObjects);

        //Return Explanation
        return new CustomerCareExplanation(user, explanation.getDetails());
    }

    /**
     * @param explanation
     *            The Explanation object to be inserted into the KnowledgeSession
     * @return a new Stateless Knowledge Session
     */
    protected StatelessKnowledgeSession getKnowledgeSession(final Explanation explanation) {
        final StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
        ksession.setGlobal("explanation", explanation);
        return ksession;
    }

    /**
     * Create the rules engine and intialise the session
     */
    @Override
    public void setup() {

        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("SimpleRcaRules.drl", RootCauseAnalyser.class),
                ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            System.err.println(kbuilder.getErrors().toString());
        }

        kbase = KnowledgeBaseFactory.newKnowledgeBase();

        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    }

}
