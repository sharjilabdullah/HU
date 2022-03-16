package com.example.visudhaajivamapp.models;

public class CartItemList {

    private CartItems product;
    private int quantity;
    private boolean selected;

    public CartItemList(CartItems product, int quantity, boolean selected) {
        this.product = product;
        this.quantity = quantity;
        this.selected=selected;
    }

    public CartItems getProduct() {
        return product;
    }

    public void setProduct(CartItems product) {
        this.product = product;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemList{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }



}
