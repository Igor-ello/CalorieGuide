package com.obsessed.calorieguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FoodItemV1Binding;
import com.obsessed.calorieguide.retrofit.Food;

import java.util.ArrayList;

public class FoodAdapterV1 extends RecyclerView.Adapter<FoodAdapterV1.FoodHolder> {
    public ArrayList<Food> foodArrayList;

    public class FoodHolder extends RecyclerView.ViewHolder{
        FoodItemV1Binding binding = FoodItemV1Binding.bind(itemView);
        public FoodHolder(@NonNull View item){
            super(item);
        }
        public void bind(Food food) {
            binding.tvName.setText(food.getFood_name());

            if(food.getPicture() != null)
                binding.imageView.setImageBitmap(food.getPicture());
            else binding.imageView.setImageResource(R.drawable.block_flipped);
        }
    }

    @NonNull //Создание
    @Override //Надувка food_item через LayoutInflater.inflate()
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_v1, parent, false);
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
