package com.example.ashu.wendor;

/**
 * Created by ashu on 21/3/18.
 */

public class CartItems {
    private int itemId;
    private int qtyEntered;
    private int price;
    private String itemName;
    private int totalEach;

    public int getTotalEach() {
        return totalEach;
    }

    public void setTotalEach(int totalEach) {
        this.totalEach = totalEach;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQtyEntered() {
        return qtyEntered;
    }

    public void setQtyEntered(int qtyEntered) {
        this.qtyEntered = qtyEntered;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
