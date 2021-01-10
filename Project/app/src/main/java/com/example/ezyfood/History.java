package com.example.ezyfood;

import java.util.Date;

public class History {
    public History(String address, String date, Integer total) {
        this.address = address;
        this.date = date;
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    private String address;
    private String date;
    private Integer total;
}
