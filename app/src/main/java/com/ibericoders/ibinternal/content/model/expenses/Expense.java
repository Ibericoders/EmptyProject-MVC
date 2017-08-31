package com.ibericoders.ibinternal.content.model.expenses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class Expense implements Parcelable {

    private String name;
    private String description;
    private double amount;
    private String date;
    private int category;

    public Expense(String name, String description, double amount, String date, int category) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name +"|"+ description +"|"+ amount +"|"+ date;
    }


    /*
     * Implementación de Parcelable
     */
    protected Expense(Parcel in) {
        name = in.readString();
        description = in.readString();
        amount = in.readDouble();
        date = in.readString();
        category = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeString(date);
        dest.writeInt(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Expense> CREATOR = new Parcelable.Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

}
