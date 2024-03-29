package com.obsessed.calorieguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FoodItemBinding;
import com.obsessed.calorieguide.retrofit.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    public ArrayList<Food> foodArrayList;

    public class FoodHolder extends RecyclerView.ViewHolder{
        FoodItemBinding binding = FoodItemBinding.bind(itemView);
        public FoodHolder(@NonNull View item){
            super(item);
        }
        public void bind(Food food) {
            // Загрузка изображения с помощью Picasso
            Picasso.get().load(food.getImage()).into(binding.imageView);
            binding.tvName.setText(food.getTitle());
        }
    }

    @NonNull //Создание
    @Override //Надувка food_item через LayoutInflater.inflate()
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
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
