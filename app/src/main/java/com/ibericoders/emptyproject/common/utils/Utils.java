package com.ibericoders.emptyproject.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.LongSparseArray;

import com.ibericoders.emptyproject.common.constants.Constants;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Desc: Clase con métodos utilizados en toda la aplicación.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class Utils {

    private static DecimalFormat sDecimalFormat;

    /**
     * Formatos para los decimales.
     */
    public static final String STRING_FORMAT_WITHOUT_DECIMALS = "%.0f";
    public static final String STRING_FORMAT_TWO_DECIMAL = "%.2f";


    /**
     * Formatos para las fechas.
     */
    public static final String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
    public static final String DATE_FORMAT_ddMM = "dd/MM";

    public static double[] insertTwoArrays (double[] firstArray, double[] secondArray){

        double[] twoArrays = new double[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0 , twoArrays, 0 , firstArray.length);
        System.arraycopy(secondArray, 0 , twoArrays, firstArray.length, secondArray.length);

        return twoArrays;
    }

    /**
     * Método que nos devuelve la fecha en objeto String, con el formato adecuado para la
     * visualización del usuario.
     *
     * @param dateInMillis
     * @param format Formato de la fecha. Si es null se tomará por defecto: "dd-MM-yyyy"
     * @return La fecha y hora en String con el formato .
     *
     */
    public static String getDateString (long dateInMillis, String format) {

        // Hallamos la fecha del hito.
        Calendar c = Calendar.getInstance();

        if (format == null) {

            format = "dd-MM-yyyy";

        }

        java.text.DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        c.setTimeInMillis(dateInMillis);

        // Devolvemos dicha fecha en un String con el formato requerido.
        return formatter.format(c.getTime());
    }


    /**
     * Método que nos devuelve la fecha actual en objeto String, con el formato adecuado
     * para la visualización del usuario.
     *
     * @param format Formato de la fecha. Si es null se tomará por defecto: "dd-MM-yyyy"
     * @return La fecha y hora en String con el formato .
     *
     */
    public static String getCurrentDateString(String format) {

        return getDateString(getCurrentTimeInMillis(), format);

    }


    /**
     * Método para obtener la fecha actual en milisegundos.
     *
     */
    public static long getCurrentTimeInMillis() {

        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    /**
     * Método que recupera la versión de la aplicación (atributo versionName).
     *
     * @return Devuelve el versionName de la aplicación, en formato String; ó un String
     * vacío si no se ha podido obtener.
     */
    public static String getAppVersion(Context c) {

        PackageInfo pInfo = null;
        try {
            pInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {}

        if(pInfo != null) {
            return pInfo.versionName;
        }
        return Constants.EMPTY_FIELD;
    }


    /**
     * Método para, a partir de una fecha pasada por argumento (milisegundos), obtener
     * el mes correspondiente (index-0).
     *
     * @param date
     */
    public static int getMonth(long date) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.get(Calendar.MONTH);
    }


    /**
     * Método para parsear una fecha en String y convertirla a milisegundos. Sólo sirve para el
     * formato yyyy-MM-dd HH:mm:ss.
     *
     * @param dateString  Fecha  en formato String.
     * @param format  Formato en el que nos llega dateString. Si es null, se utiliza  "yyyy-MM-dd HH:mm:ss".
     * @return Devuelve la fecha en milisegundos: 0l si no se ha podido parser correctamente.
     */
    public static long getMillis (String dateString, String format) {

        if(dateString == null) {
            return 0l;
        }

        if(format == null){
            format = "yyyy-MM-dd HH:mm:ss";
        }

        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

        try {

            return formatter.parse(dateString).getTime();

        } catch (ParseException e) {

            return 0l;
        }
    }


    /**
     * Método que convierte un LongSparseArray en una lista de los objetos que contiene.
     *
     * @param sparseArray
     * @param <C>
     * @return
     */
    public static <C> List<C> asList(LongSparseArray<C> sparseArray) {

        if (sparseArray == null) return null;

        List<C> arrayList = new ArrayList<C>(sparseArray.size());

        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add(sparseArray.valueAt(i));
        }
        return arrayList;
    }


    /**
     * Método que comprueba si el día indicado por la fecha 'date' es anterior
     * ó igual al día indicado por 'currentDate'.
     *
     * @param currentDate
     * @param date
     * @return Devuelve 'true' en caso de que el día indicado por 'date' sea anterior
     * ó igual al día indicado por 'currentDate'.
     */
    public static boolean isTheSameOrPreviousDay(long currentDate, long date) {

        if(date < currentDate) {
            return true;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(currentDate);
        cal2.setTimeInMillis(date);
        /*
         * Si ya hemos comparado las fechas en milisegundos, el único caso restante
         * sería que 'date' fuera mayor que 'currentDate' pero sí fuera el mismo día.
         */
        if(cal2.get(Calendar.DAY_OF_YEAR) == cal1.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }


    /**
     * Método para obtener el límite inferior de una semana a partir de una fecha pasada como
     * argumento en milisegindos. Es decir, dada una fecha en milisegundos, se
     * obtendrá la fecha del lunes a las 00:00 horas de esa semana.
     *
     * @param dateMillis  Fecha en milisegundos.
     * @return Devuelve la fecha del límite inferior de esa semana, en formato milisegundos.
     */
    private static long getLowerLimitOfWeek (long dateMillis) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateMillis);

        //c.add(Calendar.DAY_OF_WEEK, -c.get(Calendar.DAY_OF_WEEK));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTimeInMillis();
    }


    /**
     * Método para obtener el límite superior de una semana a partir de una fecha pasada como
     * argumento en milisegindos. Es decir, dada una fecha en milisegundos, se
     * obtendrá la fecha del domingo a las 23:59 horas de esa semana.
     *
     * @param dateMillis  Fecha en milisegundos.
     * @return Devuelve la fecha del límite superior de esa semana, en formato milisegundos.
     */
    private static long getUpperLimitOfWeek (long dateMillis) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateMillis);

        //c.add(Calendar.DAY_OF_WEEK, -c.get(Calendar.DAY_OF_WEEK));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTimeInMillis() + Constants.Millis_In_A_Week - 1000l;
    }


    /**
     * Método para poner en mayúscula la primera letra de una cadena.
     *
     * @param s  Palabra cuya primera letra se quiere poner en mayúsculas.
     * @return True si es válida y False si no lo es.
     */
    public static String capitalizeFirstLetter (String s) {

        if (!TextUtils.isEmpty(s)) {

            String firstLetter = ((Character)Character.toUpperCase(s.charAt(0))).toString();
            return firstLetter.concat(s.substring(1));
        }
        return s;
    }


    /**
     * Método para obtener el límite inferior de un mes a partir de un mes (índice)
     * y año (valor) pasados como argumento. Es decir, dado mes y año, se obtendrá
     * la fecha del primer día de ese mes a las 00:00 horas.
     *
     * @param year Valor.
     * @param month  Índice (0-index).
     * @return Devuelve la fecha del límite inferior de ese año y mes, en formato milisegundos.
     */
    private static long getLowerLimitOfMonth (int year, int month) {

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTimeInMillis();
    }


    /**
     * Método para obtener el límite superior de un mes a partir de un mes (índice)
     * y año (valor) pasados como argumento. Es decir, dado mes y año, se obtendrá
     * la fecha del último día de ese mes a las 23:59 horas.
     *
     * @param year Valor.
     * @param month  Índice (0-index).
     * @return Devuelve la fecha del límite superior de ese año y mes, en formato milisegundos.
     */
    private static long getUpperLimitOfMonth (int year, int month) {

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTimeInMillis();
    }


    /**
     * Método que nos devuelve los límites inferior y superior del mes más reciente
     * dado por el índice del mismo (0-index). Es decir, dado un mes del año, calcularemos
     * las fechas del primer y último día de dicho mes, teniendo en cuenta que se
     * tratará del mes de este año ó el anterior según lo hayamos superado ya ó no.
     *
     * @param month
     * @return Devuelve los límites en formato long[]. El primer elemento [0] representa
     * la fecha en milisegundos del límite inferior y el segundo elemento [1] representa
     * la fecha en milisegundos del limite superior.
     */
    public static long[] getLimitsOfLastRecentMonth(int month) {

        Calendar cNow = Calendar.getInstance();
        cNow.setTimeInMillis(getCurrentTimeInMillis());

        int monthNow = cNow.get(Calendar.MONTH);


        /*
         * Si el mes que buscamos es mayor al actual, se tratará del mes
         * del año anterior.
         */
        int year = (month > monthNow) ? cNow.get(Calendar.YEAR) - 1 : cNow.get(Calendar.YEAR);

        long lowerDate = getLowerLimitOfMonth(year, month);
        long upperDate = getUpperLimitOfMonth(year, month);

        return new long[]{lowerDate, upperDate};
    }


    public static String getMonthString(int month) {

        Calendar c = Calendar.getInstance();

        c.set(Calendar.MONTH, month);

        return getDateString(c.getTimeInMillis(), "MMMM yyyy");

    }


    public static float convertDpToPixel(float dp, Context context){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static float convertPixelsToDp(float px, Context context){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
