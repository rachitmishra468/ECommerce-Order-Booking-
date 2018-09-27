package com.uk.restaurant.GreenVich;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uk.restaurant.GreenVich.activity_fragments.HomeActivity;
import com.uk.restaurant.GreenVich.activity_fragments.SplashActivity;
import com.uk.restaurant.GreenVich.app.Config;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.NotificationUtils;
import com.uk.restaurant.GreenVich.utill.UtillClass;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.view.Window.FEATURE_NO_TITLE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity  extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {

            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            finish();

        }  else{
            txtRegId.setText("Firebase Reg Id is not received yet!");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
/*AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private final long millisecondsToShowSplash = 1500L;
    public static String  string="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };

        displayFirebaseRegId();
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("TAG@123", "Firebase reg id: " + regId);
        getApplicationVersionName(MainActivity.this);

    }


    public  void popup(String Messges){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.custom, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        textView.setTypeface(face);
        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connection status");
        builder.setIcon(R.drawable.internet_icon);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

            }
        });

        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    private void initLayout() {
        requestWindowFeature(FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        UtillClass utillClass =new UtillClass(getApplicationContext());

        if (!utillClass.isInternetConnected()){
            String s ="Please Check Your Internet Connection ";
            popup(s);

        }
        else{

        }


    }


    public static void getApplicationVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            Log.d("@1234",packageInfo.toString());
            String s=packageInfo.versionName.toString();
            string=s;
            Resources appR = context.getResources();
            CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                    "string", context.getPackageName()));
            String s1=txt.toString();
            VolleyMethods vm=new VolleyMethods(context);
            vm.update("Green Vich",s);



        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){


            String me=e.getMessage().toString();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    public void cartitemsheet( final  String PlayStoreVersion) {
        LayoutInflater inflater = (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.updatesheet, null);
        TextView textView=(TextView)view.findViewById(R.id.version);
        Button update=(Button)view.findViewById(R.id.update);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        textView.setText("Current Version "+string);
        update.setText("Update Latest Version "+PlayStoreVersion);
        textView.setTypeface(face);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                MainActivity.this.finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(PlayStoreVersion.equals(string)){
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final String parm = sharedPreferences.getString("Address", "") ;
                    if(parm.length()==0){
                        Constants.isUserLogIn=false;
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();

                    }
                    else {
                        Constants.userData=parm;
                        Constants.isUserLogIn=true;
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }


                }
                else {
                    Intent  intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id= https://play.google.com/store/apps/details?id=com.uk.restaurant.GreenVich"));
                    startActivity(intent);

                }

            }
        });


    }
    public void maintenance() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.maintenance, null);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();

    }
}
*/