package com.uk.restaurant.GreenVich.activity_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;*/
import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.Constants;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class MapsActivity extends AppCompatActivity implements   View.OnClickListener {
   /* OnMapReadyCallback,*/
    //private GoogleMap mMap;
    WebView viewInMap;
    LinearLayout llHome,llBack;
    TextView txtCategoryName;
    TextView txtQuantity   ,textlog;
    ImageView logimg;
    TextView login;
    ImageView imageView;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
     /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        getSupportActionBar().hide();
        init();

        if(Constants.isUserLogIn){
            login.setText("Logout");


        }
        else {
            login.setText("Login");

        }
        //Setting Activity Name
        txtCategoryName.setText("Contact Us");
        //Register Click Listener of Widgets
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
        String url = "https://www.google.co.in/maps/place/Greenvich/@28.6930173,77.1490005,17z/data=!3m1!4b1!4m12!1m6!3m5!1s0x390d02330b2ecbbf:0xdc375ce61b80978a!2sGreenvich!8m2!3d28.6930173!4d77.1511892!3m4!1s0x390d02330b2ecbbf:0xdc375ce61b80978a!8m2!3d28.6930173!4d77.1511892";
        viewInMap.getSettings().setJavaScriptEnabled(true);
        viewInMap.setWebViewClient(new WebViewClient());
        viewInMap.loadUrl(url);
        viewInMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
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
    public void init(){
        viewInMap = (WebView) findViewById(R.id.web_view_on_map1);
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
                //Intent in = new Intent(this, AboutUs.class);
                Intent in = new Intent(this, AboutUs.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_contact_us: {
                //Do Not Do Nothing It is Already On this Page
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
           /*     Intent in = new Intent(this, FindOnMapActivity.class);
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
}
