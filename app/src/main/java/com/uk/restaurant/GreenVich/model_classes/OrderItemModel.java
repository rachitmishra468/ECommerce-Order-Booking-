package com.uk.restaurant.GreenVich.model_classes;

/**
 * Created by Developer on 04-06-2016.
 */
public class OrderItemModel {
    String key,
            itemId,
            itemName,
            itemPrice,ID,
            itemQuantity,itemParentCategory,
            subItems,itemBase,itemBasePrice,
            specialTips,  itemBasePizzaIndex
            ,Base,Size,Free_toppings,Special_instruction
            ;

    public String getFree_toppings() {
        return Free_toppings;
    }

    public void setFree_toppings(String Free_toppings) {
        this.Free_toppings = Free_toppings;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getSpecial_instruction() {
        return Special_instruction;
    }

    public void setSpecial_instruction(String Special_instruction) {
        this.Special_instruction = Special_instruction;
    }









    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getBase() {
        return Base;
    }

    public void setBase(String Base) {
        this.Base = Base;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemParentCategory() {
        return itemParentCategory;
    }

    public void setItemParentCategory(String itemParentCategory) {
        this.itemParentCategory = itemParentCategory;
    }



    public String getItemBasePizzaIndex() {
        return itemBasePizzaIndex;
    }

    public void setItemBasePizzaIndex(String itemBasePizzaIndex) {
        this.itemBasePizzaIndex = itemBasePizzaIndex;
    }

    public String getSubItems() {
        return subItems;
    }

    public void setSubItems(String subItems) {
        this.subItems = subItems;
    }

    public String getItemBase() {
        return itemBase;
    }

    public void setItemBase(String itemBase) {
        this.itemBase = itemBase;
    }

    public String getItemBasePrice() {
        return itemBasePrice;
    }

    public void setItemBasePrice(String itemBasePrice) {
        this.itemBasePrice = itemBasePrice;
    }

    public String getSpecialTips() {
        return specialTips;
    }

    public void setSpecialTips(String specialTips) {
        this.specialTips = specialTips;
    }
}
