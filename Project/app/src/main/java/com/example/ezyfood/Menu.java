package com.example.ezyfood;

public class Menu {
    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    private String location_name;
    private String menu_name;
    private int stock;
    private int price;
    private String menu_type;
    private int picture;


    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }



    public Menu(String location_name, String menu_name, int stock, int price, String menu_type, int picture) {
        this.location_name = location_name;
        this.menu_name = menu_name;
        this.stock = stock;
        this.price = price;
        this.menu_type = menu_type;
        this.picture = picture;
    }


}
