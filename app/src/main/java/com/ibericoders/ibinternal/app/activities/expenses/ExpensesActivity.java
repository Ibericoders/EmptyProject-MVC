package com.ibericoders.ibinternal.app.activities.expenses;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.adapters.expenses.ExpensesListAdapter;
import com.ibericoders.ibinternal.app.persistence.expenses.ExpensesDatabase;
import com.ibericoders.ibinternal.common.constants.Constants;
import com.ibericoders.ibinternal.common.utils.Utils;
import com.ibericoders.ibinternal.content.model.expenses.Expense;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class ExpensesActivity extends InflatedActivity implements View.OnClickListener{

    /*
     * Constantes de la actividad
     */
    private static final int CHOOSE_FILE_REQUESTCODE=15;


    /*
     * Atributos de la UI
     */
    @BindView(R.id.listagastos)
    ListView gastos;

    @BindView(R.id.cv_exportar)
    CardView exportar;

    @BindView(R.id.edt_emailExportar)
    EditText em_exp;

    @BindView(R.id.tv_filtroGasto)
    TextView filtro;

    @BindView(R.id.fabPrincipal)
    FloatingActionButton fab1;

    @BindView(R.id.fabsub1)
    FloatingActionButton fab2;

    @BindView(R.id.fabsub2)
    FloatingActionButton fab3;

    @BindView(R.id.tv_fabsub1)
    TextView tvfab2;

    @BindView(R.id.tv_fabsub2)
    TextView tvfab3;

    @BindView(R.id.tv_valorBote)
    TextView bote;

    /*
     * Atributos de negocio
     */
    List<Expense> datos;
    ExpensesDatabase ggastos;
    ExpensesListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_expenses);

        inflateView();
        initAttrs();
        fillView();
        initListeners();
    }

    @Override
    protected void initAttrs() {

        //Obtener valor del bote
        SharedPreferences prefs=getSharedPreferences("bote", Context.MODE_PRIVATE);
        if(prefs.getString("bote","null").equals("null")){
            bote.setText("0 €");
        }else {
            bote.setText(prefs.getString("bote", null) + " €.");
        }

        //Obtener datos
        ggastos=new ExpensesDatabase(this);
        datos=ggastos.getAllExpenses();

        //Crear adaptador
        mAdapter = new ExpensesListAdapter(this, datos);
    }


    @Override
    protected void fillView() {

        /*
         * Controlar visibilidad de objetos
         */
        filtro.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        fab3.setVisibility(View.GONE);
        tvfab2.setVisibility(View.GONE);
        tvfab3.setVisibility(View.GONE);
        exportar.setVisibility(View.GONE);

        gastos.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        ExpensesListAdapter adapter = new ExpensesListAdapter(this, ggastos.getAllExpenses());
        gastos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expenses_optionsmenu, menu);

        // return true so that the menu pop up is opened
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.filtrar:
                showDialogMenu();
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        showDialogMenu();
                        Log.d("Dialog used.","here");
                    }
                }; break;
            case R.id.importar:
                importar();
                break;
            case R.id.export:
                empezarProceso();
                break;
        }
        return true;
    }

    public void showDialogMenu(){

        final String[] categorias = new String[]{Utils.getExpensesCategoryString(this, Constants.CATEGORY_COMMON),
                Utils.getExpensesCategoryString(this, Constants.CATEGORY_FOOD_DRINK),
                Utils.getExpensesCategoryString(this, Constants.CATEGORY_EDUCATION),
                Utils.getExpensesCategoryString(this, Constants.CATEGORY_OTHER),
                Utils.getExpensesCategoryString(this, Constants.CATEGORY_EXTRA_NONPLANNED)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.dialog_category_chooser));
        builder.setItems(categorias, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Activar textview con name categoria
                filtro.setText(getResources().getString(R.string.expenses_selectedcategory) + categorias[which]);
                filtro.setVisibility(View.VISIBLE);

                //Cambiar mAdapter del listview
                //Obtener datos
                ggastos=new ExpensesDatabase(ExpensesActivity.this);
                datos=ggastos.getExpensesFromCategory(Utils.getExpensesCategoryInt(ExpensesActivity.this, categorias[which]));

                //Mostrar datos en ListView
                ExpensesListAdapter adapter=new ExpensesListAdapter(ExpensesActivity.this,datos);
                gastos.setAdapter(adapter);
            }
        });
        builder.show();
    }

    public void nuevo(View v){
        Intent intent=new Intent(this,NewExpenseActivity.class);
        this.startActivity(intent);
    }

    public void desplegar(View v){
        //Filtro de pulsaciones(Abrir/Cerrar)
        if(fab2.getVisibility()==View.GONE){
            //Activar dos subbotones/textos
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            tvfab2.setVisibility(View.VISIBLE);
            tvfab3.setVisibility(View.VISIBLE);
            //Cambiar imagen boton principal
            fab1.setImageResource(R.drawable.ic_cerrar);
            //Oscurecer pantalla
        }else if(fab2.getVisibility()==View.VISIBLE){
            //Volver al inicio
            //Desactivar dos subbotones/textos
            fab2.setVisibility(View.GONE);
            fab3.setVisibility(View.GONE);
            tvfab2.setVisibility(View.GONE);
            tvfab3.setVisibility(View.GONE);
            //Cambiar imagen boton principal
            fab1.setImageResource(R.drawable.ic_anadir);

        }

    }

    public void anadir(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExpensesActivity.this);
        alertDialog.setTitle("Añadir dinero al bote");
        alertDialog.setMessage("Introduce la cantidad a añadir");

        final EditText input = new EditText(ExpensesActivity.this);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double cantidad=Double.parseDouble(input.getText().toString());
                SharedPreferences prefs=getSharedPreferences("bote", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();
                if(prefs.getString("bote","null").equals("null")){
                    editor.remove("bote");
                    editor.putString("bote",String.valueOf(cantidad));
                    bote.setText(String.valueOf(cantidad)+" €.");
                }else{
                    String anterior=prefs.getString("bote",null);
                    double valorAnterior=Double.parseDouble(anterior);
                    String res=String.valueOf(valorAnterior+cantidad);
                    editor.remove("bote");
                    editor.putString("bote",res);
                    bote.setText(res+" €.");
                    /*if(valorAnterior+cantidad < 0){
                        bote.setTextColor(ContextCompat.getColor(MainExpensesActivity.this, android.R.color.holo_red_dark));
                    }else{
                        bote.setTextColor(ContextCompat.getColor(MainExpensesActivity.this, android.R.color.holo_green_dark));
                    }*/
                }
                editor.apply();
                dialog.cancel();
            }
        });

        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void empezarProceso(){
        exportar.setVisibility(View.VISIBLE);
    }
    public void exportar(View v){

        String email=em_exp.getText().toString();
        if(email.contains("@") && email.endsWith(".com")){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Export export =new Export();
                export.execute(email);
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
            }
        }else {
            Toast.makeText(this, "Email no valido", Toast.LENGTH_LONG).show();
            em_exp.setText("");
        }
    }

    public void importar(){
        //Crear intent para selección de archivo
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        Intent i = Intent.createChooser(intent, "Archivo");
        startActivityForResult(i, CHOOSE_FILE_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Recibir la información de vuelta del Explorador de Archivos.
        if(requestCode==CHOOSE_FILE_REQUESTCODE && resultCode==RESULT_OK){

            //Obtener ruta del archivo
            String[] paths=data.getData().getLastPathSegment().split("[:]");
            String path=paths[1];

            //Leer el archivo y procesar los expenses

            //Uri uri=Uri.parse(path);
            File archivo=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),path);

            try {
                BufferedReader bf = new BufferedReader(new FileReader(archivo));
                String linea;
                int totalGastos=0;
                int gastosAnadidos=0;
                while ((linea = bf.readLine()) != null) {
                    totalGastos++;
                    String[] g = linea.split("[|]");
                    Expense expense = new Expense(g[0], g[1], Double.parseDouble(g[2]), g[3], Utils.getExpensesCategoryInt(this, g[4]));

                    if(!ggastos.checkExpense(expense.getName())){
                        ggastos.saveNewExpense(expense);
                        gastosAnadidos++;
                    }
                }
                Toast.makeText(this,"Gastos encontrados: "+totalGastos+". Gastos añadidos: "+gastosAnadidos+".",Toast.LENGTH_LONG).show();

                //Mostrar datos en ListView
                ExpensesListAdapter adapter=new ExpensesListAdapter(this,datos);
                gastos.setAdapter(adapter);

            }catch(IOException ex){
                ex.printStackTrace();
                Toast.makeText(this,"Archivo no legible o no encontrado",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(this,"El acceso al almacenamiento externo es necesario para usar esta función.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class Export extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            if(Build.VERSION.SDK_INT>24){

                //Comprobar si se puede acceder al almacenaje externo.
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

                    //Obtener los datos a save en el archivo y el name del archivo.
                    List<Expense> expenses =ggastos.getAllExpenses();
                    Calendar cal=Calendar.getInstance();
                    String filename="Exportado"+cal.get(Calendar.DAY_OF_MONTH)+"|"+cal.get(Calendar.MONTH)+"|"+cal.get(Calendar.YEAR)+
                            "|"+cal.get(Calendar.HOUR)+"|"+cal.get(Calendar.MINUTE)+".txt";

                    //Crear la carpeta
                    File folder = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOCUMENTS), "Ibericoders");
                    if (!folder.mkdirs()) {
                        folder.mkdirs();
                    }
                    //Crear el archivo
                    File file = new File(folder, filename);

                    //Escribir datos en el archivo
                    try {
                        FileWriter writer = new FileWriter(file);
                        for(int i = 0; i< expenses.size(); i++){
                            writer.append(expenses.get(i).toString()+"\n");
                        }
                        writer.flush();
                        writer.close();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                    //Enviar el archivo por email

                    //Obtener localizacion
                    File filelocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/Ibericoders/", filename);
                    Uri path = Uri.fromFile(filelocation);

                    // Indicar que vas a mandar un email
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent .setType("vnd.android.cursor.dir/email");

                    //Recipientes del email
                    String to[] = {"ibericoders@gmail.com",params[0]};
                    emailIntent .putExtra(Intent.EXTRA_EMAIL, to);

                    // Añadir el adjunto
                    emailIntent .putExtra(Intent.EXTRA_STREAM, path);

                    // Asunto del email
                    emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Base de datos exportada");
                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    //Enviar email
                    startActivityForResult(Intent.createChooser(emailIntent , "Enviar email..."),10);
                }else{
                    Toast.makeText(ExpensesActivity.this,"El almacenamiento externo no es accesible.",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(ExpensesActivity.this,"Esta función sólo está disponible en dispositivos con Android 7 o superior",Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(ExpensesActivity.this, "Base de datos exportada correctamente.", Toast.LENGTH_LONG).show();
        }
    }
}
