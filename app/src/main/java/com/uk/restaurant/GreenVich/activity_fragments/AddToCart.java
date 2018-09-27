package com.uk.restaurant.GreenVich.activity_fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.Itemsdata;
import com.uk.restaurant.GreenVich.model_classes.OrderItemModel;
import com.uk.restaurant.GreenVich.utill.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Rachit Mishra on 03-06-2016.
 */

public class AddToCart extends AppCompatActivity implements View.OnClickListener {
    boolean flagsetr =true;
    TextView btnAddToCart;
    Spinner selectQuantity;
    EditText edtCockingInstruction;
    LinearLayout llDynamicLayout;
    TextView txtCategoryName  ,textlog;
    ImageView logimg;
    TextView txtQuantity,quantityadd,price,nameitem;
    GridLayout TopingForSinglePizza;
    ArrayList<String> itemBase, itemBasePrice,itemPizzaIndex,itembasesize,itemspecial,ID;
    LinearLayout llHome, llBack;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    ImageView add,remove;
    TextView txt1;
    int total;
    TextView login;
    String No_toping ;
    ImageView imageView;
    public  String Size="";
    public  String Base="";
    LinearLayout llSpace1;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        getSupportActionBar().hide();
        init();
        Intent intent = getIntent();
        String id = intent.getStringExtra("ITEM_PRICE");
        String name = intent.getStringExtra("PARENT_CATEGORY_NAME");
        String subname=intent.getStringExtra("ITEM_NAME");
        No_toping=intent.getStringExtra("number");
        price.setText("Rs "+id);
        nameitem.setText(" "+subname);
        txtCategoryName.setText("Add To Cart");
        //Register click listener of widgets
        btnAddToCart.setOnClickListener(this);
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llFindOut.setOnClickListener(this);
        add.setOnClickListener(this);
        remove.setOnClickListener(this);
        //Initalize Values to Item Base and Item Price
        itemBase = new ArrayList<String>();
        itemBasePrice = new ArrayList<String>();
        itemPizzaIndex = new ArrayList<String>();
        itembasesize=new ArrayList<String>();
        itemspecial=new ArrayList<String>();
        ID=new ArrayList<String>();
        //Inflating Sub Item 1
        String dataurl=getIntent().getExtras().getString("Base");

        if(dataurl != null ){
            Base();
        }
        if(Constants.isUserLogIn){
            login.setText("Logout");


            //Constants.isUserLogIn=false;
            // Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
        }
        else {
            login.setText("Login");

        }

        if (Constants.subItem1.equals("")) {
            //Do Nothing Its value is null
        } else {
            try {
                JSONArray arr = new JSONArray(Constants.subItem1);
                inflateSpecialOffers1(arr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (Constants.Size.equals("")||Constants.Size.equals("[]")) {
            //Do Nothing Its value is null
        } else {
            try {
                JSONArray arr = new JSONArray(Constants.Size);
                Sizeset(arr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (Constants.subItem3.equals("")||Constants.subItem3.equals("[]")) {
            //Do Nothing Its value is null
        } else {
            try {
                JSONArray arr = new JSONArray(Constants.subItem3);
                inflateSpecialOffers3(arr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (Constants.subItem4.equals("")) {
            //Do Nothing Its value is null
        } else {
            try {
                JSONArray arr = new JSONArray(Constants.subItem4);
                inflateSpecialOffers4(arr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (Constants.subItem2.equals("")||Constants.subItem3.equals("[]")) {
            //Do Nothing Its value is null
        } else {


            if(flagsetr){
            try {
                JSONArray arr = new JSONArray(Constants.subItem2);
                inflateSpecialOffers2(arr);
            } catch (JSONException e) {
                e.printStackTrace();
            }}else {

            }
        }
        //Setting Spinner options
    /*    ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_row, getResources().getStringArray(R.array.quantity));
        selectQuantity.setAdapter(spinnerCountShoesArrayAdapter);*/
    }

  public void  quantityadddata(){
        String  d=quantityadd.getText().toString();
        total=Integer.parseInt(d);
        int add=total+1;
        String num=String.valueOf(add);
        quantityadd.setText(num);

    }

    public void  quantityremove(){
        String  d=quantityadd.getText().toString();
      /*  total=Integer.parseInt(d);
        String num=String.valueOf(total);*/
        if(d.equals("1")){

        }else {
            total=Integer.parseInt(d);
            int add=total-1;
            String num=String.valueOf(add);
            quantityadd.setText(num);


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





    //Method to inflate special options 1
    public void inflateSpecialOffers1(JSONArray arr) {

        try {
            //Code to Inflating Options
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jobj = (JSONObject) arr.get(i);
                inflateUiForSingleItem1(jobj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void Sizeset(JSONArray arr) {
            TextView txt = new TextView(this);
            txt.setText("Size - ");
            txt.setTextColor(Color.parseColor("#37474f"));
            txt.setTextSize(16);


            llDynamicLayout.addView(txt);

            try {


                JSONArray jsonArray = new JSONArray(Constants.Size);
                int j = jsonArray.length();
                final RadioButton[] rb = new RadioButton[j];
                RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                rg.setOrientation(RadioGroup.HORIZONTAL);
                rg.setPadding(0, 10, 0, 10);


                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject itemJson;
                    itemJson = jsonArray.getJSONObject(i);
                    Itemsdata itemsdata = new Itemsdata();
                    String id = itemJson.getString("id");
                    itemsdata.setid(itemJson.getString("id"));
                    rb[i] = new RadioButton(this);
                    rb[i].setText(itemJson.getString("size_name"));
                    rb[i].setTextSize(15);
                    rb[i].setId(i + 100);
                    rg.addView(rb[i]);
                    if (i == 0) {

                        rb[0].setChecked(true);
                        price.setText(" Rs " + itemJson.getString("size_price"));
                        nameitem.setText(itemJson.getString("size_name") + "  " + getIntent().getExtras().getString("ITEM_NAME"));
                        Size = itemJson.getString("size_name");
                    }
                    rb[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Size = "";
                                price.setText(" Rs " + itemJson.getString("size_price"));
                                nameitem.setText(itemJson.getString("size_name") + "  " + getIntent().getExtras().getString("ITEM_NAME"));
                                Size = itemJson.getString("size_name");

                                String t=itemJson.getJSONArray("Toppings").toString();
                                llDynamicLayout.removeView(llSpace1);
                                llDynamicLayout.removeView(txt1);
                                llDynamicLayout.removeView(TopingForSinglePizza);
                                itemBase.clear();
                                itemBasePrice.clear();
                                itemPizzaIndex.clear();
                                inflateSpecialOffers2(itemJson.getJSONArray("Toppings"));

                            } catch (JSONException f) {

                            }

                        }
                    });
                }
                llDynamicLayout.addView(rg);

            } catch (JSONException e) {
                e.printStackTrace();
            }


    }
        public void inflateSpecialOffers2(JSONArray arr) {

        llSpace1 = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40, 15);
        llSpace1.setLayoutParams(lp);
            llDynamicLayout.addView(llSpace1);
         txt1 = new TextView(this);
       // txt1.setText("Select Option");
        txt1.setTextColor(Color.parseColor("#37474f"));
        txt1.setTextSize(16);
        llDynamicLayout.addView(txt1);
        TopingForSinglePizza = new GridLayout(this);
        TopingForSinglePizza.setColumnCount(4);
        TopingForSinglePizza.setOrientation(GridLayout.HORIZONTAL);
        llDynamicLayout.addView(TopingForSinglePizza);

        try {
            if(arr.length()>0){
                txt1.setText("Select Option");
            }
            for (int i = 0; i < arr.length(); i++) {

                JSONObject jobj = (JSONObject) arr.get(i);
                inflateUiForSingleItem2(jobj, i,0,TopingForSinglePizza);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public  void Base(){

        String data=getIntent().getExtras().getString("Base");
        if(getIntent().getExtras().getString("Base").equals("")||getIntent().getExtras().getString("Base").equals("[]")){

        }
        else {
            TextView bb = new TextView(this);
            //bb.setText("Base - ");
            bb.setTextColor(Color.parseColor("#37474f"));
            bb.setTextSize(16);
            llDynamicLayout.addView(bb);

            try {
                JSONArray jsonArraybase = new JSONArray(getIntent().getExtras().getString("Base"));
                int j=jsonArraybase.length();
                final RadioButton[] rb = new RadioButton[j];
                RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                rg.setOrientation(RadioGroup.HORIZONTAL);
                for(int i = 0; i < jsonArraybase.length(); i++){
                    final   JSONObject itemJson;
                    itemJson = jsonArraybase.getJSONObject(i);
                    Itemsdata itemsdata=new Itemsdata();
                    String id=itemJson.getString("id");
                    itemsdata.setid(itemJson.getString("id"));
                    rb[i] = new RadioButton(this);
                    rb[i].setText(itemJson.getString("base_name"));
                    rb[i].setTextSize(10);
                    rb[i].setId(i + 100);
                    rg.addView(rb[i]);
                    if(i==0){

                        rb[0].setChecked(true);
                        Base=itemJson.getString("base_name");
                    }
                    rb[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                Base="";
                                Base=itemJson.getString("base_name");
                                //Constants.Basesize=Base;
                               /* itembasesize.remove(itemJson.getString("base_name"));
                                itemBase.add(Base);*/

                            }
                            catch (Exception f){

                            }

                        }
                    });
                }
                llDynamicLayout.addView(rg);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    public void inflateSpecialOffers3(JSONArray arr) {

        LinearLayout llSpace = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40, 15);
        llSpace.setLayoutParams(lp);

        llDynamicLayout.addView(llSpace);

if(!Constants.subItem3.equals("")){
    TextView txt = new TextView(this);
    txt.setText("Free Topping");
    txt.setTextColor(Color.parseColor("#37474f"));
    txt.setTextSize(16);

    llDynamicLayout.addView(txt);
}

        GridLayout TopingForSinglePizza = new GridLayout(this);
        TopingForSinglePizza.setColumnCount(4);
        TopingForSinglePizza.setOrientation(GridLayout.HORIZONTAL);
        llDynamicLayout.addView(TopingForSinglePizza);

        try {



            for (int i = 0; i < arr.length(); i++) {
                JSONObject jobj = (JSONObject) arr.get(i);
                inflateUiForSingleItem3(jobj, i,0,TopingForSinglePizza);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void inflateSpecialOffers4(JSONArray arr) {

        LinearLayout llSpace = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40, 15);
        llSpace.setLayoutParams(lp);

        llDynamicLayout.addView(llSpace);


        TextView txt = new TextView(this);
        txt.setText("Special Instructions");
        txt.setTextColor(Color.parseColor("#37474f"));
        txt.setTextSize(16);

        llDynamicLayout.addView(txt);
        GridLayout TopingForSinglePizza = new GridLayout(this);
        TopingForSinglePizza.setColumnCount(8);
        TopingForSinglePizza.setOrientation(GridLayout.HORIZONTAL);
        llDynamicLayout.addView(TopingForSinglePizza);

        try {


            int j=arr.length();
            final RadioButton[] rb = new RadioButton[j];
            RadioGroup rg = new RadioGroup(this); //create the RadioGroup
            rg.setOrientation(RadioGroup.HORIZONTAL);
            for (int i = 0; i < arr.length(); i++) {

                {
                    final   JSONObject itemJson;

                    itemJson = arr.getJSONObject(i);
                    Itemsdata itemsdata=new Itemsdata();
                   // String id=itemJson.getString("id");
                    //itemsdata.setid(itemJson.getString("id"));
                    rb[i] = new RadioButton(this);
                    rb[i].setText(itemJson.getString("Name"));
                    rb[i].setTextSize(13);
                    rb[i].setId(i + 100);
                    rg.addView(rb[i]);
                    if(i==0){

                        rb[0].setChecked(true);
                        itemspecial.add(itemJson.getString("Name"));

                    }
                    rb[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{

                                itemspecial.remove(itemJson.getString("Name"));


                            }
                            catch (Exception f){

                            }

                        }
                    });
                }
            }


            llDynamicLayout.addView(rg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String printSpaces(int a){
        String spaces="";
        for(int i=0;i<a;i++){
            spaces=spaces+" ";
        }
        return spaces;
    }

    //Method to inflate UI for single Item 1
    public void inflateUiForSingleItem2(final JSONObject jObj, final int i, final int pizzaIndex,GridLayout ll_dynamic) {
        LinearLayout ll = new LinearLayout(this);

        try {

          final   CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setText(jObj.getString("item_acc_name"));
            checkBox.setTextColor(Color.parseColor("#37474f"));
            checkBox.setTextSize(12);
            ll_dynamic.addView(checkBox);
            TextView price = new TextView(this);
            price.setTextSize(10);
            price.setTextColor(Color.parseColor("#37474f"));

            if(jObj.getString("sub_price").equals("0.00")){
                price.setText("   ");
            }
            else
            {
                price.setText(" Rs"+jObj.getString("sub_price"));

            }
            ll_dynamic.addView(price);






            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {




                    if (isChecked) {


                        try {
                            ID.add(jObj.getString("item_acc_name").toString().replaceAll("-"," ")+" Rs "+jObj.getString("sub_price").toString());
                           // ID.add(jObj.getString("id").toString());
                            itemBase.add(jObj.getString("item_acc_name").toString().replaceAll("-"," "));
                            itemBasePrice.add(jObj.getString("sub_price").toString());
                            itemPizzaIndex.add(Integer.toString(pizzaIndex).toString());

                           /* int toping =Integer.parseInt(No_toping);

                            if(No_toping.equals("0")){

                                openBottomSheet(jObj.getString("id").toString(),jObj.getString("item_acc_name").toString(),jObj.getString("sub_price").toString(),Integer.toString(pizzaIndex).toString(),toping);

                            }else {


                                int i = itemBase.size();

                                if (i < toping) {

                                    openBottomSheet(jObj.getString("id").toString(), jObj.getString("item_acc_name").toString(), jObj.getString("sub_price").toString(), Integer.toString(pizzaIndex).toString(), toping);
                                  *//*  ID.add(jObj.getString("id"));
                                    itemBase.add(jObj.getString("item_acc_name"));
                                    itemBasePrice.add(jObj.getString("sub_price"));
                                    itemPizzaIndex.add(Integer.toString(pizzaIndex));*//*
                                } else {

                                    popup(No_toping);
                                    checkBox.setChecked(false);
                                }
                            }




*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            ID.remove(jObj.getString("item_acc_name").toString().replaceAll("-"," ")+" Rs "+jObj.getString("sub_price").toString());
                                itemBase.remove(jObj.getString("item_acc_name").replaceAll("-"," "));
                                itemBasePrice.remove(jObj.getString("sub_price"));
                                itemPizzaIndex.remove(Integer.toString(pizzaIndex));



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






    //Method to inflate UI for single Item 1
    public void inflateUiForSingleItem3(final JSONObject jObj, final int i, final int pizzaIndex,GridLayout ll_dynamic) {
        LinearLayout ll = new LinearLayout(this);

        try {

            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setText(jObj.getString("free_topping_name"));
            checkBox.setTextColor(Color.parseColor("#37474f"));
            checkBox.setTextSize(10);
            ll_dynamic.addView(checkBox);
            TextView price = new TextView(this);
            price.setTextSize(10);

            price.setTextColor(Color.parseColor("#37474f"));
            // llDynamicLayout.addView(checkBox);

            ll_dynamic.addView(price);
           // checkBox.setChecked(true);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        try {
                            itembasesize.add(jObj.getString("free_topping_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            itembasesize.remove(jObj.getString("free_topping_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void openBottomSheet( final String id,final String name,final String price,final String pizaa,final  int t) {
        View view = getLayoutInflater().inflate(R.layout.botton_sheet, null);
        final Dialog mBottomSheetDialog = new Dialog(AddToCart.this);
        mBottomSheetDialog.setContentView(view);
        TextView t1=(TextView)view.findViewById(R.id.textView2) ;
        TextView t2=(TextView)view.findViewById(R.id.textView3) ;
        TextView t3=(TextView)view.findViewById(R.id.textView4) ;
        TextView t4=(TextView)view.findViewById(R.id.textView5) ;
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();

        int p=t-ID.size();
        if(p==1){
        t2.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
        }
        if(p==2){

            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
        }
        if(p==3){
            t4.setVisibility(View.GONE);

        }

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int k=1;
                ID.add(id);
                itemBase.add(name);
                itemBasePrice.add(price);
                itemPizzaIndex.add(pizaa);
                mBottomSheetDialog.dismiss();

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=2;

                    for(int j=0;j<k;j++){
                        ID.add(id);
                        itemBase.add(name);
                        itemBasePrice.add(price);
                        itemPizzaIndex.add(pizaa);
                    }
                    mBottomSheetDialog.dismiss();




            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=3;
                for(int j=0;j<k;j++){
                    ID.add(id);
                    itemBase.add(name);
                    itemBasePrice.add(price);
                    itemPizzaIndex.add(pizaa);
                }
                mBottomSheetDialog.dismiss();

            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=4;
                for(int j=0;j<k;j++){
                    ID.add(id);
                    itemBase.add(name);
                    itemBasePrice.add(price);
                    itemPizzaIndex.add(pizaa);
                }

                mBottomSheetDialog.dismiss();


            }
        });

    }
    public void inflateUiForSingleItem4(final JSONObject jObj, final int i, final int pizzaIndex,GridLayout ll_dynamic) {
        LinearLayout ll = new LinearLayout(this);


    }
    //Method to inflate UI for single Item 1
    public void inflateUiForSingleItem1(JSONObject jObj) {

        LinearLayout ll = new LinearLayout(this);
        GridLayout ToppingFirst = new GridLayout(this);
        ToppingFirst.setColumnCount(4);
        ToppingFirst.setOrientation(GridLayout.HORIZONTAL);

        GridLayout ToppingSecond = new GridLayout(this);
        ToppingSecond.setColumnCount(4);
        ToppingSecond.setOrientation(GridLayout.HORIZONTAL);

        GridLayout ToppingThird = new GridLayout(this);
        ToppingThird.setColumnCount(4);
        ToppingThird.setOrientation(GridLayout.HORIZONTAL);
        //Inflating Item Name
        TextView txtName = new TextView(this);
        try {
            //Adding Linear Layout for spacing purpose
            LinearLayout llSpace = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50, 15);
            llSpace.setLayoutParams(lp);
            llDynamicLayout.addView(llSpace);
            txtName.setText(jObj.getString("Category"));
            txtName.setTextColor(Color.parseColor("#37474f"));
            txtName.setTextSize(16);
            llDynamicLayout.addView(txtName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Inflating Spinner
        try {


            int spinnerCount = Integer.parseInt(jObj.getString("number_of_choice"));

            int toppingStatus = Integer.parseInt(jObj.getString("toppings"));
            String sizeOfPizza = jObj.getString("size");
            for (int i = 0; i < spinnerCount; i++) {
                Spinner sp = new Spinner(this);
                JSONArray jArray;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    jArray = jObj.getJSONArray("subitem");
                    String subItems[] = new String[jArray.length()];
                    for (int j = 0; j < jArray.length(); j++) {
                        JSONObject json = (JSONObject) jArray.get(j);
                        if(sizeOfPizza.equals("null"))
                            subItems[j] = json.getString("sub_item_name");
                        else
                            subItems[j] = json.getString("sub_item_name") + " " + sizeOfPizza;

                        //subItems[j] = json.getString("sub_item_name");
                    }


                    //Adding Linear Layout for spacing purpose
                    LinearLayout llSpace = new LinearLayout(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50, 15);
                    llSpace.setLayoutParams(lp);

                    //Setting Spinner options
                    ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_row, subItems);
                    llDynamicLayout.addView(llSpace);
                    sp.setAdapter(spinnerCountShoesArrayAdapter);
                }
                llDynamicLayout.addView(sp);
                if(i==0)
                    llDynamicLayout.addView(ToppingFirst);
                if(i==1)
                    llDynamicLayout.addView(ToppingSecond);
                if(i==2)
                    llDynamicLayout.addView(ToppingThird);
                if(toppingStatus != 0){
                    JSONArray jArray2 =  new JSONArray(Constants.subItem2);
                    flagsetr=false;
                    int pizzaIndex = i ;
                    try {
                        //Code to Inflating Options
                        for (int toppingIndex = 0;toppingIndex  < jArray2.length(); toppingIndex++) {
                            JSONObject jTopping = (JSONObject) jArray2.get(toppingIndex);
                            if(i==0)
                                inflateUiForSingleItem2(jTopping,toppingIndex,(pizzaIndex+1),ToppingFirst);
                            inflateUiForSingleItem3(jTopping,toppingIndex,(pizzaIndex+1),ToppingFirst);
                            if(i==1)
                                inflateUiForSingleItem2(jTopping,toppingIndex,(pizzaIndex+1),ToppingSecond);
                            if(i==2)
                                inflateUiForSingleItem2(jTopping,toppingIndex,(pizzaIndex+1),ToppingThird);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Log.d("ATCart:inflateUiFSItem1","toppingStatus");
                }



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Method to get Sub Items
    public String getSubItems() {

        //String to hold all Texts Selected
        String subItem = "";
        Spinner sp = new Spinner(this);

        for (int index = 0; index < ((ViewGroup) llDynamicLayout).getChildCount(); index++) {

            if (llDynamicLayout.getChildAt(index) instanceof Spinner) {
                sp = (Spinner) llDynamicLayout.getChildAt(index);

                //Get Text
                if (subItem.equals("")) {
                    subItem = sp.getSelectedItem().toString();
                } else {
                    subItem = subItem + " , " + sp.getSelectedItem().toString();
                }
            }
        }


        return subItem;
    }


    public String getBaseString() {
        String baseString = new String();
        if (itemBase.size() > 0) {
            for (int i = 0; i < itemBase.size(); i++) {
                if (baseString.equals("")) {

                    baseString = itemBase.get(i);

                } else {
                    baseString = baseString + "," + itemBase.get(i);
                }
            }
        } else {

            baseString="";
        }


        return baseString;
    }
    public String getfreetoping() {
        String baseString = new String();
        if (itembasesize.size() > 0) {
            for (int i = 0; i < itembasesize.size(); i++) {
                if (baseString.equals("")) {

                    baseString = itembasesize.get(i);

                } else {
                    baseString = baseString + "," + itembasesize.get(i);
                }
            }
        } else {

            baseString="";
        }


        return baseString;
    }


    public String getspecial_instruction() {
        String baseString = new String();
        if (itemspecial.size() > 0) {
            for (int i = 0; i < itemspecial.size(); i++) {
                if (baseString.equals("")) {

                    baseString = itemspecial.get(i);

                } else {
                    baseString = baseString + "," + itemspecial.get(i);
                }
            }
        } else {

            baseString="";
        }


        return baseString;
    }

   public  String getBase() {
       String baseString = new String();
       if (Base.equals("")) {
           baseString="";

       } else {
           baseString = Base;

       }
       return baseString;

   }

    public  String getid() {
        String basePrice = new String();
        if(ID.size()>0){
            for (int i = 0; i < ID.size(); i++) {
                if (basePrice.equals("")) {
                    basePrice = ID.get(i);
                } else {
                    basePrice = basePrice + "," + ID.get(i);
                }
            }
        }else{
            basePrice="";
        }
        return basePrice;
    }


    public  void popup(String Messges){

        LayoutInflater inflater = LayoutInflater.from(AddToCart.this);
        View view = inflater.inflate(R.layout.custom_booking_tiffin, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        textView.setText("Dear Customer \nYou Can Choose Only "+Messges+" Toppings");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Toppings");
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



    public  String getSize() {
        String baseString = new String();
        if (Size.equals("")) {
            baseString="";

        } else {
            baseString = Size;

        }
        return baseString;

    }


    public String getBasePrice() {
        String basePrice = new String();
        if(itemBasePrice.size()>0){
            for (int i = 0; i < itemBasePrice.size(); i++) {
                if (basePrice.equals("")) {
                    basePrice = itemBasePrice.get(i);
                } else {
                    basePrice = basePrice + "," + itemBasePrice.get(i);
                }
            }
        }else{
            basePrice="";
        }
        return basePrice;
    }
    public String getBasePizzaIndex(){
        String baseString = new String();
        if (itemPizzaIndex.size() > 0) {
            for (int i = 0; i < itemPizzaIndex.size(); i++) {
                if (baseString.equals("")) {
                    baseString = itemPizzaIndex.get(i);
                } else {
                    baseString = baseString + "," + itemPizzaIndex.get(i);
                }
            }
        } else {
            //Do Nothing There is no any Base Available
            baseString="";
        }

        return baseString;
    }

    //Method to reference3 XML widgets
    public void init() {
        btnAddToCart = (TextView) findViewById(R.id.btn_add_to_cart);
        /*selectQuantity = (Spinner) findViewById(R.id.spinner_quantity_add_to_cart);*/
        edtCockingInstruction = (EditText) findViewById(R.id.edt_cocking_instructions);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llDynamicLayout = (LinearLayout) findViewById(R.id.ll_dynamic_layout);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llContactUs = (LinearLayout) findViewById(R.id.ll_contact_us);
        llCheckOut = (LinearLayout) findViewById(R.id.ll_check_out);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llFindOut= (LinearLayout) findViewById(R.id.ll_find_on_map);
        add=(ImageView)findViewById(R.id.add);
        remove=(ImageView)findViewById(R.id.remove);
        quantityadd=(TextView)findViewById(R.id.num);
        price=(TextView)findViewById(R.id.ITEM_PRICE);
        nameitem= (TextView)findViewById(R.id.ITEM_name);
        login=(TextView) findViewById(R.id.logintext);
        imageView=(ImageView)findViewById(R.id.login);
    }

    //Click Listener of Widgets
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_to_cart: {
                DataBaseHelper dbHelper = new DataBaseHelper(this);
                String name=getBaseString();

                OrderItemModel isExist = dbHelper.getSingleItem(getIntent().getExtras().getString("ITEM_ID"), getIntent().getExtras().getString("PARENT_CATEGORY_NAME"), getSubItems(), getBaseString());
                if(!Size.equals("")){
                    isExist=null;
           }


                if (isExist != null) {
                    int quantity;
                 /*   quantity = Integer.parseInt(isExist.getItemQuantity()) + Integer.parseInt(selectQuantity.getSelectedItem().toString());*/
                    quantity = Integer.parseInt(isExist.getItemQuantity()) + Integer.parseInt(quantityadd.getText().toString());
                    isExist.setItemQuantity("" + quantity);
                    dbHelper.upDateOrder(isExist);
                } else {
                    OrderItemModel item = new OrderItemModel();
                    String rr=getIntent().getExtras().getString("ITEM_PRICE");
                    String cc=getIntent().getExtras().getString("ITEM_NAME");
                    String dd=getIntent().getExtras().getString("ITEM_ID");
                    item.setItemId(getIntent().getExtras().getString("ITEM_ID"));
                    item.setItemQuantity(quantityadd.getText().toString());

                    if(Size.equals("")){
                        item.setItemName(nameitem.getText().toString());
                        item.setItemPrice(price.getText().toString().replaceAll("Rs", " "));
                    }else {
                        item.setItemName(nameitem.getText().toString());
                        //item.setItemPrice(getIntent().getExtras().getString("ITEM_PRICE"));
                        item.setItemPrice(price.getText().toString().replaceAll("Rs", " "));
                    }
                    if(getIntent().getExtras().getString("PARENT_CATEGORY_NAME")==null){
                        item.setItemParentCategory(" ");
                    }
                   else {

                        if(Size.equals("")){
                            item.setItemParentCategory(getIntent().getExtras().getString("PARENT_CATEGORY_NAME"));
                        }else {
                            item.setItemParentCategory("");
                        }

                    }
                    item.setSubItems(getSubItems());
                    item.setItemBase(getBaseString());
                    item.setItemBasePrice(getBasePrice());
                    item.setItemBasePizzaIndex(getBasePizzaIndex());
                    item.setBase(getBase());
                    item.setSize(getSize());
                    item.setID(getid());
                    item.setFree_toppings(getfreetoping());
                    item.setSpecial_instruction(getspecial_instruction());
                    item.setSpecialTips(edtCockingInstruction.getText().toString());
                    dbHelper.addItemToOrder(item);
                }
                Constants cons = new Constants();
                txtQuantity.setText(cons.getQuantity(this));
                this.finish();
            }
            break;

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
              /*  Intent in = new Intent(this, FindOnMapActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);*/
            }break;

            case R.id.ll_check_out: {
                Intent in = new Intent(this, CheckOut.class);
                in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(in);
            }
            break;
            case R.id.remove: {
                quantityremove();
            }
            break;
            case R.id.add: {
                quantityadddata();
            }
            break;
        }
    }


}
