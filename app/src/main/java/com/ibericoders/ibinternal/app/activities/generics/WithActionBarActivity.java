package com.ibericoders.ibinternal.app.activities.generics;

import android.support.v7.widget.Toolbar;

import com.ibericoders.ibinternal.R;

/**
 * Desc: Actividad genérica y abstracta de la que deben heredar todas las que posean
 * barra de navegación, salvo algún caso justificado e indicado.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public abstract class WithActionBarActivity extends RootActivity{

    /*
     * Léeme.
     *
     * En esta clase, incorporaremos atributos y métodos relativos a la barra
     * de navegación de la aplicación.
     */

    protected Toolbar mToolbar;


    /**
     * Método para establecer y configurar la app bar de tipo Toolbar. Actualmente tiene
     * declaración como 'protected'
     *
     * @param toolbar Safe-null. Se comprueba en el propio método si el argumento es nulo ó no.
     */
    protected void setupToolbar(Toolbar toolbar)
    {
        if (toolbar == null)
        {
            // Le damos una oportunidad de recuperación por si el toolbar está en la actividad.
            toolbar = (Toolbar) this.findViewById(R.id.toolbar_tb);
        }
        if (toolbar != null)
        {
            this.mToolbar = toolbar;
            this.setSupportActionBar(toolbar);

        }

        if (getSupportActionBar() != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            this.getSupportActionBar().setDisplayShowHomeEnabled(false);
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
