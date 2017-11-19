package com.ibericoders.ibinternal.app.activities.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ibericoders.ibinternal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoteActivity extends AppCompatActivity {

    @BindView(R.id.textViewTopic) TextView textViewTopic;
    @BindView(R.id.linearLayoutAnswers) LinearLayout layout;
    //creamos variables numericas para contar cuántos votos tiene cada opcion

    private int totalParticipants, totalVotesInOptions, votesInA, votesInB, votesInC, votesInD;
    private String topic;
    private ArrayList arrayAnswers;
    private ArrayList<Integer> arrayVotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_vote);

        //Procedemos a instanciar los objetos de la UI y a iniciar variables.
        ButterKnife.bind(this);
        totalParticipants = 0;//iniciamos las variables numericas en 0;
        totalVotesInOptions = 0;//necesitamos esta variable para saber el total de votos que se llevan.
        votesInA = 0; votesInB = 0; votesInC = 0; votesInD = 0;

        //Recogemos los datos de la configuracion.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totalParticipants = bundle.getInt("totalParticipants");
            topic = bundle.getString("topic");
            arrayAnswers = bundle.getStringArrayList("answersArray");
        }
        textViewTopic.setText(topic);//seteamos el texto del topic en el título.

        arrayVotes = new ArrayList<>(totalParticipants);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


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
                                votesInA++;
                                break;
                            case 1:
                                votesInB++;
                                break;
                            case 2:
                                votesInC++;
                                break;
                            case 3:
                                votesInD++;
                                break;

                        }
                        sendResults();
                    }
                });
                layout.addView(bt,i);
                }
            }
        }

    public void sendResults() {
        totalVotesInOptions++;
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
