package com.uk.restaurant.GreenVich.model_classes;

/**
 * Created by Developer on 03-06-2016.
 */
public class ItemModel {

    String itemId, itemOrder, catId, subCatId, itemName,out_of_stock, itemSmallDesc, itemImage, itemVisivility, itemPrice , foodStyle, itemStatus, itemFullDescription,parentCatName,category,Size,Base,Has_subitem,no_of_toppings;
    public String getSize() {
        return Size;
    }
    public void setSize(String Size) {
        this.Size = Size;
    }
    public String getno_of_toppings() {
        return no_of_toppings;
    }
    public void setno_of_toppings(String no_of_toppings) {
        this.no_of_toppings = no_of_toppings;
    }
    public String getHas_subitem() {
        return Has_subitem;
    }
    public void setHas_subitem(String Has_subitem) {
        this.Has_subitem = Has_subitem;
    }
    public String getBase() {
        return Base;
    }
    public void setBase(String Base) {
        this.Base = Base;
    }
    public String getcategory() {
        return category;
    }
    public void setcategory(String category) {
        this.category = category;
    }
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemOrder() {
        return itemOrder;
    }
    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }
    public String getCatId() {
        return catId;
    }
    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSmallDesc() {
        return itemSmallDesc;
    }

    public void setItemSmallDesc(String itemSmallDesc) {
        this.itemSmallDesc = itemSmallDesc;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemVisivility() {
        return itemVisivility;
    }

    public void setItemVisivility(String itemVisivility) {
        this.itemVisivility = itemVisivility;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getFoodStyle() {
        return foodStyle;
    }

    public void setFoodStyle(String foodStyle) {
        this.foodStyle = foodStyle;
    }
    public String getout_of_stock() {
        return out_of_stock;
    }

    public void setout_of_stock(String out_of_stock) {
        this.out_of_stock = out_of_stock;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemFullDescription() {
        return itemFullDescription;
    }

    public void setItemFullDescription(String itemFullDescription) {
        this.itemFullDescription = itemFullDescription;
    }

    public String getParentCatName() {
        return parentCatName;
    }

    public void setParentCatName(String parentCatName) {
        this.parentCatName = parentCatName;
    }
}
