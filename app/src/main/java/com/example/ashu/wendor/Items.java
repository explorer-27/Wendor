package com.example.ashu.wendor;

/**
 * Created by ashu on 21/3/18.
 */

public class Items {
    private int itemId, totUnits, leftUnit, price;
    private String name, imgPath, imgUrl;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getTotUnits() {
        return totUnits;
    }

    public void setTotUnits(int totUnits) {
        this.totUnits = totUnits;
    }

    public int getLeftUnit() {
        return leftUnit;
    }

    public void setLeftUnit(int leftUnit) {
        this.leftUnit = leftUnit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
