package com.ibericoders.ibinternal.content.model.records;

import android.os.Parcelable;

import java.util.List;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class Record implements Parcelable{

    private Long date;
    private List<Attendee> attendees;
    private String title;
    private String act;
    private Long nextMeeting;

    public Record(Long date, Long nextMeeting) {
        this.date = date;
        this.nextMeeting = nextMeeting;
    }

    public Record(String title, String act) {
        this.title = title;
        this.act = act;
    }

    public Record(Long date, List<Attendee> attendees, String title, String act, Long nextMeeting) {
        this.date = date;
        this.attendees = attendees;
        this.title = title;
        this.act = act;
        this.nextMeeting = nextMeeting;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public Long getNextMeeting() {
        return nextMeeting;
    }

    public void setNextMeeting(Long nextMeeting) {
        this.nextMeeting = nextMeeting;
    }
}
