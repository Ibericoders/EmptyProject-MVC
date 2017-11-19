package com.ibericoders.ibinternal.content.model.records;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class Attendee implements Parcelable {

    private String name;
    private String email;


    public Attendee(String name, String email, String workPosition) {
        this.name = name;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static String toJson(Attendee attendee){

        Gson gson = new Gson();

        return gson.toJson(attendee);
    }

    public static Attendee fromJson(String attendee){

        Gson gson = new Gson();

        return gson.fromJson(attendee, Attendee.class);
    }


    /*
     * Implementación de Parcelable
     */
    protected Attendee(Parcel in) {
        name = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Attendee> CREATOR = new Parcelable.Creator<Attendee>() {
        @Override
        public Attendee createFromParcel(Parcel in) {
            return new Attendee(in);
        }

        @Override
        public Attendee[] newArray(int size) {
            return new Attendee[size];
        }
    };
}
