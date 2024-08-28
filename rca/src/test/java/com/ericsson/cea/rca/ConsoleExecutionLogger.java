package com.ericsson.cea.rca;

import org.databene.contiperf.ExecutionLogger;

public class ConsoleExecutionLogger implements ExecutionLogger {

    @Override
    public void logSummary(final String id, final long elapsedTime, final long invocationCount, final long startTime) {
        //        System.out.println(id + ',' + elapsedTime + ',' + invocationCount + ',' + startTime);
        System.out.println("Rate = " + (double) 1000 * invocationCount / elapsedTime + "/s");
        System.out.println();
    }

    @Override
    public void logInvocation(final String id, final int latency, final long startTime) {
        // System.out.println(id + ',' + latency + ',' + 1000000);
    }

}