package com.uk.restaurant.GreenVich.activity_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import java.util.HashMap;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    LinearLayout llHome, llBack;
    TextView txtCategoryName;
    TextView txtQuantity;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;
    EditText edtFullName, edtMailId, edtPassword, edtCofirmPassword, edtPhoneNumber, edtHouseNumber, edtStreet, edtCity, edtPincode, edtState;
    private TextInputLayout  inputLayoutEmail;
    TextView login;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
        txtCategoryName.setText("Registration");
        btnSubmit.setOnClickListener(this);
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants cons=new Constants();
        txtQuantity.setText(cons.getQuantity(this));
        if(Constants.isUserLogIn){
            login.setText("Logout");


        }
        else {
            login.setText("Login");

        }
    }

    //Method to reference XML widgets
    public void init() {
        btnSubmit = (Button) findViewById(R.id.btn_submit_registration);
        edtFullName = (EditText) findViewById(R.id.edt_full_name_registration);
        edtMailId = (EditText) findViewById(R.id.edt_email_id_registration);
        edtPassword = (EditText) findViewById(R.id.edt_password_registration);
        edtCofirmPassword = (EditText) findViewById(R.id.edt_confirm_password_registration);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_phone_no_registration);
        edtHouseNumber = (EditText) findViewById(R.id.edt_house_registration);
        edtStreet = (EditText) findViewById(R.id.edt_street_registration);
        edtCity = (EditText) findViewById(R.id.edt_city_registration);
        edtPincode = (EditText) findViewById(R.id.edt_pin_code_registration);
        edtState = (EditText) findViewById(R.id.edt_state_registration);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        edtMailId.addTextChangedListener(new MyTextWatcher(edtMailId));
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
    }

    //Click Listener of XML Widgewts
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back: {
                finish();
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
            case R.id.ll_home: {
                Intent in = new Intent(this, HomeActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
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
            case R.id.btn_submit_registration: {

                if (isRegistrationDataOK()) {
                    //Perform Action Registration Data is Ok
                    if (isPasswordMatching()) {
                        //Code if Password Matching
                        VolleyMethods vm = new VolleyMethods(this);
                        HashMap<String, String> userInfo = new HashMap<String, String>();
                        userInfo.put("full_name", edtFullName.getText().toString());
                        userInfo.put("mail_id", edtMailId.getText().toString());
                        userInfo.put("password", edtPassword.getText().toString());
                        userInfo.put("contact_no", edtPhoneNumber.getText().toString());
                        userInfo.put("house_no", edtHouseNumber.getText().toString());
                        userInfo.put("street", edtStreet.getText().toString());
                        userInfo.put("city", edtCity.getText().toString());
                        userInfo.put("post", edtPincode.getText().toString());
                        userInfo.put("state", edtState.getText().toString());
                        vm.registerUser(userInfo);
                    } else {
                        //Password is not matching
                    }
                } else {
                    //do Nothing Registration Data is not OK
                }
            }
        }
    }


        /*Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mleftSheetDialog.dismiss();
            }
        });
*/


    private boolean validateEmail() {
        String email = edtMailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(edtMailId);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //Method to check is registration Data OK or not
    public boolean isRegistrationDataOK() {
        boolean isOk;

        if (edtFullName.getText().toString().trim().equals("") || edtFullName.getText().toString().equals("Full name")) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            isOk = false;
        } else {
            if (edtMailId.getText().toString().trim().equals("") || edtMailId.getText().toString().equals("Email id")) {
                Toast.makeText(this, "Please enter mail id", Toast.LENGTH_LONG).show();
                isOk = false;
            } else {
                if (edtPassword.getText().toString().trim().equals("") || edtPassword.getText().toString().equals("Password")) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                    isOk = false;
                } else {
                    if (edtCofirmPassword.getText().toString().trim().equals("") || edtCofirmPassword.getText().toString().equals("Confirm password")) {
                        Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_LONG).show();
                        isOk = false;
                    } else {
                        if (edtPhoneNumber.getText().toString().trim().equals("") || edtPhoneNumber.getText().toString().equals("Contact no")) {
                            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
                            isOk = false;
                        } else {
                            //Checking Delivery Address
                            if (isDeliveryAddressOk()) {
                                //Perform Action Delivery Address is OK
                                isOk = true;
                            } else {
                                //Do Nothing Delivery address is not OK
                                isOk = false;
                            }
                        }
                    }
                }
            }
        }
        return isOk;
    }

    //Method to Check Delivery Address
    public boolean isDeliveryAddressOk() {
        boolean isDeliveryAddressOK;

        if (edtHouseNumber.getText().toString().trim().equals("") || edtHouseNumber.getText().toString().trim().equals("House")) {
            Toast.makeText(this, "Please enter house number", Toast.LENGTH_LONG).show();
            isDeliveryAddressOK = false;
        } else {
            if (edtStreet.getText().toString().trim().equals("") || edtStreet.getText().toString().trim().equals("Street")) {
                Toast.makeText(this, "Please enter street", Toast.LENGTH_LONG).show();
                isDeliveryAddressOK = false;
            } else {
                if (edtCity.getText().toString().trim().equals("") || edtCity.getText().toString().trim().equals("City")) {
                    Toast.makeText(this, "Please enter city", Toast.LENGTH_LONG).show();
                    isDeliveryAddressOK = false;
                } else {
                    if (edtPincode.getText().toString().trim().equals("") || edtPincode.getText().toString().trim().equals("Pin code")) {
                        Toast.makeText(this, "Please enter pin code", Toast.LENGTH_LONG).show();
                        isDeliveryAddressOK = false;
                    } else {
                        if (edtPincode.getText().toString().trim().equals("") || edtPincode.getText().toString().trim().equals("Pin code")) {
                            Toast.makeText(this, "Please enter pin code", Toast.LENGTH_LONG).show();
                            isDeliveryAddressOK = false;
                        } else {
                            //Delivery Address is OK
                            isDeliveryAddressOK = true;
                        }
                    }
                }
            }
        }
        return isDeliveryAddressOK;
    }

    //Code to check is password matching or not
    public boolean isPasswordMatching() {


        if (edtPassword.getText().toString().equals(edtCofirmPassword.getText().toString()))
            return true;
        else

            Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show();
            return false;
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.edt_email_id_registration:
                    validateEmail();
                    break;
               /* case R.id.edt_password:
                    validatePassword();
                    break;*/
            }
        }
    }
}
