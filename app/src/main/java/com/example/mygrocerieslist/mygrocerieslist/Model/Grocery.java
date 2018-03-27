package com.example.mygrocerieslist.mygrocerieslist.Model;

/**
 * Created by RaymondTsang on 12/26/17.
 */

public class Grocery {

    private int id;
    private String item;
    private String quantity;

    public Grocery() {
    }

    public Grocery(int id, String item, String quantity) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
