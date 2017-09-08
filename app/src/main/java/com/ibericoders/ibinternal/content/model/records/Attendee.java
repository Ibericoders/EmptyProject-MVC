package com.ibericoders.ibinternal.content.model.records;

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
    private String workPosition;

    public Attendee(String name, String email, String workPosition) {
        this.name = name;
        this.email = email;
        this.workPosition = workPosition;
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

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public static String toJson(Attendee attendee){

        Gson gson = new Gson();

        return gson.toJson(attendee);
    }

    public static Attendee fromJson(String attendee){

        Gson gson = new Gson();

        return gson.fromJson(attendee, Attendee.class);
    }
}
