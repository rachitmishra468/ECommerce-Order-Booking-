package com.uk.restaurant.GreenVich.utill;
import android.content.Context;
import android.os.Bundle;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.ItemModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Constants {
    public static HashMap<String, String> addressMap;
    public static String time;
    public static String status;
    public static String totalAmount;
    public static  String dis;
    public static  String tifine;
    public static String minimum_delivery_amount;
    public static String offer_amount;
    public static String imei,charges;
    public static boolean OrderDisabled=false;
    public static String postal="";
    public static String HomeText = "";
    public  static ArrayList<String> tiffinname;
    public  static ArrayList<String> desription_of_tiffin;
    public  static ArrayList<String> price_of_tiffin;
    public static String name_with_price;
    public  static ArrayList<String> nameplusprice;
    public  static ArrayList<String> stringname_price;

  /*  public static String SubItemAPI = "http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/sub_items.php?";
    public static String ForgotPasswordAPI = "http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/reset_pass_new_api.php";
    public static String OrderPlacingAPI = "http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/order_complete_api.php";
    public static String check_postcode_api = "http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/check_postcode_api.php";
    public static String ShopStatusAPI = "http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/checkout_timing.php";
    public static String AllData = "http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/all_item_android_api.php";
    public static String LogInAPI="http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/login_new_api.php";
    public static String RegisterAPI="http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/register_api.php";
    public static String Resetpassword="http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/reset_pass_new_api.php";
    public static String info="http://www.satyamtechnologies.co.uk/kanhaiyabhog/apps_data/info_android_api.php";*/
    public static Map<String, ArrayList<ItemModel>> completeData;
    public static String subItem1, subItem2,subItem3,subItem4;

    public static String Size;
    public static String Transaction_id = "";
    public static String paymentStatus = "offline";
    public static String userData;
    public static boolean isUserLogIn1=false;

    public static String infotime;
    public static boolean isUserLogIn;
    public static boolean isUserLogou;
    public static boolean isDataLoaded = false;
    public static boolean isSharingEnable=false;
    public static boolean isCartNotificationEnable=false;
    public  static boolean isClickToCallEnabled=false;
    public String getQuantity(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        Bundle bundle = helper.getItemQuantity();
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "";
        }
    }
}
