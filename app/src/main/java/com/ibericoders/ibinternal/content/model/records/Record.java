package com.ibericoders.ibinternal.content.model.records;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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


    /*
     * Implementación de Parcelable
     */
    protected Record(Parcel in) {
        date = in.readByte() == 0x00 ? null : in.readLong();
        if (in.readByte() == 0x01) {
            attendees = new ArrayList<Attendee>();
            in.readList(attendees, Attendee.class.getClassLoader());
        } else {
            attendees = null;
        }
        title = in.readString();
        act = in.readString();
        nextMeeting = in.readByte() == 0x00 ? null : in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (date == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(date);
        }
        if (attendees == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(attendees);
        }
        dest.writeString(title);
        dest.writeString(act);
        if (nextMeeting == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(nextMeeting);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
