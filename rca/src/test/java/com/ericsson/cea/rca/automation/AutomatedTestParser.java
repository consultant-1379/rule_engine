/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.cea.rca.automation;

import static com.ericsson.cea.rca.automation.tags.XmlTagDefinitions.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ericsson.cea.rca.stubs.StubbedCustomerCareTimelines;
import com.zhilabs.cc.CustomerCareHandset;
import com.zhilabs.cc.CustomerCareTimelines;
import com.zhilabs.cc.CustomerCareUser;
import com.zhilabs.cc.alarm.CEIAlarm;
import com.zhilabs.cc.alarm.Cause;
import com.zhilabs.cc.cei.CEITimeline;
import com.zhilabs.cc.cei.CEIType;

/**
 * @author eeikonl
 * @since 2012
 * 
 */
public class AutomatedTestParser extends DefaultHandler {

    public static final String INCIDENT_TAG = "Incident";

    public static final String TIMELINE_TAG = "timeline";

    private final Map<String, CustomerCareUser> eventMap;

    private final Map<String, CustomerCareHandset> handsetMap;

    private String tempVal;

    //to maintain context
    private CustomerCareUser tempEvent;

    private String tempEventId;

    private CustomerCareHandset tempHandset;

    private String tempHandsetId;

    private String tempUserId;

    private String tempTimelineId;

    private final Map<String, CustomerCareTimelines> timelineMap;

    private CustomerCareTimelines tempTimeline;

    private CEIAlarm tempAlarm;

    private String tempAlarmId;

    private final HashMap<String, CEIAlarm> alarmMap;

    private final Map<String, Cause> causeMap;

    private final Map<String, CEITimeline> timeLineMap;

    private final ArrayList<TestData> incidents;

    private String tempExplanation;

    private String tempTestname;

    /**
     * @param eventMap
     * @param tempVal
     * @param tempEvent
     * @param tempEventId
     * @param tempHandset
     */
    public AutomatedTestParser() {
        eventMap = new HashMap<String, CustomerCareUser>();
        handsetMap = new HashMap<String, CustomerCareHandset>();
        incidents = new ArrayList<TestData>();
        timelineMap = new HashMap<String, CustomerCareTimelines>();
        alarmMap = new HashMap<String, CEIAlarm>();
        causeMap = new HashMap<String, Cause>();
        timeLineMap = new HashMap<String, CEITimeline>();
    }

    public void parseDocument() {

        //get a factory
        final SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            final SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            final ClassLoader cldr = Thread.currentThread().getContextClassLoader();
            final InputStream xmlStream = cldr.getResourceAsStream("testData.xml");

            sp.parse(xmlStream, this);

        } catch (final SAXException se) {
            se.printStackTrace();
        } catch (final ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (final IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * @return the eventMap
     */
    public Map<String, CustomerCareUser> getEventMap() {
        return eventMap;
    }

    /**
     * @return the handsetMap
     */
    public Map<String, CustomerCareHandset> getHandsetMap() {
        return handsetMap;
    }

    /**
     * @return the incidentMap
     */
    public ArrayList<TestData> getIncidentMap() {
        return incidents;
    }

    /**
     * @return the timelineMap
     */
    public Map<String, CustomerCareTimelines> getTimelineMap() {
        return timelineMap;
    }

    //Event Handlers
    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
            throws SAXException {
        //reset 
        tempVal = "";

        if (qName.equalsIgnoreCase(CUSTOMER_CARE_USER_TAG)) {
            //create a new instance of employee
            tempEvent = new CustomerCareUser();
            tempEventId = attributes.getValue(ID);
        } else if (qName.equalsIgnoreCase(CUSTOMER_CARE_HANDSET_TAG)) {
            final String id = attributes.getValue(ID);
            final CustomerCareHandset handset = getHandset(attributes);
            handsetMap.put(id, handset);
        } else if (qName.equalsIgnoreCase(CUSTOMER_CARE_TIMELINES_TAG)) {
            tempTimeline = new StubbedCustomerCareTimelines();
            tempTimelineId = attributes.getValue(ID);
        } else if (qName.equalsIgnoreCase(CUSTOMER_CARE_ALARM_TAG)) {
            final String alarmId = attributes.getValue(ID);
            final CEIAlarm alarm = getAlarm(attributes);
            alarmMap.put(alarmId, alarm);
        } else if (qName.equalsIgnoreCase("Cause")) {
            final String id = attributes.getValue(ID);
            final Cause cause = getCause(attributes);
            causeMap.put(id, cause);
        } else if (qName.equalsIgnoreCase("timeline_cei")) {
            final String ceiTimeline_id = attributes.getValue(ID);
            final CEITimeline ceiTimeline = getCeiTimeline(attributes);
            timeLineMap.put(ceiTimeline_id, ceiTimeline);
        } else if (qName.equalsIgnoreCase(INCIDENT_TAG)) {
            tempTestname = attributes.getValue("test");
        }
    }

    private CustomerCareHandset getHandset(final Attributes attributes) {
        final CustomerCareHandset handset = new CustomerCareHandset();
        handset.setModel(attributes.getValue(attributes.getValue("model")));
        handset.setOs(attributes.getValue(attributes.getValue("os")));
        handset.setVendor(attributes.getValue(attributes.getValue("vendor")));
        return handset;
    }

    private CEIAlarm getAlarm(final Attributes attributes) {
        final CEIType type = CEIType.valueOf(attributes.getValue("type"));
        final Cause cause = causeMap.get(attributes.getValue("cause"));
        final CEIAlarm alarm = new CEIAlarm();
        alarm.setCause(cause);
        alarm.setType(type);
        return alarm;
    }

    private Cause getCause(final Attributes attributes) {
        final int result = Integer.parseInt(attributes.getValue("result"));
        final int cause = Integer.parseInt(attributes.getValue("cause"));
        final int subCause = Integer.parseInt(attributes.getValue("subcause"));
        final Cause c = new Cause(result, cause, subCause);
        return c;
    }

    private CEITimeline getCeiTimeline(final Attributes attributes) {
        final Locale locale = new Locale("en");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", locale);
        Date startDate;
        Date endDate;
        try {
            startDate = df.parse(attributes.getValue("startDate"));
            endDate = df.parse(attributes.getValue("endDate"));
        } catch (final ParseException e) {
            throw new RuntimeException("Something wrong with the xml file\n", e);
        }
        final String type_name = attributes.getValue("type");
        final CEIType type = CEIType.valueOf(type_name);
        final double[] values = getDoubleArrayFromString(attributes.getValue("values"));
        final CEITimeline timeline = new CEITimeline();
        timeline.setEndDate(endDate);
        timeline.setStartDate(startDate);
        timeline.setType(type);
        timeline.setValues(values);
        return timeline;
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {

        if (qName.equalsIgnoreCase(CUSTOMER_CARE_USER_TAG)) {
            //add it to the list
            eventMap.put(tempEventId, tempEvent);
        } else if (qName.equalsIgnoreCase(IMSI_TAG)) {
            tempEvent.setImsi(tempVal);
        } else if (qName.equalsIgnoreCase(IMSISV_TAG)) {
            tempEvent.setImeisv(tempVal);
        } else if (qName.equalsIgnoreCase(MSISDN_TAG)) {
            tempEvent.setMsisdn(tempVal);
        } else if (qName.equalsIgnoreCase(HANDSET_TAG)) {
            tempEvent.setHandset(handsetMap.get(tempVal));
        } else if (qName.equalsIgnoreCase(CUSTOMER_CARE_HANDSET_TAG)) {
            handsetMap.put(tempHandsetId, tempHandset);
        } else if (qName.equalsIgnoreCase(USER_TAG)) {
            tempUserId = tempVal;
        } else if (qName.equalsIgnoreCase(TIMELINE_TAG)) {
            tempTimelineId = tempVal;
        } else if (qName.equalsIgnoreCase(INCIDENT_TAG)) {
            incidents.add(new TestData(eventMap.get(tempUserId), timelineMap.get(tempTimelineId), tempExplanation,
                    tempTestname));
        } else if (qName.equalsIgnoreCase(CUSTOMER_CARE_TIMELINES_TAG)) {
            timelineMap.put(tempTimelineId, tempTimeline);
        } else if (qName.equalsIgnoreCase(END_DATE_TAG)) {
            tempTimeline.setEndDate(tempVal);
        } else if (qName.equalsIgnoreCase(START_DATE_TAG)) {
            tempTimeline.setStartDate(tempVal);
        } else if (qName.equalsIgnoreCase(CEI_TAG)) {
            tempTimeline.addCei(timeLineMap.get(tempVal));
        } else if (qName.equalsIgnoreCase(LOCATION_CEI_TAG)) {
            tempTimeline.addLocCei(timeLineMap.get(tempVal));
        } else if (qName.equalsIgnoreCase(NETWORK_CEI_TAG)) {
            tempTimeline.addNetworkCei(timeLineMap.get(tempVal));
        } else if (qName.equalsIgnoreCase(CUSTOMER_CARE_ALARM_TAG)) {
            alarmMap.put(tempAlarmId, tempAlarm);
        } else if (qName.equalsIgnoreCase(CEI_ALARM_TAG)) {
            final CEIAlarm[] alarms = getIntArrayFromString();
            tempTimeline.setAlarms(alarms);
        } else if (qName.equalsIgnoreCase("expected")) {
            tempExplanation = tempVal;
        }
    }

    /**
     * Method to parse a String (which represents an array of ints) and return it as an array of ints
     * 
     * @return
     */
    private CEIAlarm[] getIntArrayFromString() {
        final String[] alarmIDs = tempVal.split("\\,");
        final CEIAlarm[] alarms = new CEIAlarm[alarmIDs.length];
        for (int x = 0; x < alarms.length; x++) {
            alarms[x] = alarmMap.get(alarmIDs[x]);
        }
        return alarms;
    }

    /**
     * Method to parse a String (which represents an array of doubles) and return it as an array of doubles
     * 
     * @return
     */
    private double[] getDoubleArrayFromString(final String doubles) {
        final String[] stringValues = doubles.split("\\,");

        final double[] values = new double[stringValues.length];
        for (int x = 0; x < stringValues.length; x++) {
            values[x] = Double.parseDouble(stringValues[x]);
        }
        return values;
    }
}
