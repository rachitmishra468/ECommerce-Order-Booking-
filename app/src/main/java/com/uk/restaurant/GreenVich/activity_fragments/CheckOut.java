package com.uk.restaurant.GreenVich.activity_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.adapter.CheckOutAdapter;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.OrderItemModel;
import com.uk.restaurant.GreenVich.utill.AsyncTaskDataBaseOperation;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import java.util.ArrayList;

/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class CheckOut extends AppCompatActivity implements View.OnClickListener {

    TextView cancelOrder, btnShopStatus;
    public static TextView totalPrice;
    public static DataBaseHelper dbHelper;
    public static CheckOutAdapter checkOutAdapter;
    public static RecyclerView orderCatRecycler;
    public static ArrayList<OrderItemModel> orderItemList;
    public static String shopStatus;
    LinearLayout llHome,llBack;
    public static TextView txtQuantity;
    TextView txtCategoryName ,textlog;
    ImageView logimg;
    private final long millisecondsToShowSplash = 2000L;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    TextView login;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().hide();
        init();
        if(Constants.isUserLogIn){
            login.setText("Logout");
        }
        else {
            login.setText("Login");

        }
        txtCategoryName.setText("Your Order");
        cancelOrder.setOnClickListener(this);
        btnShopStatus.setOnClickListener(this);
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
        shopStatus = new String();
        VolleyMethods vm = new VolleyMethods(this);
        vm.getStoreStatus();
        Bundle bundle = dbHelper.getItemQuantity();
        totalPrice.setText("Rs" + bundle.getString("TotalPrice"));
        bindAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindAdapter();

        Bundle bundle = dbHelper.getItemQuantity();
        Constants cons = new Constants();
        txtQuantity.setText(cons.getQuantity(this));
        totalPrice.setText("Rs" + bundle.getString("TotalPrice"));
        if(Constants.isUserLogIn){
            login.setText("Logout");
        }
        else {
            login.setText("Login");

        }
    }
    public void init() {
        dbHelper = new DataBaseHelper(this);
        orderItemList = new ArrayList<OrderItemModel>();
        btnShopStatus = (TextView) findViewById(R.id.btn_shop_status_check);
        totalPrice = (TextView) findViewById(R.id.txt_total_price_check);
        cancelOrder = (TextView) findViewById(R.id.btn_cancel_order_check);
        orderCatRecycler = (RecyclerView) findViewById(R.id.check_out_recycler);
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
        Typeface typeface3= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/bbb.ttf");
        btnShopStatus.setTypeface(typeface3);
        Typeface typeface4= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/bbb.ttf");
        cancelOrder.setTypeface(typeface3);

    }

    //Click Listener of Widgets
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_order_check: {
                new AsyncTaskDataBaseOperation(this).execute();
            }
            break;
            case R.id.btn_shop_status_check: {
                //Checking Cart List
              /*  if (shopStatus.equals("0")||shopStatus.equals("")) {
                    //Do Nothing Shop is closed
                    Toast.makeText(this, "Store is closed", Toast.LENGTH_LONG).show();
                } else {*/
                    //Shop is open
                    Bundle bundle = dbHelper.getItemQuantity();
                    if (Integer.parseInt(bundle.get("TotalItem").toString()) > 0) {

                        if(Constants.isUserLogIn){
                            //If User is Already Loged In
                            Intent in=new Intent(this,DeliveryOption.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(in);
                        }else{
                            Constants.isUserLogIn1=false;
                            //Call Log In Activity if user is not Loged In
                            Intent in = new Intent(this, LogIn.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(in);
                        }

                    } else {
                        Toast.makeText(this, "Add Item to cart_icon", Toast.LENGTH_LONG).show();
                    }

            }break;

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
               /* Intent in = new Intent(this, FindOnMapActivity.class);
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

    //Method to bind Adapter
    public void bindAdapter() {
        orderItemList = dbHelper.getOrder();
        //Binding Adapter
        int noofItems = orderItemList.size();
        //Method to set Adapter class
        checkOutAdapter = new CheckOutAdapter(CheckOut.this, orderItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        orderCatRecycler.setLayoutManager(mLayoutManager);
        orderCatRecycler.setItemAnimator(new DefaultItemAnimator());
        orderCatRecycler.setAdapter(checkOutAdapter);
    }

    public Double calculateTotalPrice(ArrayList<OrderItemModel> itemList) {
        Double totalPrice = new Double(0.0);

        for (OrderItemModel item : itemList) {
            totalPrice += (new Double(item.getItemPrice()) * (new Double(item.getItemQuantity())));
        }
        return totalPrice;
    }
    public void popuplpogpout(String mess) {
        LayoutInflater inflater = (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.notificationadditem, null);Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        TextView notify=(TextView)view.findViewById(R.id.notificationtext);
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
