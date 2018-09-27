package com.uk.restaurant.GreenVich.activity_fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class DeliveryOption extends AppCompatActivity implements View.OnClickListener {
    TextView txtPostCodeWarning;
    Button btnContinue;
    EditText edtPostCode;
    RadioGroup radioGroup;
    RadioButton radioDelivery, radioCollect;
    LinearLayout llHome,llBack,pincode,timeselect;
    Date dateObj1,dateObj2;
    TextView txtQuantity,Time,hide,option_title;
    TextView txtCategoryName,login;
    ImageView imageView;
    Spinner spinner2;
    String dayOfTheWeek;
    String newDateStr;
    boolean type=true;
    public static DataBaseHelper dbHelper;
    public static String checkInTime;
    public static String checkOutTime;
    public static String CollectionCycle;
    public static String shopStatus;
    public static String DeliveryCycle;
    int time;
    public static boolean stor= true;
    String timedate;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    private final long millisecondsToShowSplash = 3000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_option);
        getSupportActionBar().hide();
        init();
        String test=shopStatus;
        txtCategoryName.setText("Instructions");
        btnContinue.setOnClickListener(this);
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
        if(Constants.isUserLogIn){
            login.setText("Logout");
        }
        else {
            login.setText("Login");
        }
        btnContinue.setText("Check delivery is available");
        try{
            slot(Integer.parseInt(DeliveryCycle));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Intent in=new Intent(this,DeliveryOption.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(in);

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_delivery: {

                        if(!stor){
                            type=true;
                            Time.setText("Choose Delivery Time ");
                            timeselect.setVisibility(View.GONE);
                            edtPostCode.setVisibility(View.VISIBLE);
                            hide.setText("Please fill the information to delivery your food");
                            btnContinue.setText("Check delivery is available");
                            pincode.setVisibility(View.VISIBLE);
                            slot(Integer.parseInt(DeliveryCycle));
                        }
                        else {
                            type=true;
                            hide.setText("Please fill the information to delivery your food");
                            Time.setText("Choose Delivery Time ");
                            timeselect.setVisibility(View.GONE);
                            btnContinue.setText("Check delivery is available");
                            pincode.setVisibility(View.VISIBLE);
                            slot(Integer.parseInt(DeliveryCycle));
                        }
                    }
                    break;
                    case R.id.radio_collect: {
                        if (!stor) {
                            type = false;
                            timeselect.setVisibility(View.GONE);
                            pincode.setVisibility(View.GONE);
                            hide.setText("Estimated Collection Time \n" + datatime(Integer.parseInt(CollectionCycle)));
                            btnContinue.setText("Continue");
                            datatime(Integer.parseInt(CollectionCycle));

                        } else {
                            type = false;
                            timeselect.setVisibility(View.VISIBLE);
                            pincode.setVisibility(View.GONE);
                            Time.setText("Choose Collection Time ");
                            btnContinue.setText("Continue");
                            slot(Integer.parseInt(CollectionCycle));
                        }
                    }
                    break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();
        txtQuantity.setText(cons.getQuantity(this));
        if (CheckOut.shopStatus.equals("0")) {
            openBottomSheet();
        }
        else {
            //fillUserData();
            btnContinue.setText("Continue");
            stor=false;
        }
        if(Constants.isUserLogIn){
            login.setText("Logout");

        }
        else {
            login.setText("Login");

        }


        if(Constants.OrderDisabled){
            openBottomSheetclose();
            btnContinue.setEnabled(false);
            radioCollect.setEnabled(false);
            radioDelivery.setEnabled(false);
            btnContinue.setBackgroundColor(Color.RED);
            btnContinue.setText("Sorry,we are currently closed");

        }
    }
    public void openBottomSheetclose() {
        View view = getLayoutInflater().inflate(R.layout.storestatus, null);
        LinearLayout le=(LinearLayout)view.findViewById(R.id.linerlayout) ;
        TextView Add_pin = (TextView)view.findViewById(R.id.text);
        Add_pin.setText("Sorry,we are currently closed");
        final Dialog mBottomSheetDialog = new Dialog(DeliveryOption.this);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.setTitle("Closed");
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.show();


        le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();


            }
        });

    }
    public void openBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.storestatus, null);
        LinearLayout le=(LinearLayout)view.findViewById(R.id.linerlayout) ;
        TextView Add_pin = (TextView)view.findViewById(R.id.text);
        Add_pin.setText("Sorry,we are currently closed ,however you can still place an order and we will process it when we open at " +Constants.time);
        final Dialog mBottomSheetDialog = new Dialog(DeliveryOption.this);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.show();
        le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

    }

    public void fillUserData(){
        try {
            JSONObject jObj=new JSONObject(Constants.userData);
            edtPostCode.setText(Constants.postal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        btnContinue = (Button) findViewById(R.id.btn_continue);
        radioCollect = (RadioButton) findViewById(R.id.radio_collect);
        radioDelivery = (RadioButton) findViewById(R.id.radio_delivery);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_delivery_option);
        txtPostCodeWarning = (TextView) findViewById(R.id.txt_post_code_warning);
        llBack=(LinearLayout)findViewById(R.id.ll_back);
        llHome=(LinearLayout)findViewById(R.id.ll_home);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
        spinner2 = (Spinner) findViewById(R.id.spinner1);
        Time=(TextView)findViewById(R.id.timetest);
        timeselect= (LinearLayout) findViewById(R.id.timeselect);
        pincode= (LinearLayout) findViewById(R.id.pincode);
        edtPostCode = (EditText) findViewById(R.id.popstcpode);
        hide=(TextView)findViewById(R.id.hide);
        option_title=(TextView)findViewById(R.id.option_title);
        Typeface typeface3= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf");
        option_title.setTypeface(typeface3);
        VolleyMethods vm = new VolleyMethods(this);
        vm.getStoreStatus();



    }
    public  void popup(String Messges, final  String status){

        LayoutInflater inflater = LayoutInflater.from(DeliveryOption.this);

        View view = inflater.inflate(R.layout.custom, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        textView.setTypeface(face);
        textView.setText(Messges);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pincode...");

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(status.equals("1")){
                    if(!stor){
                        btnContinue.setText("Continue");
                        pincode.setVisibility(View.GONE);
                        hide.setText("Estimated Delivery Time \n" +datatime(Integer.parseInt(DeliveryCycle)));
                        type=false;
                    }else {
                        timeselect.setVisibility(View.VISIBLE);
                        pincode.setVisibility(View.GONE);
                        Time.setText("Choose Delivery Time ");
                        btnContinue.setText("Continue");
                        type=false;
                        slot(Integer.parseInt(DeliveryCycle));
                    }

                }

                alertDialog.dismiss();

            }
        });

        builder.show();




    }




    public  void slot(int timeslot){
        try{
            int add=timeslot*1000*60;
            String full_timel;
            List<String> list3 = new ArrayList<String>();
            String time14 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            String format = "HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date dateObj1 = sdf.parse(checkInTime);
           /* Date dateObj1 = sdf.parse(time14);*/
            Date dateObj2 = sdf.parse(checkOutTime);
            Date dateObj23 = sdf.parse(time14);
            long dif = dateObj1.getTime();
            long dif2 = dateObj23.getTime();
            Long l = Long.valueOf(timeslot*60*1000);
            while (dif < dateObj2.getTime()) {
                Date slot = new Date(dif);
                full_timel=""+slot.getHours();
                String  full_time2=""+slot.getMinutes();
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");
                Date d = new Date();
                dayOfTheWeek = sdf1.format(d);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM");
                String formattedDate = df.format(c.getTime());

                String full_time4 =full_timel+":"+full_time2+":"+"00"+" on "+dayOfTheWeek+" "+formattedDate;
                if(dif == dateObj1.getTime()){

                }
                else {
                    list3.add(full_time4);
                    String time1 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    String time2 = checkInTime;
                    SimpleDateFormat formatt = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = formatt.parse(time1);
                    Date date2 = formatt.parse(time2);
                    long difference = date2.getTime() - date1.getTime();
                    Log.d("TAGtime@", "time -"+dateObj1.getTime());
                }
                dif += add;
                Log.d("TAGtime", ""+full_time4);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DeliveryOption.this,android.R.layout.simple_spinner_item, list3);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(dataAdapter);

        }catch (ParseException op){
            Log.d("TAGtime", ""+op);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue: {
                if (Constants.OrderDisabled) {
                    openBottomSheetclose();
                    btnContinue.setText("Sorry,we are currently closed");

                } else {

                    if (Constants.isUserLogIn) {


                        if (!stor) {

                            if (type) {
                                if (edtPostCode.getText().toString().equals("")) {
                                    Toast.makeText(getApplicationContext(), "Enter Pin Code ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Constants.postal = new String("");
                                    VolleyMethods vm = new VolleyMethods(this);
                                    Constants.postal = edtPostCode.getText().toString();
                                    String t = Constants.postal;
                                    vm.postcode(edtPostCode.getText().toString());
                                }
                                ;

                            } else {
                                int id = radioGroup.getCheckedRadioButtonId();
                                switch (id) {
                                    case R.id.radio_delivery: {

                                        dbHelper = new DataBaseHelper(this);
                                        Bundle bundle = dbHelper.getItemQuantity();
                                        Float t = Float.parseFloat(bundle.getString("TotalPrice"));

                                        if (t < Float.parseFloat(Constants.minimum_delivery_amount)) {
                                            openBottomSheetamount("For Delivery Order\n" +

                                                    "Minimum Amount Should be Rs " + Constants.minimum_delivery_amount);
                                        } else {

                                            edtPostCode.setVisibility(View.GONE);
                                            txtPostCodeWarning.setVisibility(View.GONE);
                                            Intent in = new Intent(this, DeliveryDetailAddress.class);
                                            in.putExtra("DeliveryType", "D");
                                            String data = datatime(Integer.parseInt(DeliveryCycle));
                                            in.putExtra("Time", datatime(Integer.parseInt(DeliveryCycle)));
                                            startActivity(in);
                                            this.finish();
                                        }
                                    }
                                    break;
                                    case R.id.radio_collect: {

                                        edtPostCode.setVisibility(View.GONE);
                                        Intent in = new Intent(this, DeliveryDetailAddress.class);
                                        in.putExtra("DeliveryType", "C");
                                        String data = datatime(Integer.parseInt(CollectionCycle));
                                        in.putExtra("Time", datatime(Integer.parseInt(CollectionCycle)));
                                        startActivity(in);
                                    }
                                }
                            }


                        } else {
                            if (type) {
                                if (edtPostCode.getText().toString().equals("")) {
                                    Toast.makeText(getApplicationContext(), "Enter Pin Code ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Constants.postal = new String("");
                                    VolleyMethods vm = new VolleyMethods(this);

                                    Constants.postal = edtPostCode.getText().toString();
                                    String t = Constants.postal;
                                    vm.postcode(edtPostCode.getText().toString());
                                }
                                ;

                            } else {
                                int id = radioGroup.getCheckedRadioButtonId();
                                switch (id) {
                                    case R.id.radio_delivery: {
                                        dbHelper = new DataBaseHelper(this);
                                        Bundle bundle = dbHelper.getItemQuantity();
                                        Float t = Float.parseFloat(bundle.getString("TotalPrice"));

                                        if (t < Float.parseFloat(Constants.minimum_delivery_amount)) {
                                            openBottomSheetamount("For Delivery Order\n" +

                                                    "Minimum Amount Should be Rs " + Constants.minimum_delivery_amount);
                                        } else {

                                            txtPostCodeWarning.setVisibility(View.GONE);
                                            Intent in = new Intent(this, DeliveryDetailAddress.class);
                                            in.putExtra("DeliveryType", "D");
                                            in.putExtra("Time", spinner2.getSelectedItem().toString());
                                            startActivity(in);
                                            this.finish();
                                        }
                                    }
                                    break;
                                    case R.id.radio_collect: {
                                        Intent in = new Intent(this, DeliveryDetailAddress.class);
                                        in.putExtra("DeliveryType", "C");
                                        in.putExtra("Time", spinner2.getSelectedItem().toString());
                                        startActivity(in);
                                    }
                                }
                            }
                        }
                    } else {


                        Intent in = new Intent(this, LogIn.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(in);
                        Constants.isUserLogIn1 = false;
                    }

                }
            }


            break;

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



            case R.id.ll_contact_us: {
                Intent in = new Intent(this, MapsActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_find_on_map: {
                if(Constants.isUserLogIn){
                    login.setText("Login");
                    Constants.isUserLogIn=false;
                    SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Address",  "");
                    editor.commit();
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
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;
        }
    }
    public void openBottomSheetamount(String mes) {
        View view = getLayoutInflater().inflate(R.layout.storestatus, null);
        LinearLayout le=(LinearLayout)view.findViewById(R.id.linerlayout) ;
        TextView Add_pin = (TextView)view.findViewById(R.id.text);
        Add_pin.setText(mes);
        final Dialog mBottomSheetDialog = new Dialog(DeliveryOption.this,R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.setTitle("");
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.show();


        le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();


            }
        });

    }
    public  String datatime(int i){
        String full_timel;
        String full_time4="";
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MINUTE, i);
        String time14 = new SimpleDateFormat("HH:mm:ss").format(c1.getTime());
        String format = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date dateObj23 = sdf.parse(time14);
            long dif = dateObj23.getTime();
            Date slot = new Date(dif);
            full_timel = "" + slot.getHours();
            String  full_time2=""+slot.getMinutes();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM");
            Calendar c = Calendar.getInstance();
            String formattedDate = df.format(c.getTime());
            full_time4 = full_timel + ":" + full_time2 + ":" + "00" + " on " + dayOfTheWeek + " " + formattedDate;
        }catch (ParseException e){

        }
        String t= full_time4;
        return full_time4 ;
    }





}
