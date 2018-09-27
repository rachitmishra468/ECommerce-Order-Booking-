package com.uk.restaurant.GreenVich.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uk.restaurant.GreenVich.R;
import com.uk.restaurant.GreenVich.activity_fragments.CheckOut;
import com.uk.restaurant.GreenVich.database.DataBaseHelper;
import com.uk.restaurant.GreenVich.model_classes.OrderItemModel;
import com.uk.restaurant.GreenVich.utill.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.uk.restaurant.GreenVich.activity_fragments.CheckOut.orderCatRecycler;

public class CheckOutAdapter extends RecyclerView .Adapter<CheckOutAdapter.OrderViewHolder> {

    Context context;
    public ArrayList<OrderItemModel> orderCatList;

    public CheckOutAdapter(Context context, ArrayList<OrderItemModel> orderCatList) {
        this.context = context;
        this.orderCatList = orderCatList;
    }

    @Override
    public int getItemCount() {

        return orderCatList.size();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_out_row, parent, false);
        OrderViewHolder vh = new OrderViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {
        OverScrollDecoratorHelper.setUpOverScroll(orderCatRecycler, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        List<String> pizzaIndex = new ArrayList<String>(Arrays.asList(orderCatList.get(position).getItemBasePizzaIndex().split(",")));
        List<String> pizzaIndexForTopping = new ArrayList<String>(pizzaIndex);
        boolean pizza0=false,pizza1 = false,pizza2 = false,pizza3 = false;
        if(pizzaIndex.contains("0") || pizzaIndex.contains("1"))
        {String[] itemBase = orderCatList.get(position).getItemBase().split(",");
            String[] itemBasePrice = orderCatList.get(position).getItemBasePrice().split(",");
            //For Only Single Pizza
            if(pizzaIndex.contains("0")){
                String itemBase0 = "";
                String itemBasePrice0 = "";
                if (orderCatList.get(position).getSubItems().equals("")) {

               } else {
                    holder.llSetMeal.setVisibility(View.VISIBLE);
                    String[] temp =  orderCatList.get(position).getSubItems().split(",");
                    String temp1="";
                    for(int j=0;j<temp.length;j++)
                        temp1+=" + " + temp[j] + "\n";
                    holder.setMeal.setText(temp1);
                }

                //Setting Toppings
                for (int j=(pizzaIndexForTopping).indexOf("0");j<(pizzaIndexForTopping).lastIndexOf("0")+1;j++){
                    itemBase0+=" + " + itemBase[j] + "\n";
                    if(itemBasePrice[j].equals("0.00"))
                        itemBasePrice0+=" " + "\n";
                    else
                        itemBasePrice0+="Rs " + itemBasePrice[j] + "\n";
                }
                holder.llItemBase1.setVisibility(View.VISIBLE);
                holder.txtItemBaseName1.setText(itemBase0);
                holder.txtItemBasePrice1.setText(itemBasePrice0);

                pizzaIndex.remove("0");
                pizza0 = true;
            }else{

                //For First Pizza (More Then One Pizza)
                if((pizzaIndex).contains("1")){
                    String itemBase1 = "";
                    String itemBasePrice1 = "";

                    if(pizza1 == false){
                        holder.llSetMeal1.setVisibility(View.VISIBLE);
                        String meal1=orderCatList.get(position).getSubItems().split(",")[0];
                        holder.setMeal1.setText("+ " + meal1);
                    }

                    //Setting Toppings For Subitem 1
                    for (int j=(pizzaIndexForTopping).indexOf("1");j<(pizzaIndexForTopping).lastIndexOf("1")+1;j++){
                        itemBase1+=" + " + itemBase[j] + "\n";
                        if(itemBasePrice[j].equals("0.00"))
                            itemBasePrice1+=" " + "\n";
                        else
                            itemBasePrice1+="Rs " + itemBasePrice[j] + "\n";
                    }
                    holder.llItemBase1.setVisibility(View.VISIBLE);
                    holder.txtItemBaseName1.setText(itemBase1);
                    holder.txtItemBasePrice1.setText(itemBasePrice1);
                    //Set Subitems
                    if (orderCatList.get(position).getSubItems().equals("")) {
                        //Do Nothing
                    } else {
                        //Visible Topping Name
                        holder.llSetMeal.setVisibility(View.VISIBLE);
                        String[] temp =  orderCatList.get(position).getSubItems().split(",");
                        String temp1="";
                        for(int j=1;j<temp.length;j++)
                            temp1+=" + " + temp[j] + "\n";
                        holder.setMeal.setText(temp1);
                    }

                    pizzaIndex.remove("1");
                    pizza1 = true;
                }
                // For Second Pizza
                if(pizzaIndex.contains("2")){
                    String itemBase2="";
                    String itemBasePrice2="";

                    if(pizza2 == false){
                        // Set SubItem 2 Name
                        holder.llSetMeal2.setVisibility(View.VISIBLE);
                        String meal1=orderCatList.get(position).getSubItems().split(",")[1];
                        holder.setMeal2.setText("+ " + meal1);
                    }

                    //Setting Toppings For Subitem 2
                    for (int j=(pizzaIndexForTopping).indexOf("2");j<(pizzaIndexForTopping).lastIndexOf("2")+1;j++){
                        itemBase2+=" + " + itemBase[j] + "\n";
                        if(itemBasePrice[j].equals("0.00"))
                            itemBasePrice2+=" " + "\n";
                        else
                            itemBasePrice2+="Rs  " + itemBasePrice[j] + "\n";
                    }
                    holder.llItemBase2.setVisibility(View.VISIBLE);
                    holder.txtItemBaseName2.setText(itemBase2);
                    holder.txtItemBasePrice2.setText(itemBasePrice2);
                    //Set Subitems
                    if (orderCatList.get(position).getSubItems().equals("")) {
                        //Do Nothing
                    } else {
                        //Visible Topping Name
                        holder.llSetMeal.setVisibility(View.VISIBLE);
                        String[] temp =  orderCatList.get(position).getSubItems().split(",");
                        String temp1="";
                        for(int j=2;j<temp.length;j++)
                            temp1+=" + " + temp[j] + "\n";
                        holder.setMeal.setText(temp1);
                    }

                    pizzaIndex.remove("2");
                    pizza2 = true;
                }
                // For Third Pizza
                if(pizzaIndex.contains("3")){

                    String itemBase3 = "";
                    String itemBasePrice3 = "";

                    if(pizza3 == false){
                        holder.llSetMeal3.setVisibility(View.VISIBLE);
                        String meal3=orderCatList.get(position).getSubItems().split(",")[2];
                        holder.setMeal3.setText("+ " + meal3);
                    }

                    //Setting Toppings For Subitem 1
                    for (int j=(pizzaIndexForTopping).indexOf("3");j<(pizzaIndexForTopping).lastIndexOf("3")+1;j++){
                        itemBase3+=" + " + itemBase[j] + "\n";
                        if(itemBasePrice[j].equals("0.00"))
                            itemBasePrice3+=" " + "\n";
                        else
                            itemBasePrice3+="Rs " + itemBasePrice[j] + "\n";
                    }
                    holder.llItemBase3.setVisibility(View.VISIBLE);
                    holder.txtItemBaseName3.setText(itemBase3);
                    holder.txtItemBasePrice3.setText(itemBasePrice3);

                    //Set Subitems
                    if (orderCatList.get(position).getSubItems().equals("")) {
                        //Do Nothing
                    } else {
                        //Visible Topping Name
                        holder.llSetMeal.setVisibility(View.VISIBLE);
                        String[] temp =  orderCatList.get(position).getSubItems().split(",");
                        String temp1="";
                        for(int j=3;j<temp.length;j++)
                            temp1+=" + " + temp[j] + "\n";
                        holder.setMeal.setText(temp1);
                    }

                    pizzaIndex.remove("3");
                    pizza3 = true;
                }

            }




        }

        holder.itemIndex.setText(" " + (position + 1));
        if(orderCatList.get(position).getItemParentCategory().equals("")){
            holder.itemName.setText( orderCatList.get(position).getItemName());
        }else{
            holder.itemName.setText(orderCatList.get(position).getItemParentCategory() +""+ orderCatList.get(position).getItemName());
        }

        holder.itemQuantity.setText(orderCatList.get(position).getItemQuantity());
        holder.itemPrice.setText("Rs" + roundOffTo2DecPlaces((new Float(orderCatList.get(position).getItemPrice()) * (new Float(orderCatList.get(position).getItemQuantity())))));



        if(pizza0==false && pizza1 == false && pizza2 == false && pizza3 == false){
            if (orderCatList.get(position).getSubItems().equals("")) {
                holder.llSetMeal.setVisibility(View.GONE);
                holder.setMeal1.setVisibility(View.GONE);
                holder.setMeal2.setVisibility(View.GONE);
                holder.setMeal3.setVisibility(View.GONE);
                holder.llItemBase.setVisibility(View.GONE);
                holder.llItemBase1.setVisibility(View.GONE);
                holder.llItemBase2.setVisibility(View.GONE);
                holder.llItemBase3.setVisibility(View.GONE);
                //Do Nothing
            } else {
                holder.setMeal1.setVisibility(View.GONE);
                holder.setMeal2.setVisibility(View.GONE);
                holder.setMeal3.setVisibility(View.GONE);
                holder.llItemBase.setVisibility(View.GONE);
                holder.llItemBase1.setVisibility(View.GONE);
                holder.llItemBase2.setVisibility(View.GONE);
                holder.llItemBase3.setVisibility(View.GONE);
                //Visible Topping Name
                holder.llSetMeal.setVisibility(View.VISIBLE);
                holder.setMeal.setText("+ " + orderCatList.get(position).getSubItems().replaceAll(",", "\n" + "+"));
            }

//        if (orderCatList.get(position).getItemBase().equals("")) {
//            //Do Nothing There is no any Base Available
//        } else {
//            holder.llItemBase.setVisibility(View.VISIBLE);
//            holder.txtItemBaseName.setText(" + " + orderCatList.get(position).getItemBase().replaceAll(",", "\n" + " + "));
//            holder.txtItemBasePrice.setText("£ " + getModifiedItemPrice(Integer.parseInt(orderCatList.get(position).getItemQuantity()),orderCatList.get(position).getItemBasePrice()).replaceAll(",", "\n" + "£ "));
//        }

        }

        int i= Integer.parseInt(orderCatList.get(position).getItemQuantity());




        if(i>1){

            holder.deleteimg.setImageResource(R.drawable.minus);


        }
        //Click Listener of Delete Button
        holder.llDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* DataBaseHelper dbHendler = new DataBaseHelper(context);
                dbHendler.deleteSingleItem(orderCatList.get(position));
                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);

                //Setting data to ui Widgets
                Bundle bundle = CheckOut.dbHelper.getItemQuantity();
                CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));

                Constants cons = new Constants();
                CheckOut.txtQuantity.setText(cons.getQuantity(context));*/
                {



                    int i= Integer.parseInt(orderCatList.get(position).getItemQuantity());

                    if(i>1){


                        ((CheckOut)context).popuplpogpout(orderCatList.get(position).getItemName().toString()+" was remove 1 quantity to your Order");
                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                        String sss=orderCatList.get(position).getItemId();
                        String dd= orderCatList.get(position).getItemParentCategory();
                        OrderItemModel isExist = dbHendler.getSingleItem(orderCatList.get(position).getItemId(), orderCatList.get(position).getItemParentCategory(),"", "");

                        int quantity;
                        try {


                            quantity = Integer.parseInt(orderCatList.get(position).getItemQuantity());
                            quantity = quantity - 1;
                            isExist.setItemQuantity("" + quantity);
                            String d=isExist.getItemQuantity();



                            dbHendler.upDateOrder(isExist);

                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                            orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);


                            Bundle bundle = CheckOut.dbHelper.getItemQuantity();
                            CheckOut.totalPrice.setText("Rs" + bundle.getString("TotalPrice"));

                            Constants cons = new Constants();
                            CheckOut.txtQuantity.setText(cons.getQuantity(context));

                        }
                        catch (Exception e){


                            try {
                                OrderItemModel query = dbHendler.getSingleItemupudate(orderCatList.get(position).getItemId());
                                quantity = Integer.parseInt(orderCatList.get(position).getItemQuantity());
                                quantity = quantity - 1;
                                query.setItemQuantity("" + quantity);
                                String d = query.getItemQuantity();


                                dbHendler.upDateOrder(query);

                                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);


                                Bundle bundle = CheckOut.dbHelper.getItemQuantity();
                                CheckOut.totalPrice.setText("Rs" + bundle.getString("TotalPrice"));

                                Constants cons = new Constants();
                                CheckOut.txtQuantity.setText(cons.getQuantity(context));

                            }catch (Exception e1){


                                ((CheckOut)context).popuplpogpout(orderCatList.get(position).getItemName().toString()+" was remove to your Order");
                                dbHendler = new DataBaseHelper(context);
                                dbHendler.deleteSingleItem(orderCatList.get(position));
                                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);

                                //Setting data to ui Widgets
                                Bundle bundle = CheckOut.dbHelper.getItemQuantity();
                                CheckOut.totalPrice.setText("Rs" + bundle.getString("TotalPrice"));

                                Constants cons = new Constants();
                                CheckOut.txtQuantity.setText(cons.getQuantity(context));
                            }



                        }



                    }else {


                        ((CheckOut)context).popuplpogpout(orderCatList.get(position).getItemName().toString()+" was remove  to your Order");
                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                        dbHendler.deleteSingleItem(orderCatList.get(position));
                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                        orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                        Bundle bundle = CheckOut.dbHelper.getItemQuantity();
                        CheckOut.totalPrice.setText("Rs" + bundle.getString("TotalPrice"));
                        Constants cons = new Constants();
                        CheckOut.txtQuantity.setText(cons.getQuantity(context));

                    }

               /* DataBaseHelper dbHendler = new DataBaseHelper(context);
                dbHendler.deleteSingleItem(orderCatList.get(position));
                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);

                //Setting data to ui Widgets
                Bundle bundle = CheckOut.dbHelper.getItemQuantity();
                CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));

                Constants cons = new Constants();
                CheckOut.txtQuantity.setText(cons.getQuantity(context));*/
                }
            }
        });
    }

    //View Holder Class for Category Product
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        Button btnCancel, btnDelete;
        ImageView deleteimg;
        LinearLayout llSetMeal, llDeleteItem, llItemBase,llSetMeal1,llItemBase1,llSetMeal2,llItemBase2,llSetMeal3,llItemBase3;
        public TextView itemIndex, itemName, itemQuantity, itemPrice, setMeal, txtItemBaseName, txtItemBasePrice;
        public TextView setMeal1, txtItemBaseName1, txtItemBasePrice1,setMeal2, txtItemBaseName2, txtItemBasePrice2,setMeal3, txtItemBaseName3, txtItemBasePrice3;

        public OrderViewHolder(View view) {
            super(view);
            itemIndex = (TextView) view.findViewById(R.id.txt_item_index_check_out);
            itemName = (TextView) view.findViewById(R.id.txt_item_name_check_out);
            itemQuantity = (TextView) view.findViewById(R.id.txt_item_quantity_check_out);
            itemPrice = (TextView) view.findViewById(R.id.txt_item_price_check_out);
            setMeal = (TextView) view.findViewById(R.id.txt_set_meal);
            llSetMeal = (LinearLayout) view.findViewById(R.id.ll_set_meal);
            llDeleteItem = (LinearLayout) view.findViewById(R.id.ll_remove_item);
            llItemBase = (LinearLayout) view.findViewById(R.id.ll_item_base);
            txtItemBaseName = (TextView) view.findViewById(R.id.txt_item_base_name);
            txtItemBasePrice = (TextView) view.findViewById(R.id.txt_item_base_price);
            llItemBase1 = (LinearLayout) view.findViewById(R.id.ll_item_base1);
            txtItemBaseName1 = (TextView) view.findViewById(R.id.txt_item_base_name1);
            txtItemBasePrice1 = (TextView) view.findViewById(R.id.txt_item_base_price1);
            setMeal1 = (TextView) view.findViewById(R.id.txt_set_meal1);
            llSetMeal1 = (LinearLayout) view.findViewById(R.id.ll_set_meal1);
            llItemBase2 = (LinearLayout) view.findViewById(R.id.ll_item_base2);
            txtItemBaseName2 = (TextView) view.findViewById(R.id.txt_item_base_name2);
            txtItemBasePrice2 = (TextView) view.findViewById(R.id.txt_item_base_price2);
            setMeal2 = (TextView) view.findViewById(R.id.txt_set_meal2);
            llSetMeal2 = (LinearLayout) view.findViewById(R.id.ll_set_meal2);
            llItemBase3 = (LinearLayout) view.findViewById(R.id.ll_item_base3);
            txtItemBaseName3 = (TextView) view.findViewById(R.id.txt_item_base_name3);
            txtItemBasePrice3 = (TextView) view.findViewById(R.id.txt_item_base_price3);
            setMeal3 = (TextView) view.findViewById(R.id.txt_set_meal3);
            llSetMeal3 = (LinearLayout) view.findViewById(R.id.ll_set_meal3);
            deleteimg=(ImageView)view.findViewById(R.id.deleteimg) ;
        }
    }

    //Round up to two places
    String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }

    //Method to get BaseItem Price According To Quantity
    public String getModifiedItemPrice(int quantity,String str){
        String newPrice=new String();
        float f=0.0f;

        if(str.equals("")){
            //Do Nothing There is No SubItemPrice
        }else{
            str = str + ",";
            str = str.replaceAll(",", "-");
            while (!str.equals("")) {
                f = Float.parseFloat(str.substring(0, str.indexOf("-")))*quantity;
                str = str.substring(str.indexOf("-") + 1, str.length());

                if(newPrice.equals("")){
                    newPrice=""+f;
                }else{
                    newPrice=newPrice+","+f;
                }
            }
        }
        return newPrice;
    }
}





/*extends RecyclerView.Adapter<CheckOutAdapter.OrderViewHolder> {

    Context context;
    public ArrayList<OrderItemModel> orderCatList;

    public CheckOutAdapter(Context context, ArrayList<OrderItemModel> orderCatList) {
        this.context = context;
        this.orderCatList = orderCatList;
    }

    @Override
    public int getItemCount() {
        return orderCatList.size();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_out_row, parent, false);
        OrderViewHolder vh = new OrderViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {

        OrderItemModel item = orderCatList.get(position);

        holder.itemIndex.setText(" " + (position + 1));

        if(orderCatList.get(position).getItemParentCategory().equals("")){
            holder.itemName.setText(orderCatList.get(position).getItemName());
        }else{
            holder.itemName.setText(orderCatList.get(position).getItemParentCategory() + " - " + orderCatList.get(position).getItemName());
        }

        holder.itemQuantity.setText(orderCatList.get(position).getItemQuantity());
        holder.itemPrice.setText("£" + roundOffTo2DecPlaces((new Float(orderCatList.get(position).getItemPrice()) * (new Float(orderCatList.get(position).getItemQuantity())))));

        if (orderCatList.get(position).getSubItems().equals("")) {
            //Do Nothing
        } else {
            //Visible Topping Name
            holder.llSetMeal.setVisibility(View.VISIBLE);
            holder.setMeal.setText("+ " + orderCatList.get(position).getSubItems().replaceAll(",", "\n" + "+"));
        }

        if (orderCatList.get(position).getItemBase().equals("")) {
            //Do Nothing There is no any Base Available
        } else {
            holder.llItemBase.setVisibility(View.VISIBLE);
            holder.txtItemBaseName.setText(" + " + orderCatList.get(position).getItemBase().replaceAll(",", "\n" + " + "));
            String procr=  getModifiedItemPrice(Integer.parseInt(orderCatList.get(position).getItemQuantity()),orderCatList.get(position).getItemBasePrice()).replaceAll(",", "\n" + "£ ");
            if(procr.equals("£0.00")){
                holder.txtItemBasePrice.setText("  ");
            }else
            {
                holder.txtItemBasePrice.setText("£ " + getModifiedItemPrice(Integer.parseInt(orderCatList.get(position).getItemQuantity()),orderCatList.get(position).getItemBasePrice()).replaceAll(",", "\n" + "£ "));
            }
        }


        int i= Integer.parseInt(orderCatList.get(position).getItemQuantity());




        if(i>1){

            holder.delet.setImageResource(R.drawable.minus);


        }





        holder.llDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int i= Integer.parseInt(orderCatList.get(position).getItemQuantity());

          if(i>1){




    DataBaseHelper dbHendler = new DataBaseHelper(context);
              String sss=orderCatList.get(position).getItemId();
    String dd= orderCatList.get(position).getItemParentCategory();
    OrderItemModel isExist = dbHendler.getSingleItem(orderCatList.get(position).getItemId(), orderCatList.get(position).getItemParentCategory(),"", "");

    int quantity;
try {


    quantity = Integer.parseInt(orderCatList.get(position).getItemQuantity());
    quantity = quantity - 1;
    isExist.setItemQuantity("" + quantity);
    String d=isExist.getItemQuantity();



    dbHendler.upDateOrder(isExist);

    CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
    CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);


    Bundle bundle = CheckOut.dbHelper.getItemQuantity();
    CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));

    Constants cons = new Constants();
    CheckOut.txtQuantity.setText(cons.getQuantity(context));

}
catch (Exception e){


   try {
       OrderItemModel query = dbHendler.getSingleItemupudate(orderCatList.get(position).getItemId());
       quantity = Integer.parseInt(orderCatList.get(position).getItemQuantity());
       quantity = quantity - 1;
       query.setItemQuantity("" + quantity);
       String d = query.getItemQuantity();
       dbHendler.upDateOrder(query);
       CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
       CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
       Bundle bundle = CheckOut.dbHelper.getItemQuantity();
       CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));
       Constants cons = new Constants();
       CheckOut.txtQuantity.setText(cons.getQuantity(context));

   }catch (Exception e1){
       dbHendler = new DataBaseHelper(context);
       dbHendler.deleteSingleItem(orderCatList.get(position));
       CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
       CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
       //Setting data to ui Widgets
       Bundle bundle = CheckOut.dbHelper.getItemQuantity();
       CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));
       Constants cons = new Constants();
       CheckOut.txtQuantity.setText(cons.getQuantity(context));
   }


   *//* dbHendler = new DataBaseHelper(context);
    dbHendler.deleteSingleItem(orderCatList.get(position));
    CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
    CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);

    //Setting data to ui Widgets
    Bundle bundle = CheckOut.dbHelper.getItemQuantity();
    CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));

    Constants cons = new Constants();
    CheckOut.txtQuantity.setText(cons.getQuantity(context));*//*
}



    }else {

    DataBaseHelper dbHendler = new DataBaseHelper(context);
    dbHendler.deleteSingleItem(orderCatList.get(position));
    CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
    CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);

    //Setting data to ui Widgets
    Bundle bundle = CheckOut.dbHelper.getItemQuantity();
    CheckOut.totalPrice.setText("£" + bundle.getString("TotalPrice"));

    Constants cons = new Constants();
    CheckOut.txtQuantity.setText(cons.getQuantity(context));

}




              *//*  int quantity;
                 *//**//*   quantity = Integer.parseInt(isExist.getItemQuantity()) + Integer.parseInt(selectQuantity.getSelectedItem().toString());*//**//*
                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                isExist.setItemQuantity("" + quantity);
                dbHelper.upDateOrder(isExist);*//*












        }
        });
        }

//View Holder Class for Category Product
public class OrderViewHolder extends RecyclerView.ViewHolder {
    Button btnCancel, btnDelete;
    ImageView delet;
    LinearLayout llSetMeal, llDeleteItem, llItemBase;
    public TextView itemIndex, itemName, itemQuantity, itemPrice, setMeal, txtItemBaseName, txtItemBasePrice;

    public OrderViewHolder(View view) {
        super(view);
        itemIndex = (TextView) view.findViewById(R.id.txt_item_index_check_out);
        itemName = (TextView) view.findViewById(R.id.txt_item_name_check_out);
        itemQuantity = (TextView) view.findViewById(R.id.txt_item_quantity_check_out);
        itemPrice = (TextView) view.findViewById(R.id.txt_item_price_check_out);
        setMeal = (TextView) view.findViewById(R.id.txt_set_meal);
        llSetMeal = (LinearLayout) view.findViewById(R.id.ll_set_meal);
        llDeleteItem = (LinearLayout) view.findViewById(R.id.ll_remove_item);
        llItemBase = (LinearLayout) view.findViewById(R.id.ll_item_base);
        txtItemBaseName = (TextView) view.findViewById(R.id.txt_item_base_name);
        txtItemBasePrice = (TextView) view.findViewById(R.id.txt_item_base_price);
        delet=(ImageView)view.findViewById(R.id.delete_icon);
    }
}

    //Round up to two places
    String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }

    //Method to get BaseItem Price According To Quantity
    public String getModifiedItemPrice(int quantity,String str){
        String newPrice=new String();
        float f=0.0f;

        if(str.equals("")){
            //Do Nothing There is No SubItemPrice
        }else{
            str = str + ",";
            str = str.replaceAll(",", "-");
            while (!str.equals("")) {

                f = Float.parseFloat(str.substring(0, str.indexOf("-")))*quantity;
                str = str.substring(str.indexOf("-") + 1, str.length());
                if(newPrice.equals("")){

                    newPrice=""+String.format("%.2f", f);
                }else{

                    newPrice=newPrice+","+String.format("%.2f", f);
                }
            }
        }
        return newPrice;
    }
}*/