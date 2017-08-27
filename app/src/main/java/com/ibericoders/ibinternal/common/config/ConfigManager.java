package com.ibericoders.ibinternal.common.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.ibericoders.ibinternal.common.constants.Constants;

import java.util.List;

/**
 * Desc: Gestor para la configuración de la aplicación.
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class ConfigManager {

    /*
     * Atributos de configuración. Serán privados porque los recuperaremos de presistencia
     * si no tienen valor.
     */
    private int idLanguage;
    private int enviromentType;


    /*
     * Mantenemos el gestor de persistencia inicializado para evitar hacerlo en múltiples ocasiones.
     */
    private SharedPreferences mSharedPrefs;


    /*
     * Constantes.
     */
    private static final String PREFS_KEY_IDLANGUAGE = "prefs_key_idlanguage";
    private static final String PREFS_KEY_ENVIROMENT_TYPE ="prefs_key_enviroment_type";


    private SharedPreferences getSharedPreferences(Context context) {

        if(this.mSharedPrefs == null) {
            this.mSharedPrefs = context.getSharedPreferences(Constants.PREFS_NAME_CONFIG, Context.MODE_PRIVATE);
        }
        return this.mSharedPrefs;
    }

    public int getIdLanguage(Context c) {

        if(this.idLanguage <= 0){
            this.idLanguage = getSharedPreferences(c).getInt(PREFS_KEY_IDLANGUAGE, Constants.LANGUAGE_ID_SPANISH);
        }

        return this.idLanguage;
    }


    public String getLanguageCode(Context c) {

        if(this.idLanguage <= 0){
            this.idLanguage = getSharedPreferences(c).getInt(PREFS_KEY_IDLANGUAGE, Constants.LANGUAGE_ID_SPANISH);
        }
        switch(idLanguage){
            case 3:
                return "es";
        }


        return "es";
    }

    public int getEnviromentType(Context c){

        return getSharedPreferences(c).getInt(PREFS_KEY_ENVIROMENT_TYPE, enviromentType);
    }

    public void setIdLanguage(Context c, int id_language) {

        this.idLanguage = id_language;
        SharedPreferences.Editor editor = getSharedPreferences(c).edit().putInt(PREFS_KEY_IDLANGUAGE, id_language);
        editor.apply();
    }

    public void setEnviromentType(Context c, int enviromentType){
        this.enviromentType = enviromentType;
        SharedPreferences.Editor editor = getSharedPreferences(c).edit().putInt(PREFS_KEY_ENVIROMENT_TYPE, enviromentType);
        editor.apply();
    }
}
