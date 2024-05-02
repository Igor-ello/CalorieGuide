package com.obsessed.calorieguide.retrofit.meal;

public class FoodIdQuantity {
    int product_id;
    int quantity;

    public FoodIdQuantity(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getProductId() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
