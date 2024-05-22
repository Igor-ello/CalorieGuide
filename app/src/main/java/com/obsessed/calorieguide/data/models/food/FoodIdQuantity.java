package com.obsessed.calorieguide.data.models.food;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_id_quantity")
public class FoodIdQuantity {
    @PrimaryKey(autoGenerate = true)
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
