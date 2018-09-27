package com.uk.restaurant.GreenVich.adapter;

/**
 * Created by Android on 8/6/2016.
 */

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.activity_fragments.Menu;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.ItemModel;
import com.uk.restaurant.GreenVich.model_classes.OrderItemModel;
import com.uk.restaurant.GreenVich.utill.Constants;
import com.uk.restaurant.GreenVich.utill.VolleyMethods;

import static android.view.View.GONE;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<String> category;
    private Map<String, ArrayList<ItemModel>> dataInMap;
    LinearLayout llDynamicLayout;
    public  String Base="";
    public static ImageView imgExpendable;
    public ExpandableListAdapter(Activity context, ArrayList<String> category, Map<String, ArrayList<ItemModel>> dataInMap) {
        this.context = context;
        this.category = category;
        this.dataInMap = dataInMap;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String catName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.catrow_design, null);
        }

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/bbb.ttf");
        LinearLayout itemset=(LinearLayout)convertView.findViewById(R.id.itemname);
        TextView item = (TextView) convertView.findViewById(R.id.txt_cat_name);
        imgExpendable = (ImageView) convertView.findViewById(R.id.img_expend);
        if (groupPosition % 2 == 1) {
            itemset.setBackgroundColor(Color.parseColor("#d7d3d3"));
        } else {
            itemset.setBackgroundColor(Color.parseColor("#d7d3d3"));
        }
        if (isExpanded) {

            imgExpendable.setImageResource(R.drawable.minimize_icon);
        } else {

            imgExpendable.setImageResource(R.drawable.expand_icon);
        }
        item.setTypeface(face);
        item.setText(catName.replace("\r\n",""));
        return convertView;
    }

    //Method to get No of Categories
    public int getGroupCount() {
        return category.size();
    }

    //Method to get Category Name
    public Object getGroup(int groupPosition) {
        return category.get(groupPosition);
    }

    //Method to get Group Position
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //Method to get Group Position
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    //Method to Inflate Child Or Item Data
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ItemModel itemData = (ItemModel) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_row, null);
        }
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");

        TextView itemName = (TextView) convertView.findViewById(R.id.txt_item_name);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.txt_item_price);
        TextView itemDesc = (TextView) convertView.findViewById(R.id.txt_item_desc);
        ImageView foodstyle=(ImageView)convertView.findViewById(R.id.foodstyle);
        final ImageView addItemToCart = (ImageView) convertView.findViewById(R.id.img_add_item);
        final LinearLayout  lv=(LinearLayout)convertView.findViewById(R.id.ll_add_item) ;
        final LinearLayout  basesert=(LinearLayout)convertView.findViewById(R.id.subbase) ;
        String dd=itemData.getItemFullDescription();
        String ddx=itemData.getItemName();
        itemName.setText(itemData.getItemName().replace("\r\n",""));
        itemName.setTypeface(face1);
        final String base=itemData.getBase();
        final  String name=itemData.getcategory();
        final  String no_of_toppings=itemData.getno_of_toppings();
        String foodstyl=itemData.getFoodStyle().toString();
        itemDesc.setText(itemData.getItemSmallDesc());
        final  String discre=itemData.getItemSmallDesc();
        final String size=itemData.getSize();
        final  String sub_item=itemData.getHas_subitem();

        if(foodstyl.isEmpty()){

        }
        else{


            if (foodstyl.equals("1")){
                foodstyle.setImageResource(R.drawable.veg);

            }
            else if(foodstyl.equals("2")){
                foodstyle.setImageResource(R.drawable.non);

            }
            else {
                foodstyle.setImageResource(R.drawable.veg_white);
            }
        }

        basesert.setVisibility(GONE);
        itemName.setTypeface(face1);

        if(itemData.getout_of_stock().toString().equals("1")){
            itemDesc.setTextColor(Color.parseColor("#d70707"));
            itemDesc.setText("Out Of Stock");
            itemDesc.setSelected(true);
        }
        else {

            lv.setClickable(true);
        }



        lv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemData.getout_of_stock().toString().equals("1")) {
                    ((Menu)context).popuplpogpout(itemData.getItemName().toString()+"is Out Of Stock");
                }else
                {
if(sub_item.equals("Yes")){
    VolleyMethods vm = new VolleyMethods(context);
    vm.getSpecialCases((ItemModel) getChild(groupPosition, childPosition),name,size,base,no_of_toppings);
}else {
    basesert.setVisibility(GONE);
    addItemToCart.setImageResource(R.drawable.check);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {


            addItemToCart.setImageResource(R.drawable.add_icon_new);


            if(Constants.isCartNotificationEnable){
                addtocart(itemData.getCatId(),itemData.getItemId(),itemData.getItemPrice(),itemData.getItemName(),itemData.getParentCatName());
                ((Menu)context).popuplpogpout(itemData.getItemName().toString()+" is added to your Order");
            }
            else {
                addtocart(itemData.getCatId(),itemData.getItemId(),itemData.getItemPrice(),itemData.getItemName(),itemData.getParentCatName());
               // Toast.makeText(context,itemData.getParentCatName()+ itemData.getItemName()+" added",Toast.LENGTH_SHORT).show();
                ((Menu)context).popuplpogpout(itemData.getItemName().toString()+" is added to your Order");
            }



        }

    }, 500L);

}}

            }
        });


        String rr=itemData.getParentCatName();
        String ff=itemData.getItemName();
        String rfr=itemData.getParentCatName();
        String dff=itemData.getItemPrice();
        itemPrice.setText("Rs " + itemData.getItemPrice());
        return convertView;
    }

    //Get Total No Of item Count
    public int getChildrenCount(int groupPosition) {
        return dataInMap.get(category.get(groupPosition)).size();
    }

    //Method to Get Item Name
    public Object getChild(int groupPosition, int childPosition) {
        return dataInMap.get(category.get(groupPosition)).get(childPosition);
    }

    //Methos to get IsChild Selectable
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //Methos to get HasStable Id Or not
    public boolean hasStableIds() {
        return true;
    }





    public  void addtocart(String catid,String itemid,String itemprice,String itemname,String parentcatname){


        int i=1;

        DataBaseHelper dbHelper = new DataBaseHelper(context);
       // String name=getBaseString();

        OrderItemModel isExist = dbHelper.getSingleItem(itemid, parentcatname, getSubItems(), getBaseString());


        if (isExist != null) {
            int quantity;
                 /*   quantity = Integer.parseInt(isExist.getItemQuantity()) + Integer.parseInt(selectQuantity.getSelectedItem().toString());*/
            quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
            isExist.setItemQuantity("" + quantity);
            dbHelper.upDateOrder(isExist);
        } else {
            OrderItemModel item = new OrderItemModel();
            String rr=itemprice;
            String cc=itemname;
            item.setItemId(itemid);
            item.setItemQuantity("1");
            item.setItemName(cc);
            item.setItemPrice(rr.replaceAll("Rs", " "));

            if(parentcatname==null){
                item.setItemParentCategory(" ");
            }
            else {
                item.setItemParentCategory(parentcatname);
            }
            item.setSubItems(getSubItems());
            item.setItemBase(getBaseString());
            item.setItemBasePrice(getBasePrice());
            item.setItemBasePizzaIndex(getBasePizzaIndex());
            item.setBase(getBase());
            item.setSize(getSize());
            item.setID(getid());
            item.setFree_toppings("");
            item.setSpecial_instruction("");
            item.setSpecialTips("");
            dbHelper.addItemToOrder(item);
        }
        Constants cons = new Constants();
        Bundle bundle = dbHelper.getItemQuantity();
        ((Menu)context).ll_check.setVisibility(View.VISIBLE);
        ((Menu)context).txtQuantity.setText(cons.getQuantity(context));
        ((Menu)context).price.setText("Rs" + bundle.getString("TotalPrice"));





    }

    public  String getid() {
        String basePrice = new String();

            basePrice="";

        return basePrice;
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

    public String getBasePizzaIndex(){
        String baseString = new String();

            baseString="";

        return baseString;
    }
    public  String getSize() {
        String baseString = new String();

        baseString="";
        return baseString;

    }

    public String getSubItems() {

        //String to hold all Texts Selected
        String subItem = "";



        return subItem;
    }
    public String getBaseString() {
        String baseString = new String();


            baseString="";



        return baseString;
    }


    public String getBasePrice() {
        String basePrice = new String();

            basePrice="";

        return basePrice;
    }

}