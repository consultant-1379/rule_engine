/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.instrumentation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class Instrumentation {

    private static Instrumentation instance = null;

    private long cummulativeExecutionTime = 0;

    private long maxExecutionTime = 0;

    private long minExecutionTime = 0;

    private String maxExectionRule;

    private String minExectionRule;

    private int executionCount = 0;

    private static final int executionsBeforeLogging = 1000;

    private final Map<String, Long> threadToTimeMap;

    public static Instrumentation getInstance() {
        if (instance == null) {
            instance = new Instrumentation();
        }
        return instance;
    }

    private Instrumentation() {
        threadToTimeMap = new HashMap<String, Long>();
    }

    public synchronized void logStart(final String threadName) {
        threadToTimeMap.put(threadName, System.currentTimeMillis());
    }

    public synchronized void logFinish(final String threadName) {
        if (threadToTimeMap.containsKey(threadName)) {
            executionCount++;

            final long threadExecutionTime = System.currentTimeMillis() - threadToTimeMap.get(threadName);
            updateMinOrMax(threadName, threadExecutionTime);
            cummulativeExecutionTime = cummulativeExecutionTime + threadExecutionTime;

            threadToTimeMap.remove(threadName);
            if (executionCount == executionsBeforeLogging) {
                logExecutions();
            }
        }
    }

    /**
     * @param threadName
     * @param threadExecutionTime
     */
    private void updateMinOrMax(final String threadName, final long threadExecutionTime) {
        if (threadExecutionTime > maxExecutionTime) {
            maxExecutionTime = threadExecutionTime;
            maxExectionRule = threadName;
        } else if (threadExecutionTime < minExecutionTime) {
            minExecutionTime = threadExecutionTime;
            minExectionRule = threadName;
        }

    }

    /**
     * 
     */
    private void logExecutions() {
        System.out.println(this.toString());
    }

    /**
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return "Number of invocations -> " + getExecutionCount() + "\nAvg execution time -> " + getAvgExecutionTime()
                + "\nTotal execution time -> " + getCummulativeExecutionTime() + "\nMinimum Execution Time (Rule: "
                + minExectionRule + ") -> " + minExecutionTime + "\nMaximum Execution Time (Rule: " + maxExectionRule
                + ") -> " + maxExecutionTime;
    }

    public double getAvgExecutionTime() {
        return cummulativeExecutionTime / executionCount;
    }

    public long getCummulativeExecutionTime() {
        return cummulativeExecutionTime;
    }

    public int getExecutionCount() {
        return executionCount;
    }

    public void reset() {
        instance = new Instrumentation();
    }
}
