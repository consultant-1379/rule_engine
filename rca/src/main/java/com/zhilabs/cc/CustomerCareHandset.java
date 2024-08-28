package com.zhilabs.cc;

public class CustomerCareHandset {

    private String vendor;

    private String model;

    private String os;

    public CustomerCareHandset() {
    }

    public CustomerCareHandset(final String vendor, final String model, final String os) {
        this.vendor = vendor;
        this.model = model;
        this.os = os;

    }

    public String getOs() {
        return os;
    }

    public void setOs(final String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(final String vendor) {
        this.vendor = vendor;
    }

}
