package com.obsessed.calorieguide.adapters.food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.food.listeners.OnFoodClickListener;
import com.obsessed.calorieguide.adapters.food.listeners.OnLikeFoodClickListener;
import com.obsessed.calorieguide.databinding.FoodItemV2Binding;
import com.obsessed.calorieguide.retrofit.food.Food;

import java.util.ArrayList;

public class FoodAdapterV2 extends RecyclerView.Adapter<FoodAdapterV2.FoodHolder> {
    public ArrayList<Food> foodArrayList;
    private OnFoodClickListener onFoodClickListener;
    private OnLikeFoodClickListener onLikeFoodClickListener;

    public class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FoodItemV2Binding binding;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            binding = FoodItemV2Binding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
            binding.btLikeV2.setOnClickListener(this);
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
            binding.tvCalories.setText(String.valueOf(food.getCalories()));
            binding.tvProteins.setText("p: " + food.getProteins());
            binding.tvCarbohydrates.setText("c: " + food.getCarbohydrates());
            binding.tvFats.setText("f: " + food.getFats());
            binding.btLikeV2.setImageResource(food.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_v2, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.bind(foodArrayList.get(position));
        holder.binding.btLikeV2.setOnClickListener(v -> {
            if (onLikeFoodClickListener != null) {
                onLikeFoodClickListener.onLikeFoodClick(foodArrayList.get(position), holder.binding.btLikeV2);
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
