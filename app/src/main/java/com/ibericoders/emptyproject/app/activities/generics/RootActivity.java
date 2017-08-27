package com.ibericoders.emptyproject.app.activities.generics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ibericoders.emptyproject.R;
import com.ibericoders.emptyproject.content.data.Domain;

/**
 * Desc: Actividad genérica y abstracta de la que deben heredar todas las demás, salvo
 * algún caso justificado e indicado.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public abstract class RootActivity extends AppCompatActivity {

    /*
     * Instancia del dominio de la aplicación.
     */
    protected Domain mDomain;

    /*
     * Atributos de la UI.
     */
    private View vLoading;
    private View vError;
    private TextView tvErrorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mDomain = Domain.getDomain();
    }


    public Domain getDomain() {

        if(this.mDomain == null) {
            this.mDomain = Domain.getDomain();
        }
        return this.mDomain;
    }


    public void setLoadingEnable(boolean enableLoading){

        if(this.vLoading == null){

            this.vLoading = this.findViewById(R.id.include_g_loading);

            if(this.vLoading == null)
            {
                return;
            }
        }

        if(enableLoading){
            this.vLoading.setVisibility(View.VISIBLE);
        }
        else{
            this.vLoading.setVisibility(View.GONE);
        }

    }

    public void setErrorScreen(boolean enableError, String errorMessage){

        if (this.vError == null){

            this.vError = this.findViewById(R.id.include_error_screen);
            this.tvErrorMessage = (TextView) this.findViewById(R.id.error_info_tv);

            if(this.vError == null){
                return;
            }
        }

        if(enableError){
            this.vError.setVisibility(View.VISIBLE);
            this.tvErrorMessage.setText(errorMessage);

        }else{
            this.vError.setVisibility(View.GONE);
        }
    }



    protected abstract void initAttrs();

    protected abstract void inflateView();

    protected abstract void fillView();

    protected abstract void initListeners();
}
