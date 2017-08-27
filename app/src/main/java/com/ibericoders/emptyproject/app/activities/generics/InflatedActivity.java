package com.ibericoders.emptyproject.app.activities.generics;

import butterknife.ButterKnife;

/**
 * Desc: Actividad genérica y abstracta. Concentra cierto código relativo al inflado
 * de las vistas vía la librería Butter Knife, de modo que no necesitemos repetir
 * código en todas las actividades que la implemente.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public abstract class InflatedActivity extends WithActionBarActivity {

    /*
     * Léeme.
     *
     * En esta clase, incorporaremos atributos y métodos relativos a la barra
     * de navegación de la aplicación.
     */

    @Override
    protected void inflateView() {

        ButterKnife.bind(this);
    }
}
