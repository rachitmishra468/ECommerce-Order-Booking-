package com.uk.restaurant.GreenVich.activity_fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.UtillClass;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import java.util.HashMap;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */
public class LogIn extends AppCompatActivity implements View.OnClickListener {
    EditText edtPassword, edtMailId;
    TextView btnLogIn;
    TextView btnRegister,btnResetPassword;
    LinearLayout llHome, llBack;
    TextView txtActionBarTitle;
    FragmentManager fragmentManager = getSupportFragmentManager();
    public  static  String otp="";
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Code to reference XML Widgets
        init();

        //Code to Hide Action Bar
        getSupportActionBar().hide();

//      Action Bar Title Name
        txtActionBarTitle.setText("Login");
        btnRegister.setPaintFlags(btnRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        llHome.setOnClickListener(this);
        llBack.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
    }

    //Methos to reference XML Widgets
    public void init() {
        edtMailId = (EditText) findViewById(R.id.edt_mail_id);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnResetPassword = (TextView) findViewById(R.id.btn_set_password);
        btnRegister = (TextView) findViewById(R.id.btn_register);
        btnLogIn = (TextView) findViewById(R.id.btn_login);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        txtActionBarTitle = (TextView) findViewById(R.id.txt_activity_name);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        Typeface typeface3= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf");
       // option_title.setTypeface(typeface3);
        edtMailId.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf"));
        ((TextInputLayout) findViewById(R.id.input_layout_email)).setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf"));
        edtPassword.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf"));
        ((TextInputLayout) findViewById(R.id.input_layout_password)).setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf"));
    }

    //Click Listeners of Widgets
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back: {
                finish();
            }
            break;

            case R.id.ll_home: {
                Intent in = new Intent(this, HomeActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
            break;


            case R.id.btn_login: {
                if (isLogInDataOk()) {
                    //Code If Login Data Is Ok
                    HashMap<String, String> logInData = new HashMap<String, String>();
                    logInData.put("MailId", edtMailId.getText().toString());
                    logInData.put("Password", edtPassword.getText().toString());

                    VolleyMethods vm = new VolleyMethods(this);
                    edtPassword.setText("");
                    vm.userLogIn(logInData);
                } else {
                    //Code if Login Data is not OK (Do Nothing)
                }
            }
            break;

            case R.id.btn_register: {
                Intent in = new Intent(this, RegistrationActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
            }
            break;

            case R.id.btn_set_password: {
//                Intent in = new Intent(this, ForgotPassword.class);
//                startActivity(in);
                popup();
            }

            break;
            case R.id.ll_menu: {
                Intent in = new Intent(this, Menu.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
            }
            break;

            case R.id.ll_about_us: {
                Intent in = new Intent(this, AboutUs.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
            break;

            case R.id.ll_contact_us: {
                Intent in = new Intent(this, MapsActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
            }
            break;

            case R.id.ll_check_out: {
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
            }
            break;
        }
    }


    //Method to get IsLoginData OK or Not
    public boolean isLogInDataOk() {

        boolean isOk;
        if (edtMailId.getText().toString().trim().equals("") || edtMailId.getText().toString().equals("Email id")) {
            isOk = false;
            Toast.makeText(this, "Please enter mail id", Toast.LENGTH_LONG).show();

        } else {
            if (isMailIdValid()) {
                if (edtPassword.getText().toString().trim().equals("") || edtPassword.getText().toString().equals("Password")) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                    isOk = false;
                } else {
                    isOk = true;
                }
            } else {
                //If Mail Is not Valid One
                isOk = false;
            }
        }
        return isOk;
    }

    //Method to Check Mail Id Is valid or Not
    public boolean isMailIdValid() {
        if (edtMailId.getText().toString().contains("@") && edtMailId.getText().toString().contains("."))
            return true;
        else {
            Toast.makeText(this, "Invalid mail id", Toast.LENGTH_LONG).show();
            return false;
        }

    }



    public  void popup(){
        LayoutInflater inflater = LayoutInflater.from(LogIn.this);
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password..");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                UtillClass utill=new UtillClass(LogIn.this);
                if(utill.isInternetConnected()){
                    String email=subEditText.getText().toString();
                    if(email.equals("")){
                        Toast.makeText(LogIn.this,"Email is mandatory",Toast.LENGTH_LONG).show();
                        popup();
                    }else {
                        VolleyMethods vm = new VolleyMethods(LogIn.this);
                        HashMap<String, String> userInfo = new HashMap<String, String>();
                        userInfo.put("User_Email", email);
                        vm.resetpasword(userInfo);

                    }

                }else{
                    Toast.makeText(LogIn.this,"Internet is not connected",Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LogIn.this, "Reset Password..Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }




    public  void popup(String Messges){

        LayoutInflater inflater = LayoutInflater.from(LogIn.this);
        View view = inflater.inflate(R.layout.custom_layoute, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        final EditText e1=(EditText)view.findViewById(R.id.otp) ;
        final  EditText e2=(EditText)view.findViewById(R.id.pas) ;
        final   EditText e3=(EditText)view.findViewById(R.id.conpas) ;

        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password..");
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        builder.setCancelable(false);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        if(!otp.equals("")){
            e1.setText(otp);
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                UtillClass utill=new UtillClass(LogIn.this);
                if(utill.isInternetConnected()){

                    if(e1.getText().toString().equals("") ){
                        Toast.makeText(LogIn.this,"OTP is mandatory",Toast.LENGTH_LONG).show();
                        popup("OTP is mandatory");
                    }else {

                        if(e2.getText().toString().equals("") ){
                            Toast.makeText(LogIn.this,"Password is mandatory",Toast.LENGTH_LONG).show();
                            otp=e1.getText().toString();
                            popup("Password is mandatory");

                        }else {
                            if(!e2.getText().toString().equals(e3.getText().toString())){
                                e3.setHint("Password not match");
                                e3.setHintTextColor(getResources().getColor(R.color.black));
                                otp=e1.getText().toString();
                                popup("Password not match");
                            }
                            else {
                                VolleyMethods vm = new VolleyMethods(LogIn.this);
                                HashMap<String, String> userInfo = new HashMap<String, String>();
                                userInfo.put("User_otp", e1.getText().toString());
                                userInfo.put("User_passo", e2.getText().toString());
                                vm.changepasswword(userInfo);
                            }}
                    }}else{
                    Toast.makeText(LogIn.this,"Internet is not connected",Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LogIn.this, "Reset Password..Cancel", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();

    }


    public  void popuptext(String Messges){

        LayoutInflater inflater = LayoutInflater.from(LogIn.this);
        View view = inflater.inflate(R.layout.custom_booking_tiffin, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);

        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password..");
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        builder.show();

    }





}