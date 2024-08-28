/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca;

import com.zhilabs.cc.CustomerCareAnalyser;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class CustomerCarAnalyserFactory {

    private CustomerCareAnalyser analyser = null;

    public CustomerCareAnalyser getCustomerCareAnalyser() {
        if (analyser == null) {
            analyser = new RootCauseAnalyser();
        }
        return analyser;
    }
}
