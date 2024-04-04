package com.obsessed.calorieguide.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FoodItemV2Binding;
import com.obsessed.calorieguide.retrofit.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapterV2 extends RecyclerView.Adapter<FoodAdapterV2.FoodHolder> {
    public ArrayList<Food> foodArrayList;

    public class FoodHolder extends RecyclerView.ViewHolder{
        FoodItemV2Binding binding = FoodItemV2Binding.bind(itemView);
        public FoodHolder(@NonNull View item){
            super(item);
        }
        public void bind(Food food) {
            binding.tvName.setText(food.getFood_name());
            binding.tvCalories.setText("Calories: " + food.getCalories());
            binding.tvProteins.setText("p: " + food.getProteins());
            binding.tvCarbohydrates.setText("c: " + food.getCarbohydrates());
            binding.tvFats.setText("f: " + food.getFats());

            if(food.getPicture() != null) {
                byte[] imageData = food.getPicture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                binding.imageView.setImageBitmap(bitmap);
            } else {
                binding.imageView.setImageResource(R.drawable.block_flipped);
            }
        }
    }

    @NonNull //Создание
    @Override //Надувка food_item через LayoutInflater.inflate()
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_v2, parent, false);
        return new FoodHolder(view);
    }

    @Override //Заполнение
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.bind(foodArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public void addFood(Food food){
        foodArrayList.add(food);
        //Перерисовка адаптера
        notifyDataSetChanged();
    }
}
