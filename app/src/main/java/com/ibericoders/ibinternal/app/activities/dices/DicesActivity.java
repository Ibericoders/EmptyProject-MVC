package com.ibericoders.ibinternal.app.activities.dices;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Desc: Actividad principal del modulo Dados.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class DicesActivity extends InflatedActivity implements View.OnClickListener{

    /*
     * Constantes de la actividad
     */
    private static final int MAXSEEKBAR = 20;


    /*
     * Atributos de la UI
     */


    @BindView(R.id.dices_arbitrarynumber_tv)
    TextView textViewRandom;    //TextView sobre el que se vuelca el resultado aleatorio

    @BindView(R.id.dices_maximum_tv)
    TextView textViewMaximum;   //TextView en el que el usuario puede ver el numero máximo que marca.

    @BindView(R.id.dices_arbitrary_bt)
    Button buttonArbitrary;

    @BindView(R.id.dices_seekbar_sk)
    SeekBar seekBarMaximum;

    @BindView(R.id.dices_imgdice_iv)
    ImageView imgDice;    //ImageView en el que se va a posicionar el dado.


    /*
     * Atributos de negocio
     */
    private Random random;              //Genera numeros al azar.
    private Handler handler;            //Manejador para el TimerTask
    private Timer timer;                //Usado para darle feedback al usuario.
    private boolean rolling;            //Está el dado rodando?
    private int maxNumberEstablished;   //Número máximo establecido para el aleatorio.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_dicesact);

        initAttrs();
        inflateView();
        fillView();
        setupToolbar(null);
        initListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(timer != null){

            timer.cancel();
        }
    }

    @Override
    protected void initAttrs() {

        random = new Random();
        rolling = false;
        timer = new Timer();
    }

    @Override
    protected void fillView() {

        seekBarMaximum.setMax(MAXSEEKBAR); //El máximo numero aleatorio es 20.
        textViewMaximum.setText(String.valueOf(maxNumberEstablished));


    }

    @Override
    protected void initListeners() {

        buttonArbitrary.setOnClickListener(this);

        seekBarMaximum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentMaximum, boolean fromUser) {
                //SEGÚN SE MODIFICA LA BARRA DE PROGRESO, EL NUMERO MARCADO SE VUELCA EN EL TEXTVIEW.
                maxNumberEstablished = currentMaximum;
                textViewMaximum.setText(String.valueOf(maxNumberEstablished));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //AQUI NO HACEMOS NADA.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                maxNumberEstablished = seekBar.getProgress();
                textViewMaximum.setText(String.valueOf(maxNumberEstablished));
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.dices_arbitrary_bt:

                //GENERAMOS UN NUMERO DE 1 A 10 ALEATORIO.
                int randomNumber = (int) (Math.random() * maxNumberEstablished + 1);

                //VOLCAMOS ESE NUMERO EN EL TEXTVIEW.
                textViewRandom.setText(String.valueOf(randomNumber));
                break;

            case R.id.dices_imgdice_iv:

                //SI NO ESTÁ RODANDO YA, CLARO.
                if (!rolling) {

                    rolling = true;

                    //ENSEÑAMOS EL DADO RODANDO.
                    imgDice.setImageResource(R.drawable.dice3droll);

                    //PAUSA PARA QUE LA IMAGEN SE ACTUALICE.
                    //(VER LA CLASE ROLL).
                    timer.schedule(new Roll(), 400);
                }
                break;

        }
    }

    //CUANDO ACABA ENVIA UN MENSAJE DE CALLBACK.
    private class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    //RECIBE UN MENSAJE DEL TIMER DE QUE SE PUEDE INICIAR.
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {

            //OBTENEMOS EL RESULTADO DEL DADO
            //NEXTINT VA DE 0 A 5, POR TANTO, +1.
            switch (random.nextInt(6) + 1) {

                case 1:
                    //Mostramos la imagen correspondiente en cada uno de los 6
                    // casos posibles

                    imgDice.setImageResource(R.drawable.one);
                    break;

                case 2:
                    imgDice.setImageResource(R.drawable.two);
                    break;

                case 3:
                    imgDice.setImageResource(R.drawable.three);
                    break;

                case 4:
                    imgDice.setImageResource(R.drawable.four);
                    break;

                case 5:
                    imgDice.setImageResource(R.drawable.five);
                    break;

                case 6:
                    imgDice.setImageResource(R.drawable.six);
                    break;

                default:
            }
            rolling = false;  //EL USUARIO PUEDE VOLVER A TIRAR PORQUE HA ACABADO LA TIRADA.
            return true;
        }
    };
}
