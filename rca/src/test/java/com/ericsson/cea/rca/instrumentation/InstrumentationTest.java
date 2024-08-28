/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.instrumentation;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class InstrumentationTest {

    private Instrumentation instrumentation;

    @Before
    public void setup() {
        instrumentation = Instrumentation.getInstance();
    }

    @After
    public void tearDown() {
        instrumentation.reset();
    }

    @Test
    /**
     * Simple test should always pass, only one thread, same sleep for every thread etc. Everythign is predictable
     * @throws InterruptedException
     */
    public void test10Invocations() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            instrumentation.logStart("Name" + i);
            Thread.sleep(100);
            instrumentation.logFinish("Name" + i);
        }
        assertEquals("Avg execution time", 100.0, instrumentation.getAvgExecutionTime(), 10);
        assertEquals(10, instrumentation.getExecutionCount());
    }

    /**
     * Start 100 threads and have all call the instrumentation class. Verify that the result is still acceptable
     * 
     * @throws InterruptedException
     */
    @Test
    public void test1000Invocations10Threads() throws InterruptedException {
        final Thread[] threads = new Thread[100];
        new Random(System.currentTimeMillis()).nextInt(1000);

        for (int x = 0; x < threads.length; x++) {
            threads[x] = new Thread() {

                @Override
                public void run() {
                    instrumentation.logStart(this.getName());
                    try {
                        Thread.sleep(100);
                    } catch (final InterruptedException e) {
                        fail();
                    }
                    instrumentation.logFinish(this.getName());
                }
            };
        }

        startThreadsAndWaitTillAllAreFinsihed(threads);

        assertEquals("Avg execution time", 100.0, instrumentation.getAvgExecutionTime(), 10);
        assertEquals(100, instrumentation.getExecutionCount());
    }

    /**
     * @param threads
     * @throws InterruptedException
     */
    private void startThreadsAndWaitTillAllAreFinsihed(final Thread[] threads) throws InterruptedException {
        for (final Thread t : threads) {
            t.start();
        }

        for (final Thread t : threads) {
            t.join();
        }
    }
}
