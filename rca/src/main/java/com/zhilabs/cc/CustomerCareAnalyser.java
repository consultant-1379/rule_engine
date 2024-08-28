package com.zhilabs.cc;

public interface CustomerCareAnalyser {

    public CustomerCareExplanation analyse(CustomerCareUser user, CustomerCareTimelines timelines);

    public void setup();
}
