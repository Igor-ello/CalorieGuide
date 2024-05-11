package com.obsessed.calorieguide.adapters.food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.food.listeners.OnAddFoodClickListener;
import com.obsessed.calorieguide.adapters.food.listeners.OnFoodClickListener;
import com.obsessed.calorieguide.adapters.food.listeners.OnLikeFoodClickListener;
import com.obsessed.calorieguide.databinding.FoodItemLikesBinding;
import com.obsessed.calorieguide.retrofit.food.Food;

import java.util.ArrayList;

public class FoodIntakeAdapter extends RecyclerView.Adapter<FoodIntakeAdapter.FoodHolder> {
    public ArrayList<Food> foodArrayList;
    private OnFoodClickListener onFoodClickListener;
    private OnLikeFoodClickListener onLikeFoodClickListener;
    private OnAddFoodClickListener onAddFoodClickListener;

    public FoodIntakeAdapter(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    public class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FoodItemLikesBinding binding;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            binding = FoodItemLikesBinding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
            binding.btLike.setOnClickListener(this);
            binding.btAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onFoodClickListener != null) {
                onFoodClickListener.onFoodClick(foodArrayList.get(position));
            }
            if (position!= RecyclerView.NO_POSITION && onAddFoodClickListener!= null) {
                onAddFoodClickListener.onAddFoodClick(foodArrayList.get(position));
            }
        }

        public void bind(Food food) {
            binding.tvName.setText(food.getFoodName());
            binding.tvCalories.setText(String.valueOf(food.getCalories()));
            binding.btLike.setImageResource(food.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active);
            binding.btAdd.setImageResource(R.drawable.add);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_likes, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.bind(foodArrayList.get(position));
        holder.binding.btLike.setOnClickListener(v -> {
            if (onLikeFoodClickListener != null)
                onLikeFoodClickListener.onLikeFoodClick(foodArrayList.get(position), holder.binding.btLike);
        });
        holder.binding.btAdd.setOnClickListener(v -> {
            if (onAddFoodClickListener != null)
                onAddFoodClickListener.onAddFoodClick(foodArrayList.get(position));
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

    public void setOnAddFoodClickListener(OnAddFoodClickListener listener) {
        this.onAddFoodClickListener = listener;
    }
}

