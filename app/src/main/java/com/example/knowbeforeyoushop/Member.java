package com.example.knowbeforeyoushop;

import java.io.Serializable;

public class Member implements Serializable {
    public String title;
    public String category,quant;
    public String price;
    public String number;

    Member(){

    }

    public Member(String category, String no, String price, String quant, String title) {

        this.category = category;
        this.number = no;
        this.price = price;
        this.quant = quant;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
