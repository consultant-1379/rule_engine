package com.zhilabs.cc;

public class CustomerCareUser {
    private String imsi;

    private String imeisv;

    private String msisdn;

    private CustomerCareHandset handset;

    public CustomerCareUser(final String imsi, final String msisdn, final String imeisv,
            final CustomerCareHandset handset) {
        this.imsi = imsi;
        this.msisdn = msisdn;
        this.imeisv = imeisv;
        this.handset = handset;

    }

    /**
     * 
     */
    public CustomerCareUser() {
        // TODO Auto-generated constructor stub
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(final String imsi) {
        this.imsi = imsi;
    }

    public String getImeisv() {
        return imeisv;
    }

    public void setImeisv(final String imeisv) {
        this.imeisv = imeisv;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
    }

    public CustomerCareHandset getHandset() {
        return handset;
    }

    public void setHandset(final CustomerCareHandset handset) {
        this.handset = handset;
    }
}
