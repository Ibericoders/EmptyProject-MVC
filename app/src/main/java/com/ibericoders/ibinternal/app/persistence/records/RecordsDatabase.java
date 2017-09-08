package com.ibericoders.ibinternal.app.persistence.records;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ibericoders.ibinternal.app.persistence.generic.DatabaseHelper;
import com.ibericoders.ibinternal.content.model.records.Attendee;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class RecordsDatabase {

    private SQLiteDatabase mDatabase;

    public RecordsDatabase(Context ctx){

        //Creación objeto ayudante y obtención de la base de datos

        DatabaseHelper helper = new DatabaseHelper(ctx, "data");
        mDatabase = helper.getWritableDatabase();
    }


    //-----------------------------Recuperar Actas--------------------------------------------------

    public List<Record> getAllRecords() {

        String sql = "select * from recordsTable";

        Cursor c = mDatabase.rawQuery(sql, null);

        ArrayList<Record> records = new ArrayList<>();

        while (c.moveToNext()){

            String attendees = c.getString(5);

            String[] attendeesArray = attendees.split("[|]");

            ArrayList<Attendee> attendeeList = new ArrayList<>();

            for (int i = 0; i < attendeesArray.length; i++){

                attendeeList.add(Attendee.fromJson(attendeesArray[i]));
            }

            Record record = new Record(c.getLong(1), attendeeList, c.getString(2), c.getString(3), c.getLong(4));

            records.add(record);
        }
        return records;
    }
    //----------------------------------------------------------------------------------------------
    //---------------------------------Metodo guardar----------------------------------------------
    public void saveRecord(Record record) {

        List<Attendee> attendees = record.getAttendees();
        String attendeeAll = "";
        for (int i = 0; i < attendees.size(); i++){

            String attendee = Attendee.toJson(attendees.get(i));
            attendeeAll.concat(attendee);

        }
        String sql="insert into recordsTable(date,title,act,nextmeeting,attendees)";
        sql+="values(" + record.getDate() + ",'" + record.getTitle() + "','" + record.getAct()+ "'," + record.getNextMeeting() + ",'" + attendeeAll + "')";
        mDatabase.execSQL(sql);

    }
    //----------------------------------------------------------------------------------------------

    //-------------------------------Metodo recuperar fecha------------------------------------------
    public Record getRecordFromDate(long date) {
        String sql = "select * from recordsTable where date =" + date;

        Cursor c = mDatabase.rawQuery(sql, null);

        Record record = null;

        if (c.moveToNext()){

            String attendees = c.getString(5);

            String[] attendeesArray = attendees.split("[|]");

            ArrayList<Attendee> attendeeList = new ArrayList<>();

            for (int i = 0; i < attendeesArray.length; i++){

                attendeeList.add(Attendee.fromJson(attendeesArray[i]));
            }

            record = new Record(c.getLong(1), attendeeList, c.getString(2), c.getString(3), c.getLong(4));
        }

        return record;
    }
    //----------------------------------------------------------------------------------------------
}
