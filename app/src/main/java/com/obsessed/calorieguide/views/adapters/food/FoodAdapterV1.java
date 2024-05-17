package com.obsessed.calorieguide.views.adapters.food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FoodItemV1Binding;
import com.obsessed.calorieguide.views.adapters.food.listeners.OnFoodClickListener;
import com.obsessed.calorieguide.views.adapters.food.listeners.OnLikeFoodClickListener;
import com.obsessed.calorieguide.network.food.Food;

import java.util.ArrayList;

public class FoodAdapterV1 extends RecyclerView.Adapter<FoodAdapterV1.FoodHolder> {
    public ArrayList<Food> foodArrayList;
    private OnFoodClickListener onFoodClickListener;
    private OnLikeFoodClickListener onLikeFoodClickListener;

    public FoodAdapterV1(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    public class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FoodItemV1Binding binding;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            binding = FoodItemV1Binding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
            binding.btLikeV1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onFoodClickListener != null) {
                onFoodClickListener.onFoodClick(foodArrayList.get(position));
            }
        }

        public void bind(Food food) {
            binding.tvName.setText(food.getFoodName());
            binding.btLikeV1.setImageResource(food.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active);
            Log.d("FoodAdapter", "Name: " + food.getFoodName() + " like: " + food.getIsLiked());

            if (food.getPicture() != null) {
                byte[] imageData = food.getPicture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                binding.imageView.setImageBitmap(bitmap);
            } else {
                binding.imageView.setImageResource(R.drawable.food_default);
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
        holder.binding.btLikeV1.setOnClickListener(v -> {
            if (onLikeFoodClickListener != null) {
                onLikeFoodClickListener.onLikeFoodClick(foodArrayList.get(position), holder.binding.btLikeV1);
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
