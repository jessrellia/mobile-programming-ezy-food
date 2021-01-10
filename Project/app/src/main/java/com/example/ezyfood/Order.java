package com.example.ezyfood;

public class Order {
    private String name;
    private int qty;
    private int price;
    private  int img;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }


    public Order(String name, int qty, int price, int img, String type) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.img = img;
        this.type = type;
    }
}
