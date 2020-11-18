package com.example.androideatit.Model;

import java.util.List;

public class Request {
    private String phone;
    private String adress;
    private String total;
    private String status;
    private String name;
    private List<Order> foods;

    public Request() {
    }

    public Request(String phone, String adress, String total, String name, List<Order> foods) {
        this.phone = phone;
        this.adress = adress;
        this.total = total;
        this.name = name;
        this.foods = foods;
        this.status="0";//default is 0,0 is placed,1:shipping, 2: shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
