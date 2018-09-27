package com.uk.restaurant.GreenVich.activity_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.utill.AppController;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TiffinBookingActivity extends Activity  implements View.OnClickListener{

    EditText book_name, book_email, book_user_phone, instruction,address;
    Button Submit;
    LinearLayout llHome, llBack;
    public static ArrayList<String> valueof_spinner;
    public static ArrayList<String> price;
    public static ArrayList<String> mes;
    ProgressDialog pDialog;
    TextView text;
    LinearLayout linearspinner;
    LinearLayout llRequest, llResponse;
    TextView responseMessage;
    Spinner spinner;
    public  static  String pricet;
    public static  String   selectedItem;

    public static  String maindata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_booking_tiffin);
        init();
        llBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        setdata();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });



    }

    public void init() {
        text=(TextView)findViewById(R.id.text);
        book_name = (EditText) findViewById(R.id.book_userfull_name);
        book_email = (EditText) findViewById(R.id.book_user_email);
        book_user_phone = (EditText) findViewById(R.id.book_user_phone);
        address = (EditText) findViewById(R.id.book_userfull_Address);
        instruction = (EditText) findViewById(R.id.instruction);
        Submit = (Button) findViewById(R.id.service_submit);
        spinner=(Spinner)findViewById(R.id.spinner);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        VolleyMethods volleyMethods=new VolleyMethods(this);
        volleyMethods.getTiffinData();


    }


    public  void  setdata(){

        try {

            price=new ArrayList<>();
            mes=new ArrayList<>();
            valueof_spinner=new ArrayList<>();
            JSONArray arr = new JSONArray(Constants.tifine);
            for (int i=0;i<arr.length();i++){
                                JSONObject jsonObject= arr.getJSONObject(i);
                                String tiffin_name=jsonObject.getString("tifin_name");
                                String tiffin_description=jsonObject.getString("tifin_description");
                                String tiffin_price=jsonObject.getString("price");
                                Constants.tiffinname.add(tiffin_name);
                                Constants.desription_of_tiffin.add(tiffin_description);
                                Constants.price_of_tiffin.add(tiffin_price);
                                //Constants.name_with_price=tiffin_name  +"        Rs.    "+tiffin_price;
                                valueof_spinner.add(tiffin_name  +"        Rs. "+tiffin_price);
                                mes.add(tiffin_description);
                                price.add(tiffin_price);
                            }


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, valueof_spinner);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {

                    String selectvalue=parent.getItemAtPosition(position).toString();
                    selectedItem = selectvalue.toString();
                    int i=valueof_spinner.indexOf(selectedItem);
                        String maesssges=mes.get(i);
                        pricet=price.get(i);
                        text.setText(maesssges);
                    String sentdata=selectedItem.toString();
                    String[] parts = sentdata.split(" ");
                  String part1 = parts[0];
                    String part2 = parts[1];
maindata=part1+" "+ part2;
                }
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });



        }
        catch(JSONException e){
            e.printStackTrace();
        }



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




        }
    }



    private void validateData() {
        if (book_name.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Name is required", Toast.LENGTH_SHORT).show();
        } else{

            if (book_email.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Email is Required", Toast.LENGTH_SHORT).show();
            } else{

                if (book_user_phone.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Phone no. is Required", Toast.LENGTH_SHORT).show();
                } else{

                    if (address.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter Your Address", Toast.LENGTH_SHORT).show();
                    } else{
                        if (spinner.getSelectedItem().toString().equals(""))  {
                            Toast.makeText(getApplicationContext(), "Select Your Service", Toast.LENGTH_SHORT).show();
                        } else{


                            submitData();
/*
                            String t=spinner.getSelectedItem().toString();
                            String[] parts = t.split(" ");
                            String part1 = parts[0];
                            String part2 = parts[1];
                            String fimal= part1+" "+part2;
                            int i=valueof_spinner.indexOf(t);
                            String maesssges=mes.get(i);
                            String pricet=price.get(i);
                            popuptext(maesssges);
                            // popup(maesssges);*/

                        }

                    }

                }

            }


        }


    }


    public  void popup(String mess){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("");
        builder.setMessage(mess);
        builder.setPositiveButton("S", null);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitData();
                    }
                });
        builder.show();
    }


  private  void submitData()  {
      pDialog = new ProgressDialog(this);
      pDialog.setMessage("Loading...");
        final String TAG = "RAQFragment:SubmitData";
        String api = "http://www.appwebtechnologies.com/greenvich/apps_data/booking_api.php";
        pDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    Log.d("TAG@123", response.toString());
                    JSONObject jObj = new JSONObject(response.toString());

                     if(jObj.getString("response").equals("1")){

                      String toastmsg=jObj.getString("msg").toString();
                        Toast.makeText(getApplicationContext(), ""+toastmsg, Toast.LENGTH_SHORT).show();
                        book_name.setText("");
                        book_email.setText("");
                        book_user_phone.setText("");
                        address.setText("");
                        instruction.setText("");
                        Intent intent=new Intent(TiffinBookingActivity.this,Menu.class);
                        startActivity(intent);
                    }

                    else{
                        Toast.makeText(getApplicationContext(), jObj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    Log.d("TAG@123",e.getMessage());
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG@123",error.getMessage());
                pDialog.dismiss();

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> param = new HashMap<String, String>();
                param.put("full_name",book_name.getText().toString());
                param.put("email",book_email.getText().toString());
                param.put("phone",book_user_phone.getText().toString());
                param.put("address",address.getText().toString());
                param.put("message",instruction.getText().toString());

                param.put("tifin_type",maindata.toString());
                param.put("tifin_price",pricet.toString());
                String s=param.toString();
                Log.d("TAG@123", param.toString());
                return param;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, TAG);
    }
    public  void popuptext(String m){

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.custom_spinner, null);
        final TextView textView=(TextView)view.findViewById(R.id.tv1);
        textView.setText(m);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info..");
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

                submitData();


            }
        });

        builder.show();

    }


}