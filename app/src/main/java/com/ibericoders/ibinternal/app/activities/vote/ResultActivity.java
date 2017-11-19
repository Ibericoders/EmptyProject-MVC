package com.ibericoders.ibinternal.app.activities.vote;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.managers.vote.MyValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultActivity extends InflatedActivity {

    /*
     * Atributos de UI.
     */
    @BindView(R.id.pieChart) PieChart pieChart;

    /*
     * Atributos de negocio
     */
    private float votesInA;
    private float votesInB;
    private float votesInC;
    private float votesInD;
    private String topic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initAttrs();
        inflateView();
        fillView();
        initListeners();
    }

    @Override
    protected void initAttrs() {

        //Llamamos a través de la clase Bundle a los datos de la votación

        if (this.getIntent() != null) {

            votesInA = this.getIntent().getIntExtra("votesA", 0);
            votesInB = this.getIntent().getIntExtra("votesB", 0);
            votesInC = this.getIntent().getIntExtra("votesC", 0);
            votesInD = this.getIntent().getIntExtra("votesD", 0);
            topic = this.getIntent().getStringExtra("topic");
        }

        //creamos un array con los resultados para poder utilizarlo en la tabla.
        float[] resultVotes = new float[4];
        resultVotes[0] = votesInA;
        resultVotes[1] = votesInB;
        resultVotes[2] = votesInC;
        resultVotes[3] = votesInD;

    }

    @Override
    protected void fillView() {

    }

    @Override
    protected void initListeners() {}


    private void formatData(){
        List<PieEntry> entries = new ArrayList<>();
        // transformamos nuestros datos en PieEntry.
        entries.add(new PieEntry(votesInA, "Opción A / SÍ"));
        entries.add(new PieEntry(votesInB, "Opción B/ NO"));
        entries.add(new PieEntry(votesInC, "Opción C"));
        entries.add(new PieEntry(votesInD, "Opción D"));

        PieDataSet set = new PieDataSet(entries, "SetPieChart");
        set.setSliceSpace(2f);
        set.setSelectionShift(7f);
        // seteamos los colores del grafico y el color del texto, entre otros parámetros.
        set.setColors(new int[]{R.color.colorPrimaryLight, R.color.colorButtonNo, R.color.colorButtonC, R.color.colorButtonD}, this);

        PieData data = new PieData(set);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        configChart();
    }

    private void configChart(){

        pieChart.setBackgroundColor(Color.TRANSPARENT);
        pieChart.setDrawEntryLabels(true);
        pieChart.setUsePercentValues(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.setDescription(null);
        pieChart.setCenterText(topic);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setMaxAngle(180f);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.setEntryLabelTextSize(18);
        pieChart.invalidate();
    }

    @OnClick(R.id.buttonToShare)
    public void share(){
        pieChart.saveToGallery(topic, 100);
        sendEmail();

    }

    /* @Override
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
    }*/

    //método para envíar el email al pulsar el botón de resultado
    //en principio, al ser una App interna, la dirección será predefinida
    //se plantea hacer un menú de opciones para poder configurar las preferencias.

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"ibericoders@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.setType("application/image");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Resultado de la votación '" + topic + "'");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "El resultado de la votación sobre el asunto '" + topic + "' se encuentra en su galería de imágenes, adjúntelo si lo desea.");
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/Result" + topic + ".jpeg"));

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ResultActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
