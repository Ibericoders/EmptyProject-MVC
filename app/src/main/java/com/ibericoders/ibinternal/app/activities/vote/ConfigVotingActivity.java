package com.ibericoders.ibinternal.app.activities.vote;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ibericoders.ibinternal.R;
import java.util.ArrayList;

public class ConfigVotingActivity extends AppCompatActivity {

    private EditText topicName, editDescription, editDate, editOpenAnswer;
    private SeekBar seekBarParticipants;
    private TextView textViewParticipants;
    private int totalParticipants;
    private Spinner spinner;
    private String cat = null;
    private ArrayList<String> answersArray;
    private FloatingActionButton fabAddOpenAnswer;
    private TextInputLayout textInputLayoutAnswers;
    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_config_voting);

        //Procedemos a instanciar los objetos de la UI y a iniciar variables.
        topicName = findViewById(R.id.edt_config_topic);
        editDescription = findViewById(R.id.edt_votingdescription);
        editDate = findViewById(R.id.edt_votingdate);
        seekBarParticipants = findViewById(R.id.seekBarParticipants);
        textViewParticipants = findViewById(R.id.numberOfParticipantsTv);
        editOpenAnswer = findViewById(R.id.edt_openAnswer);
        fabAddOpenAnswer = findViewById(R.id.fab_addAnswer);
        totalParticipants = 0;
        spinner = findViewById(R.id.sp_numberOfAnswers);
        answersArray = new ArrayList<>();
        textInputLayoutAnswers = findViewById(R.id.tilAnswers);
        //declaramos una variable para llevar el contador de respuestas añadidas al array
        cont = 0;

        //el Text Input Layout de las respuestas personalizables comienza siendo invisible.
        editOpenAnswer.setVisibility(View.INVISIBLE);
        fabAddOpenAnswer.setVisibility(View.INVISIBLE);
        textInputLayoutAnswers.setVisibility(View.INVISIBLE);

        //programamos el comportamiento del seekbar
        seekBarParticipants.setMax(20);
        setParticipants();
        seekBarParticipants.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentParticipants, boolean fromUser) {
                //El progreso de la barra muestra el total de participantes de la votacion.
                totalParticipants = currentParticipants;
                setParticipants();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                totalParticipants = seekBarParticipants.getProgress();
                setParticipants();

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Seleccione tipo", "Si / No", "A, B, C, D", "Respuestas personalizables"});
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = parent.getSelectedItem().toString();

                switch (cat) {

                    case "Si / No":
                        setVisibilityForAnswers(false);
                        break;

                    case "A, B, C, D":
                        setVisibilityForAnswers(false);
                        break;


                    //en el caso de que el usuario elija "Respuestas personalizables", los botones para añadir dichas respuestas se hacen visibles
                    case "Respuestas personalizables":
                        setVisibilityForAnswers(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                //Generar cuadro de dialogo de editDate
                DatePickerDialog dgDate = new DatePickerDialog(ConfigVotingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //Cada vez que se seleccione una date se genera una cadena con los datos de la fecha seleccionada.
                        String selectedDate = view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear();
                        //Volcamos la cadena de date en el TextView
                        editDate.setText(selectedDate);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                dgDate.show();
            }
        });
    }


    public void cancel(View v) {
        this.finish();
    }

    public void vote(View v) {
        if (topicName.getText().length() > 0 && editDescription.getText().length() > 0 && editDate.getText().length() > 0 && totalParticipants > 0) {
            switch (cat) {
                case "Si / No":
                    addYesNoToArray();
                    break;

                case "A, B, C, D":
                    addABCDToArray();
                    break;
            }
            sendConfig();
        } else {
            Toast.makeText(this, "Es necesario completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void sendConfig() {
        //se abre una nueva actividad y se manda a traves
        //del Intent el resultado de las voting.
        Intent intent = new Intent(ConfigVotingActivity.this, VoteActivity.class);
        intent.putExtra("totalParticipants", totalParticipants);
        intent.putExtra("topic", topicName.getText().toString());
        intent.putExtra("answersArray", answersArray);
        intent.putExtra("description", editDescription.getText());
        intent.putExtra("cat", cat);
        intent.putExtra("date", editDate.getText());

        startActivity(intent);
    }


    public void setParticipants() {
        String text = totalParticipants + "";
        textViewParticipants.setText(text);
    }

    //metodo para añadir una respuesta customizada al ArrayList de respuestas, al pulsar el boton de añadir
    public void addAnswer(View view) {
        //añadimos una respuesta al contador de respuestas
            if (cont <= 4 && !TextUtils.isEmpty(editOpenAnswer.getText().toString())) {
                answersArray.add(editOpenAnswer.getText().toString());
                editOpenAnswer.getText().clear();
                Toast.makeText(this, "Respuesta añadida correctamente", Toast.LENGTH_SHORT).show();
                //añadimos el string que sacamos del editText al array de respuestas.
                cont++;
                editOpenAnswer.setHint("Respuesta " + cont);
            }else if (TextUtils.isEmpty(editOpenAnswer.getText().toString())){
                    Toast.makeText(this, "Añade primero una respuesta", Toast.LENGTH_SHORT).show();
            } else if (cont > 4){
                //si se añadido 4 respuestas, ya no se puede más.
                Toast.makeText(this, "Número máximo de respuestas personalizables", Toast.LENGTH_SHORT).show();
            }
        }

    private void addYesNoToArray() {
        for (int i = 0; i < answersArray.size(); i++){

            answersArray.remove(i);
        }

        answersArray.add("Si");
        answersArray.add("No");
    }

    private void addABCDToArray() {

        for (int i = 0; i < answersArray.size(); i++){

            answersArray.remove(i);
        }
        answersArray.add("Opción A");
        answersArray.add("Opción B");
        answersArray.add("Opción C");
        answersArray.add("Opción D");
    }

    private void setVisibilityForAnswers(boolean visible) {
        if (visible) {
            textInputLayoutAnswers.setVisibility(View.VISIBLE);
            editOpenAnswer.setVisibility(View.VISIBLE);
            fabAddOpenAnswer.setVisibility(View.VISIBLE);
        }else{
            textInputLayoutAnswers.setVisibility(View.GONE);
            editOpenAnswer.setVisibility(View.GONE);
            fabAddOpenAnswer.setVisibility(View.GONE);
        }
    }
}
