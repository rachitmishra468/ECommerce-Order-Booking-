package com.uk.restaurant.GreenVich.activity_fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */
public class OrderReviewPage extends AppCompatActivity implements View.OnClickListener{

    public static TextView txtResponse;
    LinearLayout llHome,llBack;

    TextView txtCategoryName,textlog;
    ImageView logimg;
    public static TextView txtQuantity;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    TextView login;
    ImageView imageView;
    private AlertDialog myDialog;
    private View alertView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review_page);

        //Code to Hide Action Bar
        getSupportActionBar().hide();

        //Code to reference XML Widgets
        init();
        if(Constants.isUserLogIn){
            login.setText("Logout");



        }
        else {
            login.setText("Login");

        }
        //Setting Activity Name
        txtCategoryName.setText("Order Review");
        //Register Click Listener of Widgets
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);

        txtResponse=(TextView)findViewById(R.id.txt_response);
        //Hot Order api and get Response from ther


        if(getIntent().getExtras().get("PaymentType").toString().equals("ONLINE")){
            //Getting Intent
            Intent intent = getIntent();

            try {
                JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

                //Displaying payment details
                showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
//                llOrderDetails.setVisibility(View.VISIBLE);
//                llTransaction.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        else{


            VolleyMethods vm=new VolleyMethods(this);
            Constants.paymentStatus = "offline";
            Constants.Transaction_id = "";
            vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),getIntent().getExtras().get("Time").toString());
        }





        /*VolleyMethods vm=new VolleyMethods(this);
        Constants.paymentStatus = "offline";
        Constants.Transaction_id = "";
        vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),getIntent().getExtras().get("Time").toString());*/
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }



    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {


        if(jsonDetails.getString("state").equals("approved")){
            Constants.paymentStatus = "approved";
            Constants.Transaction_id = jsonDetails.getString("id");
            VolleyMethods vm=new VolleyMethods(this);
            vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),getIntent().getExtras().get("Time").toString());
        }
        else {
            Constants.paymentStatus = "offline";
            Constants.Transaction_id = "";
            VolleyMethods vm=new VolleyMethods(this);
            vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),getIntent().getExtras().get("Time").toString());
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();
        if(Constants.isUserLogIn){
            login.setText("Logout");
            }
        else {
            login.setText("Login");

        }
    }
    public void init(){
        llBack=(LinearLayout)findViewById(R.id.ll_back);
        llHome=(LinearLayout)findViewById(R.id.ll_home);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:{
                finish();
            }break;

            case R.id.ll_home:{
                Intent in=new Intent(this,HomeActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }break;

            case R.id.ll_menu: {
                Intent in = new Intent(this, Menu.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_about_us: {
                Intent in = new Intent(this, AboutUs.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_contact_us: {
                Intent in = new Intent(this, MapsActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_find_on_map: {
                if(Constants.isUserLogIn){
                    login.setText("Login");
                    SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Address",  "");
                    editor.commit();

                    Constants.isUserLogIn=false;
                    Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent in = new Intent(this, LogIn.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(in);
                    Constants.isUserLogIn1=true;
                }
                /*Intent in = new Intent(this, FindOnMapActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);*/
            }break;

            case R.id.ll_check_out: {
                //Do Not Do Nothing It is ALready On This Page
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;
        }
    }

    public  void popuptext1(String Messges){

        LayoutInflater inflater = LayoutInflater.from(OrderReviewPage.this);
        View view = inflater.inflate(R.layout.dialog_layout_resonse, null);
        TextView textView=(TextView)view.findViewById(R.id.response);
        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertView);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.app_icon);
        builder.setView(view);
        builder.setCancelable(false);
        myDialog=builder.create();
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Intent intent=new Intent(OrderReviewPage.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        builder.show();

    }

}
