package com.obsessed.calorieguide.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FoodItemV1Binding;
import com.obsessed.calorieguide.retrofit.food.Food;

import java.util.ArrayList;

public class FoodAdapterV1 extends RecyclerView.Adapter<FoodAdapterV1.FoodHolder> {
    public ArrayList<Food> foodArrayList;
    private OnFoodClickListener onFoodClickListener;
    private OnLikeFoodClickListener onLikeFoodClickListener;

    public class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FoodItemV1Binding binding;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            binding = FoodItemV1Binding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
            binding.btLike.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onFoodClickListener != null) {
                onFoodClickListener.onFoodClick(foodArrayList.get(position));
            }
            if (position != RecyclerView.NO_POSITION && onLikeFoodClickListener != null) {
                onLikeFoodClickListener.onLikeFoodClick(foodArrayList.get(position));
            }
        }

        public void bind(Food food) {
            binding.tvName.setText(food.getFoodName());

            if (food.getPicture() != null) {
                byte[] imageData = food.getPicture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                binding.imageView.setImageBitmap(bitmap);
            } else {
                binding.imageView.setImageResource(R.drawable.grass_icon);
            }
        }
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_v1, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.bind(foodArrayList.get(position));
        holder.binding.btLike.setOnClickListener(v -> {
            if (onLikeFoodClickListener != null) {
                onLikeFoodClickListener.onLikeFoodClick(foodArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public void addFood(Food food) {
        foodArrayList.add(food);
        notifyDataSetChanged();
    }

    public void setOnFoodClickListener(OnFoodClickListener listener) {
        this.onFoodClickListener = listener;
    }

    public void setOnLikeFoodClickListener(OnLikeFoodClickListener listener) {
        this.onLikeFoodClickListener = listener;
    }
}
