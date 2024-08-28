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

public class Explanation {
    private String details;

    public Explanation() {

    }

    public Explanation(final String details) {
        super();
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }
}
