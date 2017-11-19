package com.ibericoders.ibinternal.app.activities.expenses;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.persistence.expenses.ExpensesDatabase;
import com.ibericoders.ibinternal.common.utils.Utils;
import com.ibericoders.ibinternal.content.model.expenses.Expense;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class NewExpenseActivity extends InflatedActivity{


    private static final String PREFS_POT = "bote";
    /*
     * Atributos de UI
     */
    @BindView(R.id.edt_nombreGasto)
    EditText name;

    @BindView(R.id.edt_descripcionGasto)
    EditText description;

    @BindView(R.id.edt_cantidadGasto)
    EditText amount;

    @BindView(R.id.edt_fechaGasto)
    EditText date;

    @BindView(R.id.sp_categoriaGasto)
    Spinner sp;

    @BindView(R.id.newexpense_cancel)
    Button cancel;

    /*
     * Atributos de negocio
     */
    private ExpensesDatabase expensesData;
    private String cat=null;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_newexpense);

        initAttrs();
        inflateView();
        fillView();
        initListeners();
    }

    @Override
    protected void initAttrs() {

        List<String> categories = Utils.getCategoriesList(this);
        String[] categoriesArray = new String[6];
        categoriesArray[0] = "Seleccione categoría...";

        for (int i = 0; i < categories.size(); i++){

            categoriesArray[i + 1] = categories.get(i);
        }
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriesArray);

        expensesData = new ExpensesDatabase(this);
    }

    @Override
    protected void fillView() {

        sp.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @OnClick(R.id.edt_fechaGasto)
    public void getExpenseDate(){
        Calendar cal=Calendar.getInstance();
        //Generar cuadro de dialogo de date
        DatePickerDialog dgDate=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Cada vez que se seleccione una date se genera una cadena con los datos de la feccha seleccionada.
                String fechaselec=view.getDayOfMonth()+"/"+(view.getMonth()+1)+"/"+view.getYear();
                //Volcamos la cadena de date en el TextView
                date.setText(fechaselec);
            }
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        dgDate.show();
    }

    @OnClick(R.id.newexpense_cancel)
    public void cancelRequest(){
        this.finish();
    }

    @OnClick(R.id.newexpense_save)
    public void saveData(){

        if(name.getText().length() > 0 && description.getText().length() > 0 && amount.getText().length() > 0
                && date.getText().length() > 0 && cat != null && !cat.equals("Seleccione categoría...")){

            Expense g = new Expense(name.getText().toString(), description.getText().toString(),
                    Double.parseDouble(amount.getText().toString()), date.getText().toString(),
                    Utils.getExpensesCategoryInt(this, cat));

            if(!expensesData.checkExpense(g.getName())){

                expensesData.saveNewExpense(g);
                Toast.makeText(this, "Gasto introducido correctamente", Toast.LENGTH_LONG).show();

                SharedPreferences prefs=getSharedPreferences(PREFS_POT, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();

                if(prefs.getString(PREFS_POT,"null").equals("null")){

                    editor.remove(PREFS_POT);
                    editor.putString(PREFS_POT,"-"+String.valueOf(g.getAmount()));

                }else{

                    double valorAnterior=Double.parseDouble(prefs.getString(PREFS_POT,null));
                    String res=String.valueOf(valorAnterior-g.getAmount());
                    editor.remove(PREFS_POT);
                    editor.putString(PREFS_POT,res);
                }
                editor.apply();
                this.finish();

            }else{

                Toast.makeText(this, "Gasto ya introducido", Toast.LENGTH_LONG).show();
            }
        }else{

            Toast.makeText(this, "Es necesario completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}
