package com.zhilabs.cc;

public class CustomerCareException extends Exception {

    public CustomerCareException() {
        super();
    }

    public CustomerCareException(final String arg0) {
        super(arg0);
    }

    public CustomerCareException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public CustomerCareException(final Throwable arg0) {
        super(arg0);
    }
}
