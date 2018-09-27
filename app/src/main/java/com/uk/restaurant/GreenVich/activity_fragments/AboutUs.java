package com.uk.restaurant.GreenVich.activity_fragments;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */
public class AboutUs extends AppCompatActivity implements View.OnClickListener{
    TextView addressdata,phonenodata;
    LinearLayout llHome, llBack;
    TextView txtCategoryName  ,textlog;
    ImageView logimg;
    TextView txtQuantity,textView7,name,openinglist;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    TextView login,mon,tus,wed,tur,fri,sur,sun;
    ImageView imageView;
    TextView m,t,wd,th,fr,sa,su;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().hide();
        init();
        getinfo();
        if(Constants.isUserLogIn){
            login.setText("Logout");
        }
        else {
            login.setText("Login");

        }
        txtCategoryName.setText(R.string.app_name);
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
        Constants cons = new Constants();
        txtQuantity.setText(cons.getQuantity(this));
        if(Constants.isUserLogIn){
            login.setText("Logout");

        }
        else {
            login.setText("Login");

        }
    }

    public void init() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
        addressdata=(TextView)findViewById(R.id.addresstext);
        phonenodata=(TextView)findViewById(R.id.phone);
        mon=(TextView) findViewById(R.id.mon);
        tus=(TextView) findViewById(R.id.tuesday);
        wed=(TextView) findViewById(R.id.wednesday);
        tur=(TextView) findViewById(R.id.thrusday);
        fri=(TextView) findViewById(R.id.friday);
        sur=(TextView) findViewById(R.id.saturday);
        sun=(TextView) findViewById(R.id.sunday);
        textView7=(TextView)findViewById(R.id.textView7);
        name=(TextView)findViewById(R.id.name);
        openinglist=(TextView)findViewById(R.id.openinglist);
        Typeface face1= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/bbb.ttf");
        Typeface face= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/aaa.ttf");
        name.setTypeface(face1);
        textView7.setTypeface(face1);
        openinglist.setTypeface(face);
        m=(TextView)findViewById(R.id.m) ;
        t=(TextView)findViewById(R.id.t) ;
        wd=(TextView)findViewById(R.id.wd) ;
        th=(TextView)findViewById(R.id.th) ;
        fr=(TextView)findViewById(R.id.fr) ;
        sa=(TextView)findViewById(R.id.sa) ;
        su=(TextView)findViewById(R.id.su) ;

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) {
            mon.setTextColor(getResources().getColor(R.color.red));
            m.setTextColor(getResources().getColor(R.color.red));
        } else if (Calendar.TUESDAY == dayOfWeek) {
            tus.setTextColor(getResources().getColor(R.color.red));
            t.setTextColor(getResources().getColor(R.color.red));
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            wed.setTextColor(getResources().getColor(R.color.red));
            wd.setTextColor(getResources().getColor(R.color.red));
        } else if (Calendar.THURSDAY == dayOfWeek) {
            tur.setTextColor(getResources().getColor(R.color.red));
            th.setTextColor(getResources().getColor(R.color.red));
        } else if (Calendar.FRIDAY == dayOfWeek) {
            fri.setTextColor(getResources().getColor(R.color.red));
            fr.setTextColor(getResources().getColor(R.color.red));
        } else if (Calendar.SATURDAY == dayOfWeek) {
            sur.setTextColor(getResources().getColor(R.color.red));
            sa.setTextColor(getResources().getColor(R.color.red));
        } else if (Calendar.SUNDAY == dayOfWeek) {
            sun.setTextColor(getResources().getColor(R.color.red));
            su.setTextColor(getResources().getColor(R.color.red));
        }
    }
    public  void getinfo(){
        VolleyMethods vm = new VolleyMethods(this);
        vm.getinfo();
    }

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

            case R.id.ll_menu: {
                Intent in = new Intent(this, Menu.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_about_us: {
                //Do Nothing it is Already on This Activity
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
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;
        }
    }

    public  void setdayatime(){
        try{
            JSONArray arr = new JSONArray(Constants.infotime);
            JSONObject jsonObject=arr.getJSONObject(0);
            JSONArray  jsonArray= jsonObject.getJSONArray("delivery_time");
            JSONObject jsonObject1= jsonObject.getJSONObject("address");
            String  data1=jsonObject1.getString("phone").toString();
            String  data2=jsonObject1.getString("address").toString();
            addressdata.setText(data2);
            phonenodata.setText(data1);

            for(int i=0;i<=jsonArray.length();i++) {
                JSONObject jobj = jsonArray.getJSONObject(i);


                if (i == 0) {
                    try{

                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        mon.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){

                    }

                } else if (i == 1) {
                    try{
                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        tus.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){

                    }

                } else if (i == 2) {
                    try{
                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        wed.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){

                    }

                } else if (i == 3) {
                    try{
                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        tur.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){

                    }

                } else if (i == 4) {
                    try{
                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        fri.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){

                    }

                } else if (i == 5) {
                    try{
                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        sur.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){

                    }

                } else if (i == 6) {
                    try{

                        SimpleDateFormat check_in_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm a");
                        Date Dt = check_in_time.parse(jobj.getString("check_in_time").toString());
                        SimpleDateFormat check_out_time = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat check_out_timeSDF = new SimpleDateFormat("hh:mm a");
                        Date check_out_timeDt = check_out_time.parse(jobj.getString("check_out_time").toString());
                        sun.setText(SDF.format(Dt).toString()  + "-"+check_out_timeSDF.format(check_out_timeDt).toString());}
                    catch (ParseException p){



                    }

                }

            }

        }catch (JSONException e){}





    }
}
