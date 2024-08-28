package com.zhilabs.cc.alarm;

import java.io.Serializable;

public class Cause implements Serializable {

    private int result;

    private int cause;

    private int subCause;

    public Cause(final int result, final int cause, final int subCause) {
        this.result = result;
        this.cause = cause;
        this.subCause = subCause;

    }

    public int getResult() {
        return result;
    }

    public void setResult(final int result) {
        this.result = result;
    }

    public int getCause() {
        return cause;
    }

    public void setCause(final int cause) {
        this.cause = cause;
    }

    public int getSubCause() {
        return subCause;
    }

    public void setSubCause(final int subCause) {
        this.subCause = subCause;
    }
}
