package com.zhilabs.cc;

import java.io.Serializable;

public class CustomerCareExplanation implements Serializable {

    private CustomerCareUser user;

    private String explanation;

    public CustomerCareExplanation() {
        //Do something
    }

    public CustomerCareExplanation(final CustomerCareUser user, final String explanation) {
        this.user = user;
        this.explanation = explanation;

    }

    public CustomerCareUser getUser() {
        return user;
    }

    public void setUser(final CustomerCareUser user) {
        this.user = user;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(final String explanation) {
        this.explanation = explanation;
    }
}
