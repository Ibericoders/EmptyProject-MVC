package com.ibericoders.ibinternal.app.adapters.expenses;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.common.constants.Constants;
import com.ibericoders.ibinternal.content.model.expenses.Expense;

import java.util.List;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class ExpensesListAdapter extends BaseAdapter {

    private List<Expense> mExpensesList;

    private LayoutInflater mLayoutInflater;

    private Context mContext;


    public ExpensesListAdapter(Context ctx, List<Expense> mExpensesList){

        this.mExpensesList = mExpensesList;
        this.mContext = ctx;

        mLayoutInflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {

        return mExpensesList.size();
    }

    @Override
    public Expense getItem(int position) {

        return mExpensesList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Expense expense = mExpensesList.get(position);

        //Generamos un objeto View a partir de la plantilla creada para la fila.
        convertView = mLayoutInflater.inflate(R.layout.row_expenseslist, null);

        //Nombre Expense
        TextView tvName = (TextView) convertView.findViewById(R.id.expenseslistrow_expensename_tv);

        tvName.setText(expense.getName());

        //Descripcion Expense
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.expenseslistrow_expensedescription_tv);

        tvDescripcion.setText(expense.getDescription());

        //Cantidad Expense
        TextView tvCantidad = (TextView) convertView.findViewById(R.id.expenseslistrow_expenseamount_tv);

        tvCantidad.setText(String.valueOf(expense.getAmount()));

        //Fecha Expense
        TextView tvFecha = (TextView) convertView.findViewById(R.id.expenseslistrow_expensedate_tv);

        tvFecha.setText(expense.getDate());

        //Categoría Expense
        ImageView ivCategory = (ImageView) convertView.findViewById(R.id.expenseslistrow_categoryicon_iv);

        switch(expense.getCategory()){

            case Constants.CATEGORY_COMMON:

                ivCategory.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_expensescategory_common));
                break;

            case Constants.CATEGORY_FOOD_DRINK:

                ivCategory.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_expensescategory_foodanddrink));
                break;

            case Constants.CATEGORY_EDUCATION:

                ivCategory.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_expensescategory_education));
                break;

            case Constants.CATEGORY_OTHER:

                ivCategory.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_expensescategory_other));
                break;

            case Constants.CATEGORY_EXTRA_NONPLANNED:

                ivCategory.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_expensescategory_extranonplanned));
                break;
        }

        //Devolver el view de la fila
        return convertView;
    }
}
