package com.uk.restaurant.GreenVich.activity_fragments;
import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.app.Config;
import com.uk.restaurant.GreenVich.utill.AppController;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.NotificationUtils;
import com.uk.restaurant.GreenVich.utill.UtillClass;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import java.util.HashMap;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    static final Integer LOCATION = 0x1;
    TextView txtQuantity
            ,textlog;
    TextView movementtext;
    private ImageLoader mImageLoader;
    ImageView logimg;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut,share,call,tiffin;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    TextView login;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        init();

        if(Constants.isSharingEnable){
            share.setVisibility(View.GONE);
           // call.setVisibility(View.GONE);
        }
        if (Constants.isClickToCallEnabled){
            call.setVisibility(View.GONE);
        }

        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
        share.setOnClickListener(this);
        call.setOnClickListener(this);
        tiffin.setOnClickListener(this);
        UtillClass utill=new UtillClass(this);
        if(utill.isInternetConnected()){
            VolleyMethods vm=new VolleyMethods(this);
            vm.getAllData();
        }else{
            Toast.makeText(this,"Internet is not connected",Toast.LENGTH_LONG).show();
        }


        if(Constants.isUserLogIn){
            login.setText("Logout");

        }
        else {
            login.setText("Login");

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            askForPermission(Manifest.permission.READ_PHONE_STATE,LOCATION);
        }






        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String regId = sharedPreferences.getString("message", null);

        if(regId != null && regId.length() > 0){
            popuptext(regId);
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {



                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    popuptext(message);

                }
            }
        };

    }

    private void askForPermission(String permission, Integer requestCode) {
        if(ContextCompat.checkSelfPermission(HomeActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permission)) {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{permission}, requestCode);
            }
        } else {

        }
    }

    @Override
    public void onBackPressed() {

        finishAffinity();

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


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));


    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public  void popuptext(String Messges){

        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
        View view = inflater.inflate(R.layout.notification, null);
        mImageLoader = AppController.getInstance().getImageLoader();
        if (mImageLoader == null)
            mImageLoader = AppController.getInstance().getImageLoader();
        TextView textView=(TextView)view.findViewById(R.id.message);
        NetworkImageView profilePic=(NetworkImageView)view.findViewById(R.id.profilePic);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String title = sharedPreferences.getString("title", null);
        String time = sharedPreferences.getString("timestamp", null);
        String img = sharedPreferences.getString("imageUrl", null);
        if(img.length()<2){
            profilePic.setVisibility(View.GONE);
        }
        else {
            profilePic.setImageUrl(img, mImageLoader);
        }

        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(R.drawable.app_icon);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("message",  "");
                editor.putString("title",  "");
                editor.putString("timestamp",  "");
                editor.commit();
                alertDialog.dismiss();
            }
        });
        builder.setNegativeButton("Read Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();
            }
        });

        builder.show();

    }



    public void init() {
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        tiffin= (LinearLayout) findViewById(R.id.tiffin_srvice);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        share= (LinearLayout) findViewById(R.id.share);
        call= (LinearLayout) findViewById(R.id.call);
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
        movementtext=(TextView)findViewById(R.id.movementtext);

        if(Constants.HomeText.equals("")){
            movementtext.setVisibility(View.GONE);
        }else {
            movementtext.setText("********************* "  +Constants.HomeText  +  "********************");
        }
        movementtext.setSelected(true);
    }
    public  void popup( String Messges){


        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setTitle("Message!");

        builder.setMessage(Messges);

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog diag = builder.create();

        //Display the message!
        diag.show();
    }






    /*{


        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
    View view = inflater.inflate(R.layout.custom_booking_tiffin, null);
    TextView textView=(TextView)view.findViewById(R.id.tv);
    EditText book_name=(EditText) view.findViewById(R.id.book_userfull_name);
    EditText book_email=(EditText) view.findViewById(R.id.book_user_email);
    EditText book_user=(EditText) view.findViewById(R.id.book_user_phone);
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


}*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_menu: {

                UtillClass utill=new UtillClass(this);
                if(utill.isInternetConnected()){
                    Intent in = new Intent(this, Menu.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(in);

                }else{
                    Toast.makeText(this,"Internet is not connected",Toast.LENGTH_LONG).show();
                }

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


            }
            break;

            case R.id.ll_check_out: {
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;
            case R.id.share: {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Green Vich");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "clicke here to visit  https://play.google.com/store/apps/details?id=com.uk.restaurant.GreenVich");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
            break;
            case R.id.call: {
                 Intent intent = new Intent(Intent.ACTION_DIAL);
                 intent.setData(Uri.parse("tel:" + "01149046400"));
                 startActivity(intent);
            }
            break;
            case R.id.tiffin_srvice: {
               Intent intent =new Intent(HomeActivity.this,TiffinBookingActivity.class);
                startActivity(intent);
                break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {
                //Location
                case 1:


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();


                    }
                    break;
                //Call
              /*  case 2:
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "{This is a telephone number}"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    }
                    break;
                //Write external Storage
                case 3:
                    break;
                //Read External Storage
                case 4:
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, 11);
                    break;
                //Camera
                case 5:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 12);
                    }
                    break;*/
                //Accounts

            }

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{


            finish();
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
