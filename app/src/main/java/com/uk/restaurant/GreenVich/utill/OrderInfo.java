package com.uk.restaurant.GreenVich.utill;

import android.content.Context;

import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.OrderItemModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Android on 6/18/2016.
 */
public class OrderInfo {

    Context context;

    public OrderInfo(Context context) {
        this.context = context;
    }

    public JSONObject getOrder() {
        JSONObject jobj = new JSONObject();

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        ArrayList<OrderItemModel> itemList = new ArrayList<OrderItemModel>();
        itemList = dbHelper.getOrder();
        String header = "items: [\n";

        //Forming String to the Order Items
        String completeString = new String();
        String item = new String();
        for (int i = 0; i < itemList.size(); i++) {

            String[] itemBase = itemList.get(i).getItemBase().split(",");
            String[] pizzaDetails=itemList.get(i).getItemBasePizzaIndex().split(",");
            String topping = "";

            if(Arrays.asList(pizzaDetails).contains("0")){
                topping = "\"offer_item_1_topping\"" + ":" + "-" +getBaseItemsPrice(Arrays.toString(Arrays.copyOfRange(itemBase,Arrays.asList(pizzaDetails).indexOf("0"),Arrays.asList(pizzaDetails).lastIndexOf("0")+1)).replace("]","").substring(1),itemList.get(i).getItemBasePrice(),Integer.parseInt(itemList.get(i).getItemQuantity()))  + "-,\n";
                topping+="\"offer_item_2_topping\"" + ":" + "-" +" "+"-,\n" + "\"offer_item_3_topping\"" + ":" + "-" + " "+"-,\n";
            }

            if(Arrays.asList(pizzaDetails).contains("1")){
                topping = "\"offer_item_1_topping\"" + ":" + "-" +getBaseItemsPrice(Arrays.toString(Arrays.copyOfRange(itemBase,Arrays.asList(pizzaDetails).indexOf("1"),Arrays.asList(pizzaDetails).lastIndexOf("1")+1)).replace("]","").substring(1),itemList.get(i).getItemBasePrice(),Integer.parseInt(itemList.get(i).getItemQuantity()))  + "-,\n";
            }else if(!(Arrays.asList(pizzaDetails).contains("0"))){
                topping="\"offer_item_1_topping\"" + ":" + "-"+" "+"-,\n" + "\"offer_item_2_topping\"" + ":" + "-" +" "+"-,\n" + "\"offer_item_3_topping\"" + ":" + "-" + " "+"-,\n";
            }

            if(Arrays.asList(pizzaDetails).contains("2")){
                topping+="\"offer_item_2_topping\"" + ":" + "-" +getBaseItemsPrice(Arrays.toString(Arrays.copyOfRange(itemBase,Arrays.asList(pizzaDetails).indexOf("2"),Arrays.asList(pizzaDetails).lastIndexOf("2")+1)).replace("]","").substring(1),itemList.get(i).getItemBasePrice(),Integer.parseInt(itemList.get(i).getItemQuantity()))  + "-,\n";
            }else if(Arrays.asList(pizzaDetails).contains("1")){
                topping+="\"offer_item_2_topping\"" + ":" + "-" +" "+"-,\n" + "\"offer_item_3_topping\"" + ":" + "-" + " "+"-,\n";
            }

            if(Arrays.asList(pizzaDetails).contains("3")){
                topping+="\"offer_item_3_topping\"" + ":" + "-" +getBaseItemsPrice(Arrays.toString(Arrays.copyOfRange(itemBase,Arrays.asList(pizzaDetails).indexOf("3"),Arrays.asList(pizzaDetails).lastIndexOf("3")+1)).replace("]","").substring(1),itemList.get(i).getItemBasePrice(),Integer.parseInt(itemList.get(i).getItemQuantity()))  + "-,\n";
            }else if(Arrays.asList(pizzaDetails).contains("1") && Arrays.asList(pizzaDetails).contains("2")) {
                topping+="\"offer_item_3_topping\"" + ":" + "-" + " "+"-,\n";
            }

            item = new String();
            item = "{" + "\"item_name\"" + ":" + "-" +itemList.get(i).getItemParentCategory()+"  "+ itemList.get(i).getItemName() + "-,\n" +
                    "\"sub_item\"" + ":" + "-" + itemList.get(i).getSubItems().trim() + "-,\n" +
                    "\"item_id\"" + ":" + "-" + itemList.get(i).getItemId() + "-,\n" +
                    "\"base\"" + ":" + "-" + itemList.get(i).getBase() + "-,\n" +
                    "\"instruction\"" + ":" + "-" + itemList.get(i).getSpecialTips() + "-,\n" +
                    "\"price\"" + ":" + "-" + itemList.get(i).getItemPrice() + "-,\n" +
                    topping.trim() +
                    "\"quantity\"" + ":" + "-" + itemList.get(i).getItemQuantity() + "-\n" +
                    "}";

            if (completeString.equals("")) {
                completeString = completeString + item;
            } else {
                completeString = completeString + "," + item;
            }
        }

        completeString = completeString.replace("-", "\"");
        String finalString = header + completeString + "\n]";

        finalString = "{" + finalString + "}";

        JSONObject jsonArray = new JSONObject();
        try {
            jsonArray = new JSONObject(finalString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    //Method to create Address Json Object
    public JSONObject getAddress() {
        JSONObject jobj = new JSONObject();
        String address = new String();
        address = "{" + "\"full_name\"" + ":\""  + Constants.addressMap.get("Full_name").replaceAll("\"", "\\\"") + "\",\n" +
                "\"mail_id\"" + ":\""  + Constants.addressMap.get("Email_id") + "\",\n" +
                "\"user_id\"" + ":\""  + Constants.addressMap.get("User_id") + "\",\n" +
                "\"contact_no\"" + ":\""  + Constants.addressMap.get("Contact_no") + "\",\n" +
                "\"house_no\"" + ":\""  + Constants.addressMap.get("House") + "\",\n" +
                "\"street\"" + ":\""  + Constants.addressMap.get("Street") + "\",\n" +
                "\"city\"" + ":\"" + Constants.addressMap.get("City") + "\",\n" +
                "\"state\"" + ":\""  + Constants.addressMap.get("State") + "\",\n" +
                "\"post\"" + ":\""  + Constants.addressMap.get("Post_code") + "\",\n" +
                "\"delivery_detail\"" + ":\""  + Constants.addressMap.get("Delivery_detail") + "\"\n" +
                "}";

//        address = address.replace("-", "\"");

        try {
            jobj = new JSONObject(address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jobj;
    }

    //Method to get Price of Base Items
    public String getBaseItemsPrice(String baseItem, String basePrice,int quantity) {

        String baseItemPrice=new String();

        String price = basePrice;
        if (basePrice.equals("")) {
            //Do Nothing There is no BaseItemPrice
        } else {
            baseItem=baseItem+",";
            basePrice = basePrice + ",";
            baseItem=baseItem.replaceAll(",","-");
            basePrice = basePrice.replaceAll(",", "-");
            while (!baseItem.equals("")) {

                if(baseItemPrice.equals("")){
                    baseItemPrice =baseItem.substring(0, baseItem.indexOf("-"))+" "+roundOffTo2DecPlaces((Float.parseFloat(basePrice.substring(0, basePrice.indexOf("-")))*quantity));
                }else{
                    baseItemPrice =baseItemPrice+","+baseItem.substring(0, baseItem.indexOf("-"))+" "+roundOffTo2DecPlaces((Float.parseFloat(basePrice.substring(0, basePrice.indexOf("-")))*quantity));
                }

                baseItem = baseItem.substring(baseItem.indexOf("-") + 1, baseItem.length());
                basePrice = basePrice.substring(basePrice.indexOf("-") + 1, basePrice.length());
            }
        }
        String str=baseItemPrice;
        return baseItemPrice;
    }

    //Round up to two places
    String roundOffTo2DecPlaces(float val) {

        return " £ "+String.format("%.2f", val);
    }

}
