package com.ibericoders.ibinternal.app.persistence.expenses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ibericoders.ibinternal.app.persistence.generic.DatabaseHelper;
import com.ibericoders.ibinternal.content.model.expenses.Expense;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: Clase con los métodos necesarios para interaccionar con la base de datos de gastos.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class ExpensesDatabase {

    private SQLiteDatabase mDatabase;

    public ExpensesDatabase(Context ctx){

        //Creación objeto ayudante y obtención de la base de datos

        DatabaseHelper helper = new DatabaseHelper(ctx, "data");
        mDatabase = helper.getWritableDatabase();
    }

    public void saveNewExpense(Expense expense){

        if(!checkExpense(expense.getName())){

            String sql = "insert into expensesTable (name,description,amount,date,category) ";

            sql+="values ('" + expense.getName() + "','" + expense.getDescription() + "'," + expense.getAmount() +
                    ",'" + expense.getDate() + "'," + expense.getCategory() + ")";

            mDatabase.execSQL(sql);
        }
    }

    public boolean checkExpense(String expenseName){

        boolean res = false;

        String sql = "select * from expensesTable where name='" + expenseName + "'";

        Cursor c = mDatabase.rawQuery(sql, null);

        while(c.moveToNext()){

            res = true;
        }

        c.close();

        return res;
    }

    public List<Expense> getAllExpenses(){

        String sql="select * from expensesTable";

        Cursor c= mDatabase.rawQuery(sql, null);

        List<Expense> expenses = new ArrayList<>();

        while(c.moveToNext()){

            Expense g = new Expense(c.getString(1), c.getString(2), c.getDouble(3),
                    c.getString(4), c.getInt(5));
            expenses.add(g);
        }
        c.close();

        return expenses;
    }

    public List<Expense> getExpensesFromCategory(int category){

        String sql = "select * from expensesTable where category=" + category + "";

        Cursor c = mDatabase.rawQuery(sql, null);

        List<Expense> expenses = new ArrayList<>();

        while(c.moveToNext()){

            Expense g = new Expense(c.getString(1), c.getString(2), c.getDouble(3),
                    c.getString(4), c.getInt(5));
            expenses.add(g);
        }
        c.close();
        return expenses;
    }

    public Expense getExpense(String name){

        String sql = "select * from expensesTable where name='" + name + "'";

        Cursor c= mDatabase.rawQuery(sql, null);
        Expense g = null;

        while(c.moveToNext()){

            g = new Expense(c.getString(1), c.getString(2), c.getDouble(3),
                    c.getString(4), c.getInt(5));
        }
        c.close();

        return g;
    }

    public void deleteExpense(String name) {

        String sql = "delete * from expensesTable where name='" + name + "'";
        mDatabase.execSQL(sql);
    }
}
