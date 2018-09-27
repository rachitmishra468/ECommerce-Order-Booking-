package com.uk.restaurant.GreenVich.utill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.uk.restaurant.GreenVich.activity_fragments.AboutUs;
import com.uk.restaurant.GreenVich.activity_fragments.AddToCart;
import com.uk.restaurant.GreenVich.activity_fragments.CheckOut;
import com.uk.restaurant.GreenVich.activity_fragments.DeliveryOption;
import com.uk.restaurant.GreenVich.activity_fragments.HomeActivity;
import com.uk.restaurant.GreenVich.activity_fragments.LogIn;
import com.uk.restaurant.GreenVich.activity_fragments.Menu;
import com.uk.restaurant.GreenVich.activity_fragments.OrderReviewPage;
import com.uk.restaurant.GreenVich.activity_fragments.SplashActivity;
import com.uk.restaurant.GreenVich.activity_fragments.TiffinBookingActivity;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Developer on 02-06-2016.
 */
public class VolleyMethods {
    Context context;

    ProgressDialog pDialog;
    public  String SubItemAPI = "http://www.appwebtechnologies.com/greenvich/apps_data/sub_items.php";
    public  String ForgotPasswordAPI = "http://www.appwebtechnologies.com/greenvich/apps_data/reset_pass_new_api.php";
    public  String OrderPlacingAPI = "http://www.appwebtechnologies.com/greenvich/apps_data/order_complete_api.php";
    public  String check_postcode_api = "http://www.appwebtechnologies.com/greenvich/apps_data/check_postcode_api.php";
    public  String ShopStatusAPI = "http://www.appwebtechnologies.com/greenvich/apps_data/checkout_timing.php";
    public  String AllData = "http://www.appwebtechnologies.com/greenvich/apps_data/all_item_android_api.php";
    public  String LogInAPI="http://www.appwebtechnologies.com/greenvich/apps_data/login_new_api.php";
    public  String RegisterAPI="http://www.appwebtechnologies.com/greenvich/apps_data/register_new_api.php";
    public  String Resetpassword="http://www.appwebtechnologies.com/greenvich/apps_data/reset_pass_new_api.php";
    public  String info="http://www.appwebtechnologies.com/greenvich/apps_data/info_android_api.php";

    public VolleyMethods(Context context) {
        this.context = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
    }

    //Method to get Item Data
    public void getItem(final String cat_id, final String parentCategoryName, final String gId) {
        String tag_string_req = "string_req";
        String url = LogInAPI;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());

                String res = response.toString();
                try {
                    JSONArray jArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Intent in = new Intent(context, ItemActivity.class);
//                in.putExtra("CatId", cat_id);
//                in.putExtra("ParentCategoryName", parentCategoryName);
//                context.startActivity(in);
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat_id", cat_id);
                params.put("g_id", gId);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

public void getTiffinData(){
    String CategoryRequest = "TiffinServiceCategoryRequest";
    Constants.tiffinname=new ArrayList<>();
    Constants.desription_of_tiffin=new ArrayList<>();
    Constants.price_of_tiffin=new ArrayList<>();
    Constants.name_with_price="";
    Constants.nameplusprice=new ArrayList<>();
    Constants.stringname_price=new ArrayList<>();
    TiffinBookingActivity.valueof_spinner=new ArrayList<>();
    String url = "http://www.appwebtechnologies.com/greenvich/apps_data/tifin_android_api.php";
    Constants.tifine=new String("");
    pDialog.show();
    JsonArrayRequest req = new JsonArrayRequest(url,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (response == null) {
                      /*  if(context instanceof HomeActivity){
                            String d="No data found";
                            ((HomeActivity)context).popup(d);
                        }*/
                        Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
                    } else {
                        try {


                        JSONArray arr = new JSONArray(response.toString());
                            Log.d("TAG@123",response.toString());
                            Constants.tifine=response.toString();
                           if(context instanceof TiffinBookingActivity){
                            String d="No data found";
                            ((TiffinBookingActivity)context).setdata();
                        }
                          /*  for (int i=0;i<arr.length();i++){
                                JSONObject jsonObject= arr.getJSONObject(i);
                                String tiffin_name=jsonObject.getString("tifin_name");
                                String tiffin_description=jsonObject.getString("tifin_description");
                                String tiffin_price=jsonObject.getString("price");
                                Constants.tiffinname.add(tiffin_name);
                                Constants.desription_of_tiffin.add(tiffin_description);
                                Constants.price_of_tiffin.add(tiffin_price);
                                Constants.name_with_price=tiffin_name  +"        Rs.      "+  tiffin_price;
                                TiffinBookingActivity.valueof_spinner.add(Constants.name_with_price);

                            }*/


                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String d="Something went wrong!. Please Try Again";
           /* Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
            Intent in=new Intent(context,HomeActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            ((HomeActivity)context).popup(d);*/

              /*  Intent in=new Intent(context,HomeActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(in);*/
            //   ((Activity)context).finish();
            VolleyLog.d("TAG", "Error: " + error.getMessage());
            pDialog.hide();
        }
    });
    //Adding request to request queue
    AppController.getInstance().addToRequestQueue(req, CategoryRequest);
}





    public void changepasswword(final Map<String,String> userRegisterData) {

        String user_register_req = "user_tablebooking_request";

        String url = ForgotPasswordAPI;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1")){
                        if(context instanceof LogIn){
                            String d=jObj.getString("msg");
                            ((LogIn)context).popuptext(d);
                        }

                    }else{
                        if(context instanceof LogIn){
                            String d=jObj.getString("msg");
                            LogIn.otp="";
                            ((LogIn)context).popup(d);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", userRegisterData.get("User_otp"));
                params.put("password", userRegisterData.get("User_passo"));
                String t=params.toString();
                Log.d("TAG@123", t);
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }



    //Method to get Response of special Cases
    public void getSpecialCases(final ItemModel itemObj,final String category,final String Size,final String Base,final  String number) {
        // Tag used to cancel the request
        final String tag_string_req = "string_req";
        String url = SubItemAPI;
        Constants.subItem1 = new String("");
        Constants.subItem2 = new String("");
        Constants.subItem3 = new String("");
        Constants.subItem4 = new String("");
        Constants.Size = new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());
                String res = response.toString();

                if (!response.toString().equals("")) {
                    //Code to Assigninh Sub Item Values
                    try {
                        JSONObject subItemObj = new JSONObject(response.toString());

                        Constants.subItem1 = subItemObj.getJSONArray("subitem1").toString();
                        //Constants.subItem3 = subItemObj.getJSONArray("free_topping").toString();
                        Constants.subItem2 = subItemObj.getJSONArray("subitem2").toString();
                       // Constants.subItem4 = subItemObj.getJSONArray("special_instructions").toString();
                        Constants.Size = subItemObj.getJSONArray("Size").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Do Nothing
                    Constants.subItem1 = "";
                    Constants.subItem2 = "";
                    Constants.subItem3 = "";
                    Constants.subItem4 = "";
                    Constants.Size = "";
                }


                //Start Add to cart_icon Activity
                Intent in = new Intent(context, AddToCart.class);
                String d=itemObj.getCatId()+itemObj.getItemId()+itemObj.getItemPrice()+itemObj.getItemName()+itemObj.getParentCatName();
                in.putExtra("CAT_ID", itemObj.getCatId());
                in.putExtra("ITEM_ID", itemObj.getItemId());
                in.putExtra("ITEM_PRICE", itemObj.getItemPrice());
                in.putExtra("ITEM_NAME", itemObj.getItemName());
                in.putExtra("PARENT_CATEGORY_NAME", itemObj.getParentCatName());
                in.putExtra("category", category);
                in.putExtra("Size", Size);
                in.putExtra("Base",Base);
                in.putExtra("number",number);
                context.startActivity(in);
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("item_id", itemObj.getItemId());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    //Method to Place Order
    public void placeOrder(final String deliveryMethod, final String paymentMethod,final String time) {
        // Tag used to cancel the request
        String tag_string_req = "string_req";
        String url = OrderPlacingAPI;
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Bundle bundle = dbHelper.getItemQuantity();
        OrderInfo order = new OrderInfo(context);
        String items=order.getOrder().toString();
        String total=bundle.getString("TotalPrice");
        String delivery=deliveryMethod;
        String deli=paymentMethod;
        String addre=order.getAddress().toString();
        pDialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                completeAction(response.toString());
                pDialog.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                ((OrderReviewPage)context).popuptext1("Oops! Please Try Again.");
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                DataBaseHelper dbHelper = new DataBaseHelper(context);
                Bundle bundle = dbHelper.getItemQuantity();
                OrderInfo order = new OrderInfo(context);
                String POUND = "\u00A3";
                Map<String, String> params = new HashMap<String, String>();
                params.put("Items", order.getOrder().toString());
                params.put("GrandTotal", "" + bundle.getString("TotalPrice"));
                params.put("DeliveryType", deliveryMethod);
                params.put("PaymentType", paymentMethod);
                params.put("transaction_id",Constants.Transaction_id);
                params.put("Status",Constants.paymentStatus);
                params.put("Time", time);
                params.put("Address",order.getAddress().toString());
                String data=params.toString();
                VolleyLog.d("TAG@123", data);
                Log.d("TAG@123", data);
                return params;

            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }






    public void getStoreStatus() {
        pDialog.show();
        String ShopStatus = "shop_status_status";
        String url = ShopStatusAPI;
        Constants.dis=new String("");
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    Constants.time= jsonResponse.getString("checkInTime");
                    DeliveryOption.checkInTime = jsonResponse.getString("checkInTime");
                    DeliveryOption.checkOutTime = jsonResponse.getString("checkOutTime");
                    DeliveryOption.CollectionCycle = jsonResponse.getString("CollectionCycle");
                    DeliveryOption.DeliveryCycle = jsonResponse.getString("DeliveryCycle");
                    Constants.dis= jsonResponse.getString("discount").toString();
                    CheckOut.shopStatus = jsonResponse.getString("response");
                    Constants.status = jsonResponse.getString("response");
                    if(jsonResponse.getString("OrderDisabled").toString().equals("1")){
                        Constants.OrderDisabled=true;
                    }else {
                        Constants.OrderDisabled=false;
                    }
                    pDialog.hide();

                }catch (JSONException r)   {
                    pDialog.hide();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, ShopStatus);
    }

    //Method to parsing Item
    public ArrayList<ItemModel> parseItem(String catName,JSONArray itemJsonArray,String cateName) {
        ArrayList<ItemModel> itemList = new ArrayList<ItemModel>();

        JSONArray data;
        for (int i = 0; i < itemJsonArray.length(); i++) {
            JSONObject itemJson;
            JSONArray mJSONArray;
            ItemModel itemModel = null;
            try {

                itemJson = itemJsonArray.getJSONObject(i);
                itemModel = new ItemModel();
                 mJSONArray = itemJson.optJSONArray("base");
               // String dd=mJSONArray.toString();
                if (mJSONArray == null) {
                    itemModel.setcategory(cateName);
                    itemModel.setItemId(itemJson.getString("item_id"));
                    itemModel.setItemOrder(itemJson.getString("item_order"));
                    itemModel.setCatId(itemJson.getString("cat_id"));
                    itemModel.setSubCatId(itemJson.getString("g_id"));
                    itemModel.setItemName(itemJson.getString("item_name").replaceAll("-", " "));
                    itemModel.setFoodStyle(itemJson.getString("is_non_veg"));
                    itemModel.setout_of_stock(itemJson.getString("out_of_stock"));
                    itemModel.setItemSmallDesc(itemJson.getString("small_desc"));
                    itemModel.setItemPrice(itemJson.getString("price"));
                    itemModel.setHas_subitem(itemJson.getString("has_subitem"));
                    itemModel.setBase(itemJson.getString("base").toString());
                    itemModel.setSize(itemJson.getString("size").toString());
                    String food=itemJson.getString("is_non_veg").toString();
                   // itemModel.setno_of_toppings(itemJson.getString("no_of_toppings"));
                    itemModel.setItemStatus(itemJson.getString("status"));
                    if(itemJson.getString("sub_category").equals("")){
                        itemModel.setParentCatName("");
                        if(itemJson.getString("full_desc").equals("")){
                            String de=itemJson.getString("full_desc");
                            itemModel.setItemFullDescription(" ");
                        }else{

                            itemModel.setItemFullDescription(itemJson.getString("full_desc"));
                        }
                    }else{
                        itemModel.setParentCatName(itemJson.getString("sub_category"));
                        if(itemJson.getString("full_desc").equals("")){
                            itemModel.setItemFullDescription(" ");
                        }else{
                            itemModel.setItemFullDescription(itemJson.getString("full_desc"));
                        }
                    }

                }
                else {
                    itemModel.setcategory(cateName);

                    itemModel.setItemId(itemJson.getString("item_id"));
                    itemModel.setItemOrder(itemJson.getString("item_order"));
                    itemModel.setCatId(itemJson.getString("cat_id"));
                    itemModel.setSubCatId(itemJson.getString("g_id"));
                    itemModel.setItemName(itemJson.getString("item_name").replaceAll("-", " "));
                    itemModel.setFoodStyle(itemJson.getString("is_non_veg"));
                    itemModel.setout_of_stock(itemJson.getString("out_of_stock"));
                    itemModel.setItemSmallDesc(itemJson.getString("small_desc"));
                    itemModel.setItemPrice(itemJson.getString("price"));
                    itemModel.setItemStatus(itemJson.getString("status"));


                    itemModel.setHas_subitem(itemJson.getString("has_subitem"));

                    itemModel.setBase(itemJson.getJSONArray("base").toString());
                    String d=itemJson.getJSONArray("base").toString();
                    if(itemJson.getString("sub_category").equals("")){
                        itemModel.setParentCatName(" ");
                        //String de=itemJson.getString("full_desp");
                        if(itemJson.getString("full_desc").equals("")){

                            itemModel.setItemFullDescription(" ");
                        }else{
                            String ddse=itemJson.getString("full_desc");
                            itemModel.setItemFullDescription(itemJson.getString("full_desc"));
                        }
                    }else{

                        itemModel.setParentCatName(itemJson.getString("sub_category"));
                        if(itemJson.getString("full_desc").equals("")){
                            String de=itemJson.getString("full_desc");
                            itemModel.setItemFullDescription(" ");
                        }else{
                            itemModel.setItemFullDescription(itemJson.getString("full_desc"));
                        }
                    }
                    itemModel.setSize(itemJson.getJSONArray("size").toString());
                    mJSONArray=null;

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            itemList.add(itemModel);
        }

        return itemList;
    }


    public void completeAction(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            OrderReviewPage.txtResponse.setText(jsonResponse.getString("msg").toString());
            //OrderReviewPage orderReviewPage=new OrderReviewPage();
           // orderReviewPage.popup(jsonResponse.getString("msg").toString());
            if (jsonResponse.getString("response").toString().equals("1")) {
                //Dalete Cart Values Order is placed Successfullu
                DataBaseHelper dbHelper = new DataBaseHelper(context);
                dbHelper.deleteOrder();

                Constants cons = new Constants();

               // OrderReviewPage.txtQuantity.setText(cons.getQuantity(context));
                ((OrderReviewPage)context).popuptext1(jsonResponse.getString("msg").toString());

            } else {
                ((OrderReviewPage)context).popuptext1(jsonResponse.getString("msg").toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAllData() {

        String CategoryRequest = "CategoryRequest";
        String url = AllData;
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            if(context instanceof HomeActivity){
                                String d="No data found";
                                ((HomeActivity)context).popup(d);
                            }
                            Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
                        } else {
                            Constants.isDataLoaded = true;
                            parseAllData(response);
                            pDialog.hide();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String d="Something went wrong!. Please Try Again";
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Intent in=new Intent(context,HomeActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                ((HomeActivity)context).popup(d);

              /*  Intent in=new Intent(context,HomeActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(in);*/
             //   ((Activity)context).finish();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        });
        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, CategoryRequest);
    }

    //Parsing Data
    public void parseAllData(JSONArray jArray) {

        Constants.completeData = new LinkedHashMap<String, ArrayList<ItemModel>>();

        for (int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject jobj = jArray.getJSONObject(i);
                String CatItems=jobj.getJSONArray("Items").toString();
                String cateName=jobj.getString("category").toString();
                Constants.completeData.put(jobj.getString("category"), parseItem(cateName,jobj.getJSONArray("Items"),cateName));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        getStoreStatus();
    }




    public void postcode(final String postcode) {
        pDialog.show();
        String tag_string_req = "string_req";
        String url =check_postcode_api;
        Constants.charges = new String("");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@1232", response.toString());


                String op=response;
                try{
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("response").toString().equals("1")) {
                        pDialog.hide();
                        Constants.charges=jsonResponse.getString("Delivery_Charge");
                        ((DeliveryOption)context).popup(jsonResponse.getString("msg").toString(),jsonResponse.getString("response").toString());

                    } else {
                        pDialog.hide();
                        ((DeliveryOption)context).popup(jsonResponse.getString("msg").toString(),jsonResponse.getString("response").toString());

                    }
                }catch (JSONException e){


                    e.getMessage().toString();

                }



                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("postcode", postcode);

                String dd=params.toString();
                Log.d("TAG@1232", dd);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    //method for update

    public void update(final String AppName,final String version) {
        pDialog.show();
        String tag_string_req = "update";
        String url ="http://www.satyamtechnologies.co.uk/Dashboard/apps_data/check_app_version_api.php";
        Constants.minimum_delivery_amount=new String("");
        Constants.offer_amount=new String("");
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@1232", response.toString());
                String op=response;
                try{
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("IsHardStop").toString().equals("0")) {
                        pDialog.hide();
                        if(jsonResponse.getString("maintenance").toString().equals("0")){

                            Constants.minimum_delivery_amount=jsonResponse.getString("minimum_delivery_amount");
                            Constants.offer_amount=jsonResponse.getString("offer_amount");
                            Constants.HomeText=jsonResponse.getString("HomeText");
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);final String parm = sharedPreferences.getString("Address", "") ;

                            if(jsonResponse.getString("isSharingEnable").toString().equals("0")){
                                Constants.isSharingEnable=true;
                            }
                            if(jsonResponse.getString("isCartNotificationEnable").toString().equals("0")){
                                Constants.isCartNotificationEnable=true;
                            }

                                    if(jsonResponse.getString("isClickToCallEnabeled").toString().equals("0")){
                                        Constants.isClickToCallEnabled=true;

                                    }
                            if(parm.length()==0){
                                Constants.isUserLogIn=false;
                                context.startActivity(new Intent(context, HomeActivity.class));
                            }
                            else {
                                Constants.userData=parm;
                                Constants.isUserLogIn=true;
                                context.startActivity(new Intent(context, HomeActivity.class));
                            }



                        }else {

                            ((SplashActivity)context).maintenance();

                        }


                    }
                    else {
                        pDialog.hide();


                        if(jsonResponse.getString("maintenance").toString().equals("0")){
                            String PlayStoreVersion=jsonResponse.getString("PlayStoreVersion").toString();
                            ((SplashActivity)context).cartitemsheet(PlayStoreVersion);}

                        else {
                            ((SplashActivity)context).maintenance();
                        }

                    }
                }catch (JSONException e){

                }



                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("AppName", AppName);
                params.put("version", version);
                params.put("os", "Android");
                String dd=params.toString();
                Log.d("TAG@1232", dd);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




















    //Method to LogIn User
    public void userLogIn(final Map<String,String> userLogInData) {
        // Tag used to cancel the request
        String user_log_in_req = "login_req";
        String url = LogInAPI;
//        ItemActivity.itemList=new ArrayList<ItemModel>();
        Constants.userData=new String("");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                //Code To Handle Response
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    if(jsonObject.getString("response").toString().equals("1")){
                        //If email and password is verified from server
                       /* Constants.isUserLogIn=true;
                        Constants.userData=jsonObject.getJSONObject("address").toString();
                        Intent in=new Intent(context, DeliveryOption.class);
                        context.startActivity(in);
                        ((Activity) context).finish();*/
                        if(Constants.isUserLogIn1){
                            Constants.isUserLogIn=true;
                            Constants.userData=jsonObject.getString("address").toString();
                            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("Address",  Constants.userData);
                            editor.commit();
                            Intent in=new Intent(context, Menu.class);
                            context.startActivity(in);

                        }
                        else {
                            Constants.isUserLogIn=true;
                            Constants.isUserLogIn1=true;
                            Constants.userData=jsonObject.getString("address").toString();
                            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("Address",  Constants.userData);
                            editor.commit();
                            Intent in=new Intent(context, DeliveryOption.class);
                            context.startActivity(in);

                        }



                    }else{
                        //If Mail Id Password is not Verified from Server
                        Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyM:userLogIn", "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MailId", userLogInData.get("MailId"));
                params.put("Password", userLogInData.get("Password"));
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_log_in_req);
    }



    //Method to Register User
    public void registerUser(final Map<String,String> userRegisterData) {
        // Tag used to cancel the request
        pDialog.show();
        String user_register_req = "user_register_request";
        String url = RegisterAPI;
//        ItemActivity.itemList=new ArrayList<ItemModel>();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                //Code to Handle Response
                try {
                    JSONObject jObj=new JSONObject(response);
                    if(jObj.getString("response").equals("1")){
                        Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                        Intent in=new Intent(context,LogIn.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(in);
                    }else{
                        //Registration Not complete Response
                        Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("full_name", userRegisterData.get("full_name"));
                params.put("mail_id", userRegisterData.get("mail_id"));
                params.put("password", userRegisterData.get("password"));
                params.put("contact_no", userRegisterData.get("contact_no"));
                params.put("house_no", userRegisterData.get("house_no"));
                params.put("street", userRegisterData.get("street"));
                params.put("city", userRegisterData.get("city"));
                params.put("post", userRegisterData.get("post"));
                params.put("state", userRegisterData.get("state"));
                String hint=params.toString();
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }
    ///////////////table booking///////////




    public void tablebooking(final Map<String,String> userRegisterData) {

        String user_register_req = "user_tablebooking_request";
        String url = Resetpassword;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());


                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1")){
                        Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                        Intent in=new Intent(context,HomeActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(in);
                    }else{

                        Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", userRegisterData.get("name"));
                params.put("email", userRegisterData.get("email"));
                params.put("occasion", userRegisterData.get("occasion"));
                params.put("prefferred_booking", userRegisterData.get("prefferred_booking"));
                params.put("no_of_adults", userRegisterData.get("no_of_adults"));
                params.put("no_of_children", userRegisterData.get("no_of_children"));
                params.put("phone", userRegisterData.get("phone"));
                params.put("selectedDate", userRegisterData.get("selectedDate"));
                params.put("message", userRegisterData.get("message"));
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }
    /*public void resetpasword(final Map<String,String> userRegisterData) {

        String user_register_req = "user_tablebooking_request";
        String url = Constants.Resetpassword;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());


                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1")){
                        if(context instanceof LogIn){
                            String d=jObj.getString("msg");
                            ((LogIn)context).popup(d);
                        }
                       // Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                       *//* Intent in=new Intent(context,HomeActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(in);*//*
                    }else{
                        if(context instanceof LogIn){
                            String d=jObj.getString("msg");
                            ((LogIn)context).popup(d);
                        }
                       // Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userRegisterData.get("User_Email"));

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }*/



    public void resetpasword(final Map<String,String> userRegisterData) {

        String user_register_req = "user_tablebooking_request";
        String url = ForgotPasswordAPI;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());


                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1")){
                        if(context instanceof LogIn){
                            String d=jObj.getString("msg");
                            ((LogIn)context).popup(d);
                        }

                    }else{
                        if(context instanceof LogIn){
                            String d=jObj.getString("msg");
                            ((LogIn)context).popuptext(d);
                        }
                        // Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userRegisterData.get("User_Email"));

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }


    public  void getinfo(){


        String ShopStatus = "shop_status_status";
        String url = info;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                try {

                    Constants.infotime=new String();
                    Constants.infotime=response;
                    ((AboutUs)context).setdayatime();
                    pDialog.hide();
                }catch (Exception r)   {
                    pDialog.hide();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, ShopStatus);




    }
}
