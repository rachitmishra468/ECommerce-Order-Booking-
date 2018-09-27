package com.uk.restaurant.GreenVich.activity_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class DeliveryDetailAddress extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmitAddress;
    RadioButton radioPayOnline;
    RadioButton radioCashOnDelivery;
    RadioGroup radioGroupPayMentOption;
    LinearLayout llHome,llBack,llOptionDelivery;
    EditText edtName, edtEmail, edtPhoneNo, edtHouse, edtStreet, edtCity, edtDeliveryNote, edtPostCode, edtState;
    TextView login;
    String Time;
    ImageView imageView;
    TextView txtQuantity,textaddress,collecttext  ,title_delivery,textlog;
    ImageView logimg;
    TextView txtCategoryName;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail_address);

        //Code to Hide Action Bar
        getSupportActionBar().hide();

        //Reference XML widgets
        init();
        if(Constants.isUserLogIn){
            login.setText("Logout");


        }
        else {
            login.setText("Login");

        }
        //Setting Activity Name
        txtCategoryName.setText("Check Out");
        Time=getIntent().getExtras().get("Time").toString();
        if(getIntent().getExtras().get("DeliveryType").toString().equals("C")){
            txtCategoryName.setText("Check Out");

            radioCashOnDelivery.setText("Cash On Collection");
            textaddress.setVisibility(View.GONE);
            llOptionDelivery.setVisibility(View.GONE);
        }else{
            //Do nothing
        }

        //Register Click listener of widgets
        btnSubmitAddress.setOnClickListener(this);
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);

        if(Constants.userData.equals("")){
            //Do Nothing User Data is Not available
        }else{
            //User Data is Available Fill Data to Edit Texts
            fillUserData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();
        txtQuantity.setText(cons.getQuantity(this));
        if(Constants.isUserLogIn){
            login.setText("Logout");


        }
        else {
            login.setText("Login");

        }
    }

    //Method to reference XML Widgets
    public void init() {

        edtPostCode = (EditText) findViewById(R.id.edt_pin_code_address_page);
        edtName = (EditText) findViewById(R.id.edt_full_name);
        edtEmail = (EditText) findViewById(R.id.edt_email_id);
        edtPhoneNo = (EditText) findViewById(R.id.edt_phone_no);
        edtHouse = (EditText) findViewById(R.id.edt_house);
        edtStreet = (EditText) findViewById(R.id.edt_street);
        edtCity = (EditText) findViewById(R.id.edt_city);
        edtState = (EditText) findViewById(R.id.edt_state);
        edtDeliveryNote = (EditText) findViewById(R.id.edt_delivery_note);
        radioPayOnline = (RadioButton) findViewById(R.id.radio_pay_online);
        radioCashOnDelivery = (RadioButton) findViewById(R.id.radio_cash_on_delivery);
        radioGroupPayMentOption = (RadioGroup) findViewById(R.id.radio_group_payment_option);
        btnSubmitAddress = (Button) findViewById(R.id.btn_submit_delivery_address);
        llBack=(LinearLayout)findViewById(R.id.ll_back);
        llHome=(LinearLayout)findViewById(R.id.ll_home);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        llOptionDelivery=(LinearLayout)findViewById(R.id.option_delivery);
        textaddress=(TextView)findViewById(R.id.textadd);
        collecttext=(TextView)findViewById(R.id.textdelevery);
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
        title_delivery=(TextView)findViewById(R.id.title_delivery);
        Typeface typeface= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf");
        collecttext.setTypeface(typeface);
        Typeface typeface1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf");
        title_delivery.setTypeface(typeface1);
    }

    //Click Listener of widgets
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_delivery_address: {
                int selectedRadioId = radioGroupPayMentOption.getCheckedRadioButtonId();

                switch (selectedRadioId) {
                    case R.id.radio_cash_on_delivery: {
                        //Cash On Delivery option is Selected
                        if (Constants.isUserLogIn) {
                            //Checking Dekievery info
                            if (checkDeliveryInfo()) {
                                Intent in = new Intent(this, OrderReviewPage.class);
                                in.putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString());
                                in.putExtra("PaymentType", "COD");
                                in.putExtra("Time", Time);
                                startActivity(in);
                                this.finish();
                            } else {
                                //Do nothing
                            }}else {
                            Intent in = new Intent(this, LogIn.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(in);
                            Constants.isUserLogIn1 = false;
                        }
                    }
                    break;

                    case R.id.radio_pay_online: {
                        if (checkDeliveryInfo()) {
                            Intent in = new Intent(DeliveryDetailAddress.this, PayMentActivity.class);
                            in.putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString());
                            in.putExtra("PaymentType", "ONLINE");
                            in.putExtra("Time", Time);
                            startActivity(in);
                        } else {
                            //Do nothing
                        }

                    }break;
                }
            }
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
             /*   Intent in = new Intent(this, FindOnMapActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);*/

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

    public boolean checkDeliveryInfo() {
        boolean result = false;

        if (edtName.getText().toString().equals("") || edtName.getText().toString().equals("Full name")) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
        } else {
            if (edtEmail.getText().toString().equals("") || edtEmail.getText().toString().equals("Email id")) {
                Toast.makeText(this, "Please enter mail", Toast.LENGTH_LONG).show();
            } else {
                if (edtPhoneNo.getText().toString().equals("") || edtPhoneNo.getText().toString().equals("Contact no")) {
                    Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
                } else {
                    if (edtHouse.getText().toString().equals("") || edtHouse.getText().toString().equals("House")) {
                        Toast.makeText(this, "Please enter house number", Toast.LENGTH_LONG).show();
                    } else {
                        if (edtStreet.getText().toString().equals("") || edtStreet.getText().toString().equals("Street")) {
                            Toast.makeText(this, "Please enter street", Toast.LENGTH_LONG).show();
                        } else {
                            if (edtCity.getText().toString().equals("") || edtCity.getText().toString().equals("City")) {
                                Toast.makeText(this, "Please enter city name", Toast.LENGTH_LONG).show();
                            } else {
                                if (edtPostCode.getText().toString().equals("") || edtPostCode.getText().toString().equals("Post code")) {
                                    Toast.makeText(this, "Please enter post code", Toast.LENGTH_LONG).show();
                                } else {


                                    if (edtPostCode.getText().toString().equals("") || edtPostCode.getText().toString().equals("Post code")) {
                                        Toast.makeText(this, "Please enter state name", Toast.LENGTH_LONG).show();
                                    } else {
                                        Constants.addressMap = new HashMap<>();
                                        Constants.addressMap.put("Full_name", edtName.getText().toString());
                                        Constants.addressMap.put("Email_id", edtEmail.getText().toString());
                                        Constants.addressMap.put("User_id", getUserId());
                                        Constants.addressMap.put("Contact_no", edtPhoneNo.getText().toString());
                                        Constants.addressMap.put("House", edtHouse.getText().toString());
                                        Constants.addressMap.put("Street", edtStreet.getText().toString());
                                        Constants.addressMap.put("City", edtCity.getText().toString());
                                        Constants.addressMap.put("Post_code", edtPostCode.getText().toString());
                                        Constants.addressMap.put("State", edtState.getText().toString());
                                        Constants.addressMap.put("Delivery_detail", edtDeliveryNote.getText().toString());
                                        result = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }


    //Method to fill user Data
    public void fillUserData(){
        try {
            JSONObject jObj=new JSONObject(Constants.userData);
            edtName.setText(jObj.getString("full_name"));
            edtEmail.setText(jObj.getString("mail_id"));
            edtPhoneNo.setText(jObj.getString("contact_no"));
            edtHouse.setText(jObj.getString("house_no"));
            edtStreet.setText(jObj.getString("street"));
            if (Constants.postal.equals("")) {
                edtPostCode.setText(jObj.getString("post"));
            } else {
                edtPostCode.setText(Constants.postal);
            }
            edtCity.setText(jObj.getString("city"));

            edtState.setText(jObj.getString("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Method to get User Id
    public String getUserId(){
        String userId=new String();
        try {
            JSONObject jObj=new JSONObject(Constants.userData);
            userId=jObj.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userId;
    }
}
