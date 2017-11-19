package com.ibericoders.ibinternal.app.managers.vote;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyValueFormatter implements IValueFormatter {

    // Clase que se utiliza para personalizar el formato en el que queremos que aparezcan
    //las estadisticas del gráfico de votaciones.

    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("#"); // No usamos porcentajes, queremos que aparezca el numero
        //de votos en terminos absolutos.
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // devolvemos un String con el formato que queremos utilizar
        return mFormat.format(value) + " votos";
    }
}