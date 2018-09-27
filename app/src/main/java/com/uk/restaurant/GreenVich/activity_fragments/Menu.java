package com.uk.restaurant.GreenVich.activity_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.adapter.ExpandableListAdapter;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.ItemModel;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.UtillClass;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class Menu extends AppCompatActivity implements View.OnClickListener {

    //Complete Data Of all Items
    ArrayList<String> groupList;
    ArrayList<String> childList;
    ExpandableListView expListView;
    Map<String, ArrayList<ItemModel>> itemDataInMap;
    UtillClass util = new UtillClass(this);
    public static TextView txtQuantity, price;
    TextView txtCategoryName, textlog;
    ImageView logimg;
    private final long millisecondsToShowSplash = 1500L;
    public static DataBaseHelper dbHelper;
    LinearLayout llHome, llBack;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut, llFindOut;
    public static RelativeLayout ll_check;
    TextView login;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Code to Hide Action Bar
        getSupportActionBar().hide();


        init();


        if (Constants.isCartNotificationEnable) {
            txtQuantity.setVisibility(View.GONE);

        }
        if (Constants.isUserLogIn) {
            login.setText("Logout");


        } else {
            login.setText("Login");

        }
        //Setting Activity Name
        txtCategoryName.setText("Order");
        //Register Click Listener of Widgets
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
        ll_check.setOnClickListener(this);
        if (util.isInternetConnected()) {

            if (Constants.isDataLoaded) {
                itemDataInMap = new LinkedHashMap<String, ArrayList<ItemModel>>();
                groupList = new ArrayList<String>();
                Set records = Constants.completeData.keySet();
                Iterator myVeryOwnIterator = Constants.completeData.keySet().iterator();
                while (myVeryOwnIterator.hasNext()) {
                    String key = (String) myVeryOwnIterator.next();
                    groupList.add(key);
                    loadChild(key, Constants.completeData.get(key));
                    ArrayList<ItemModel> value = Constants.completeData.get(key);
                }
                expListView = (ExpandableListView) findViewById(R.id.expendable_view);

                expListView.setDividerHeight(5);
                expListView.setDividerHeight(5);
                expListView.setGroupIndicator(null);
                expListView.setClickable(true);

                final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, groupList, itemDataInMap);
                expListView.setAdapter(expListAdapter);

                //setGroupIndicatorToRight();

                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        int count = expListAdapter.getGroupCount();
                        for (int i = 0; i < count; i++)
                            expListView.collapseGroup(i);
                        return true;
                    }
                });
                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)
                            expListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }
                });

            } else {
                Constants.isDataLoaded = false;
                Intent in = new Intent(this, HomeActivity.class);
                startActivity(in);
                this.finish();
            }
        } else {
            Toast.makeText(Menu.this, "Internet not connected", Toast.LENGTH_SHORT).show();
            Constants.isDataLoaded = false;
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();
        Bundle bundle = dbHelper.getItemQuantity();
        txtQuantity.setText(cons.getQuantity(this));
        price.setText("Rs " + bundle.getString("TotalPrice"));

        String t = bundle.getString("TotalPrice").toString();
        if (t.equals("0.00")) {
            ll_check.setVisibility(View.GONE);
        } else {
            ll_check.setVisibility(View.VISIBLE);
        }

        if (Constants.isUserLogIn) {
            login.setText("Logout");


        } else {
            login.setText("Login");

        }
    }

    //Method to reference XML Widgets
    public void init() {
        dbHelper = new DataBaseHelper(this);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llFindOut = (LinearLayout) findViewById(R.id.ll_find_on_map);
        login = (TextView) findViewById(R.id.logintext);
        imageView = (ImageView) findViewById(R.id.login);
        price = (TextView) findViewById(R.id.price);
        ll_check = (RelativeLayout) findViewById(R.id.ll_check);
    }

    //Click Listener of Widgets
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
                //Do Nothing It Is ALready At that Activity
                Intent in = new Intent(this, Menu.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_about_us: {
                // Intent in = new Intent(this, AboutUs.class);
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
            case R.id.ll_check: {
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;

            case R.id.ll_find_on_map: {
                if (Constants.isUserLogIn) {
                    login.setText("Login");
                    SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Address", "");
                    editor.commit();

                    Constants.isUserLogIn = false;
                    Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                } else {
                    Intent in = new Intent(this, LogIn.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(in);
                    Constants.isUserLogIn1 = true;
                }
               /* Intent in = new Intent(this, FindOnMapActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);*/
            }
            break;

            case R.id.ll_check_out: {

                Bundle bundle = dbHelper.getItemQuantity();
                if (Integer.parseInt(bundle.get("TotalItem").toString()) > 0) {
                    VolleyMethods vm = new VolleyMethods(this);
                    vm.getStoreStatus();
                    if (Constants.isUserLogIn) {
                        //If User is Already Loged In
                        Intent in = new Intent(this, DeliveryOption.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(in);
                    } else {
                        Constants.isUserLogou = false;
                        //Call Log In Activity if user is not Loged In
                        Intent in = new Intent(this, LogIn.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(in);
                    }

                } else {
                    popuplpogpout("Add Item to Cart");
                    Toast.makeText(this, "Add Item to Cart ", Toast.LENGTH_LONG).show();
                }


            }
            break;
        }
    }

    private void loadChild(String key, ArrayList<ItemModel> items) {
        itemDataInMap.put(key, Constants.completeData.get(key));
    }


    public void popuplpogpout(String mess) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.notificationadditem, null);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        TextView notify = (TextView) view.findViewById(R.id.notificationtext);
        notify.setTypeface(face);
        notify.setText(mess);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        mBottomSheetDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomSheetDialog.dismiss();

            }
        }, millisecondsToShowSplash);


    }
}
