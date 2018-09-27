package com.uk.restaurant.GreenVich.utill;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uk.restaurant.GreenVich.activity_fragments.CheckOut;
import com.uk.restaurant.GreenVich.activity_fragments.Menu;
import com.uk.restaurant.GreenVich.adapter.CheckOutAdapter;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;

/**
 * Created by Developer on 07-06-2016.
 */
public class AsyncTaskDataBaseOperation extends AsyncTask<Void, Void, Void> {

    Context context;
    ProgressDialog pDialog;

    public AsyncTaskDataBaseOperation(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        DataBaseHelper dbHelper=new DataBaseHelper(context);
        dbHelper.deleteOrder();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        DataBaseHelper dbHelper=new DataBaseHelper(context);
        CheckOutAdapter checkOutAdapter = new CheckOutAdapter(context, dbHelper.getOrder());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        CheckOut.orderCatRecycler.setLayoutManager(mLayoutManager);
        CheckOut.orderCatRecycler.setItemAnimator(new DefaultItemAnimator());
        CheckOut.orderCatRecycler.setAdapter(checkOutAdapter);

        Bundle bundle=dbHelper.getItemQuantity();
        Constants cons = new Constants();
        CheckOut.totalPrice.setText("" + bundle.getDouble("TotalPrice"));
       /* ((Menu)context).txtQuantity.setText(cons.getQuantity(context));
        ((Menu)context).price.setText("£" + bundle.getString("TotalPrice"));*/
        Menu.ll_check.setVisibility(View.GONE);




        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
