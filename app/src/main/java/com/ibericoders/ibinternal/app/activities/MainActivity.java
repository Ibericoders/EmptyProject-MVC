package com.ibericoders.ibinternal.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.dices.DicesActivity;
import com.ibericoders.ibinternal.app.activities.expenses.ExpensesActivity;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.activities.records.RecordsActivity;
import com.ibericoders.ibinternal.app.activities.vote.ConfigVotingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * Desc: Actividad principal de acceso a la aplicacion.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class MainActivity extends AppCompatActivity {


    /*
     * Atributos de la UI
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.act_mainact);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.frame_dices, R.id.frame_votes, R.id.frame_record, R.id.frame_expenses})
    public void onClick(View view){

        switch (view.getId()){

            case R.id.frame_votes:

                Answers.getInstance()
                        .logCustom(new CustomEvent("Module access from Main")
                                .putCustomAttribute("Module", "Votaciones"));

                Intent intent_voting=new Intent(this, ConfigVotingActivity.class);
                this.startActivity(intent_voting);
                break;

            case R.id.frame_dices:

                Answers.getInstance()
                        .logCustom(new CustomEvent("Module access from Main")
                                .putCustomAttribute("Module", "Dados"));

                Intent intent_dices=new Intent(this, DicesActivity.class);
                this.startActivity(intent_dices);
                break;

            case R.id.frame_expenses:

                Answers.getInstance()
                        .logCustom(new CustomEvent("Module access from Main")
                                .putCustomAttribute("Module", "Gastos"));

                Intent intent_expenses=new Intent(this, ExpensesActivity.class);
                this.startActivity(intent_expenses);
                break;

            case R.id.frame_record:

                Answers.getInstance()
                        .logCustom(new CustomEvent("Module access from Main")
                                .putCustomAttribute("Module", "Actas"));

                Intent intent_act=new Intent(this, RecordsActivity.class);
                this.startActivity(intent_act);
                break;
        }
    }
}
