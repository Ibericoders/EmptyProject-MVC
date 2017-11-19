package com.ibericoders.ibinternal.app.activities.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ibericoders.ibinternal.R;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {

    private TextView textViewTopic;
    //creamos variables numericas para contar cuántos votos tiene cada opcion

    private int totalParticipants, totalVotesInOptions;
    private String topic;
    private ArrayList arrayAnswers;
    private ArrayList<Integer> arrayVotes;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_vote);
        layout = findViewById(R.id.layout_VoteActivity);

        //Procedemos a instanciar los objetos de la UI y a iniciar variables.
        textViewTopic = findViewById(R.id.textViewTopic);
        totalParticipants = 0;//iniciamos las variables numericas en 0;
        totalVotesInOptions = 0;//necesitamos esta variable para saber el total de votos que se llevan.

        final int totalParts = 0;
        Integer totalVotos = 0;
        int totalVotosA = 0;

        //Recogemos los datos de la configuracion.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totalParticipants = bundle.getInt("totalParticipants");
            topic = bundle.getString("topic");
            arrayAnswers = bundle.getStringArrayList("answersArray");
        }
        textViewTopic.setText(topic);//seteamos el texto del topic en el título.

        arrayVotes = new ArrayList<>(totalParticipants);

        int BUTTON_WIDTH = 80;
        int BUTTON_HEIGHT = 40;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT);


        for (int i=0; i<arrayAnswers.size(); i++){//para cada elemento de la lista de respuestas generamos un boton y le añadimos su texto.
            if (arrayAnswers.get(i)!= null){
                Button bt = new Button(this);
                //Si la posicion no está vacía, seteamos sus atributos
                bt.setId(View.generateViewId()); //cada boton tiene su id propia
                bt.setTag(i);
                bt.setText(arrayAnswers.get(i).toString()); //el string se vuelca en el boton
                bt.setLayoutParams(params);
                bt.setVisibility(View.VISIBLE);// se hace visible
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) view.getTag();

                        switch(position){

                            case 0:
                                break;
                            case 1:
                                break;

                        }

                        sendResults();
                    }
                });
                layout.addView(bt,i);
                }

            }

        }



        //programamos el comportamiento de los botones
        /*buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votesInOptionA++;
                totalVotesInOptions++;
                sendResults();
                Toast.makeText(VoteActivity.this, "Has votado correctamente.", Toast.LENGTH_SHORT).show();
                //cada click, añade un voto a la variable correspondiente, pero sólo si
                // el numero de participantes se ha definido antes y es mayor que 0.
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votesInOptionB++;
                totalVotesInOptions++;
                sendResults();
                Toast.makeText(VoteActivity.this, "Has votado correctamente.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votesInOptionC++;
                totalVotesInOptions++;
                sendResults();
                Toast.makeText(VoteActivity.this, "Has votado correctamente.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votesInOptionD++;
                totalVotesInOptions++;
                sendResults();
                Toast.makeText(VoteActivity.this, "Has votado correctamente.", Toast.LENGTH_SHORT).show();
            }
        });*/




    public void sendResults() {
        //le metemos un retardo de 0.5 segundos.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (totalVotesInOptions == totalParticipants && totalVotesInOptions != 0) {
            //si el numero total de votos es igual al de participantes, y distinto de 0
            //iniciamos la siguiente activity.
            //cuando se pulsa el botón, se abre una nueva actividad y se manda a traves
            //del Intent el resultado de las voting.
            Intent intent = new Intent(VoteActivity.this, ResultActivity.class);
            //intent.putExtra("votesA", votesInOptionA);
            intent.putExtra("totalParticipants", totalParticipants);
            intent.putExtra("topic", topic);
            startActivity(intent);

        }
    }
}
