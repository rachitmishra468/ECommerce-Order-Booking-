package com.uk.restaurant.GreenVich.activity_fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.PayPalConfig;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */
public class PayMentActivity extends AppCompatActivity {

    Button btnCancel,btnConfirm;
    public static TextView response;
    TextView amount,deliveryType,paymentMethod,Discount;
    public static TextView total_amount,delivery_charge,discounttext;
    DataBaseHelper dbHelper;
    public static String imei,dType;
    ProgressDialog pDialog;
    private String paymentAmount;
    String discount,deliveryCharge;
    public static final int PAYPAL_REQUEST_CODE = 123;



 /*   private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID)
            .acceptCreditCards(false)
            .merchantName("AlsPlaice");
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
       /* if(Constants.mode){
            config = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                    .clientId(Constants.Paypal_id)
                    .acceptCreditCards(false)
                    .merchantName("Flamin Chicken");
        }else {
            config = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId(Constants.Paypal_id)
                    .acceptCreditCards(false)
                    .merchantName("Flamin Chicken");
        }
*/
       /* init();
        defineWidget();
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);*/
    }

  /*  @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }*/

    private void defineWidget() {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Constants.imei=new String("");
        Constants.imei = tm.getDeviceId().toString();
        dType = getIntent().getExtras().getString("DeliveryType");
        Bundle total = dbHelper.getItemQuantity();
        amount.setText("£ " + total.getString("TotalPrice"));
        discounttext.setText(Constants.dis+"%");
        if(getIntent().getExtras().get("DeliveryType").toString().equals("C")){
            String t=Constants.charges;
            delivery_charge.setText("£ "+0);
            Float fullamount=(Float.parseFloat(total.getString("TotalPrice"))*(Integer.parseInt(Constants.dis)))/100;
            Float te=Float.parseFloat(total.getString("TotalPrice"))-fullamount;
            //total_amount.setText("£ " + total.getString("TotalPrice"));
            total_amount.setText("£ " + String.format("%.2f",te));
            Constants.totalAmount=new String();
            Constants.totalAmount=String.valueOf(String.format("%.2f",te));

        }else{
            String to=Constants.charges.toString();
            try {
                float f = Float.parseFloat(String.valueOf(to));
                delivery_charge.setText("£ " + String.format("%.2f", f));
                Float fullamount = (Float.parseFloat(total.getString("TotalPrice")) * (Integer.parseInt(Constants.dis))) / 100;
                Float t = Float.parseFloat(total.getString("TotalPrice")) - fullamount + f;
                total_amount.setText("£ " + String.format("%.2f", t));
                Constants.totalAmount = new String();
                Constants.totalAmount = String.valueOf(String.format("%.2f", t));
            }
            catch (Exception e){
                System.err.println("NumberFormatException: " + e.getMessage());

            }
        }


        if(getIntent().getExtras().getString("DeliveryType").equals("D"))
            deliveryType.setText("Delivery");
        else
            deliveryType.setText("Collect");
        paymentMethod.setText("ONLINE");
    }

    /*private void getPayment() {


        String t=Constants.totalAmount;
        paymentAmount =  Constants.totalAmount;

        PayPalPayment payment = new PayPalPayment((new BigDecimal(String.valueOf(paymentAmount))), "GBP", "AlsPlaice",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);


        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);


        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
*/
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, OrderReviewPage.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString())
                                .putExtra("PaymentType", "ONLINE")
                                .putExtra("Time", getIntent().getExtras().get("Time").toString())
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {


                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }*/

    //Method to reference XML widgets
    public void init(){
        dbHelper = new DataBaseHelper(this);
        btnCancel=(Button)findViewById(R.id.btn_cancel_payment);
        btnConfirm=(Button)findViewById(R.id.btn_confirm_payment);
        amount = (TextView) findViewById(R.id.paypal_amount);
        discounttext = (TextView) findViewById(R.id.paypal_discount);
        delivery_charge = (TextView) findViewById(R.id.paypal_delivery_charge);
        total_amount = (TextView) findViewById(R.id.paypal_total_amount);
        deliveryType = (TextView) findViewById(R.id.paypal_delivery_type);
        paymentMethod = (TextView) findViewById(R.id.paypal_payment_method);
        response = (TextView) findViewById(R.id.response);
        Discount=(TextView)findViewById(R.id.discount);
        discounttext.setText(Constants.dis+"%");
    }

    //
   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_payment:{
                this.finish();
            }break;

            case R.id.btn_confirm_payment:{
                //Proceed to Payment.
                getPayment();
            }
        }
    }*/
    /*public void deliveryCharge() {
        // Tag used to cancel the request
        final String TAG = "AsyncTDC:deliveryCharge";
        String url = Constants.ShippingAPI;
        Constants.totalAmount = new String("");
        pDialog = new ProgressDialog(PayMentActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
//        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    //  discount = jsonObject.getString("discount");
                    //  Constants.disCount = discount;
                    deliveryCharge = jsonObject.getString("shipping_cost");
                    PayMentActivity.discounttext.setText(discount +"%");
                    if(PayMentActivity.dType.equals("D"))
                    {  pDialog.dismiss();
                        String t=Double.toString(((Double.parseDouble(dbHelper.getItemQuantity().getString("TotalPrice"))*(100-Integer.parseInt(discount)))/100)+Double.parseDouble(deliveryCharge));


                        Constants.totalAmount = Double.toString(((Double.parseDouble(dbHelper.getItemQuantity().getString("TotalPrice"))*(100-Integer.parseInt(discount)))/100)+Double.parseDouble(deliveryCharge));
                        double d = Double.parseDouble(Constants.totalAmount);
                        double d1 = Double.parseDouble(Constants.charges)+d;
                        Constants.totalAmount=String.valueOf(d1);

                        float f = Float.parseFloat(String.valueOf(d1));
                        int value = (int)f;
                        int e=Integer.parseInt(Constants.dis);
                        int main=value*e;
                        int net=main/100;

                        //PayMentActivity.delivery_charge.setText("£" + jsonObject.getString("shipping_cost").toString());
                        PayMentActivity.total_amount.setText("£ " + net);


                        // PayMentActivity.total_amount.setText("£ " + String.format("%.2f",f ));
                    }
                    else{
                        pDialog.dismiss();
                        Constants.totalAmount = Double.toString(((Double.parseDouble(dbHelper.getItemQuantity().getString("TotalPrice"))*(100-Integer.parseInt(discount)))/100));
                        float f = Float.parseFloat(String.valueOf(Constants.totalAmount));
                        int value = (int)f;
                        int e=Integer.parseInt(Constants.dis);
                        int main=value*e;
                        int net=main/100;
                        /// hhhhh
                        //PayMentActivity.delivery_charge.setText("£" + jsonObject.getString("shipping_cost").toString());
                        PayMentActivity.total_amount.setText("£ " + net);
                        PayMentActivity.total_amount.setText("£ " + Constants.totalAmount);
                    }


                    pDialog.dismiss();
                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                OrderInfo order = new OrderInfo(PayMentActivity.this);
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("Address", order.getAddress().toString());
                // params.put("mobile_id",Constants.imei);
                // params.put("email_id",Constants.addressMap.get("Email_id"));
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, TAG);
    }*/
    public  void Mode(){

    }
}
