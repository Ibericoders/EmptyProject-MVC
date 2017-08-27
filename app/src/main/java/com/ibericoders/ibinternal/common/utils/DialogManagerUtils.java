package com.ibericoders.ibinternal.common.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

import com.ibericoders.ibinternal.R;

/**
 * Desc: Clase para gestionar la creación/destrucción de cuadros de diálogo
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class DialogManagerUtils {

    /**
     * Current alert dialog
     */
    private static AlertDialog currentAlertDialog = null;

    /**
     * Current dialog
     */
    private static Dialog currentDialog = null;

    /**
     * Flag
     */
    private static boolean hideProgressDialog = false;



    public static void getAlertDialog(Context context, String title, String mensaje, String buttonTitle, DialogInterface.OnClickListener buttonListener) {

        // Ocultamos algun dialogo anterior
        DialogManagerUtils.hideCurrentDialog();

        // Configuramos el dialogo
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(mensaje);
        alertDialog.setCancelable(false);

        if (buttonTitle == null) {
            buttonTitle = context.getString(R.string.btn_close);
        }

        if (buttonListener != null) {
            alertDialog.setPositiveButton(buttonTitle, buttonListener);
        } else {

            alertDialog.setPositiveButton(buttonTitle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    hideCurrentDialog();
                }
            });
        }
        // Mostramos la alerta en el hilo principal
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {

            @Override
            public void run() {
                // Mostramos la alerta
                if (alertDialog != null)
                    currentAlertDialog = alertDialog.show();
            }
        });
    }


    public static void hideCurrentDialog() {

        try {

            hideProgressDialog = true;

            if (currentAlertDialog != null && currentAlertDialog.isShowing()) {
                currentAlertDialog.dismiss();
                currentAlertDialog.hide();
            }

            currentAlertDialog = null;


            if (currentDialog != null && currentDialog.isShowing()) {
                currentDialog.dismiss();
            }

            currentDialog = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            currentDialog = null;
            currentAlertDialog = null;
        }
    }
}
