package com.zhilabs.cc;

import java.io.Serializable;

public class Location implements Serializable {

    private int mcc;

    private int mnc;

    private int lac;

    private int sac;

    private int ci;

    public Location(final int mcc, final int mnc, final int lac, final int sac, final int ci) {
        this.mcc = mcc;
        this.mnc = mnc;
        this.lac = lac;
        this.sac = sac;
        this.ci = ci;

    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(final int mcc) {
        this.mcc = mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(final int mnc) {
        this.mnc = mnc;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(final int lac) {
        this.lac = lac;
    }

    public int getSac() {
        return sac;
    }

    public void setSac(final int sac) {
        this.sac = sac;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(final int ci) {
        this.ci = ci;
    }
}
