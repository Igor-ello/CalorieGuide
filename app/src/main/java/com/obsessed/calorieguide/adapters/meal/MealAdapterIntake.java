package com.obsessed.calorieguide.adapters.meal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.meal.listeners.OnAddMealClickListener;
import com.obsessed.calorieguide.adapters.meal.listeners.OnLikeMealClickListener;
import com.obsessed.calorieguide.adapters.meal.listeners.OnMealClickListener;
import com.obsessed.calorieguide.databinding.MealItemLikesBinding;
import com.obsessed.calorieguide.retrofit.meal.Meal;

import java.util.ArrayList;

public class MealAdapterIntake extends RecyclerView.Adapter<MealAdapterIntake.MealHolder> {
    private ArrayList<Meal> mealArrayList;
    private OnMealClickListener onMealClickListener;
    private OnLikeMealClickListener onLikeMealClickListener;
    private OnAddMealClickListener onAddMealClickListener;

    public MealAdapterIntake(ArrayList<Meal> mealArrayList) {
        this.mealArrayList = mealArrayList;
    }

    public class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MealItemLikesBinding binding;

        public MealHolder(@NonNull View itemView) {
            super(itemView);
            binding = MealItemLikesBinding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
            binding.btLike.setOnClickListener(this);
            binding.btAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onMealClickListener != null) {
                onMealClickListener.onMealClick(mealArrayList.get(position));
            }
            if (position!= RecyclerView.NO_POSITION && onLikeMealClickListener!= null) {
                onAddMealClickListener.onAddMealClick(mealArrayList.get(position));
            }
        }

        public void bind(Meal meal) {
            binding.tvName.setText(meal.getMealName());
            binding.tvCalories.setText(String.valueOf(meal.getTotalCalories()));
            binding.btLike.setImageResource(meal.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active);
            binding.btAdd.setImageResource(R.drawable.add);

            if (meal.getPicture() != null) {
                byte[] imageData = meal.getPicture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                binding.imageView.setImageBitmap(bitmap);
            } else {
                binding.imageView.setImageResource(R.drawable.meal_default);
            }
        }
    }

    @NonNull
    @Override
    public MealAdapterIntake.MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_likes, parent, false);
        return new MealAdapterIntake.MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        holder.bind(mealArrayList.get(position));
        holder.binding.btLike.setOnClickListener(v -> {
            if (onLikeMealClickListener != null)
                onLikeMealClickListener.onLikeMealClick(mealArrayList.get(position), holder.binding.btLike);
        });
        holder.binding.btAdd.setOnClickListener(v -> {
            if (onAddMealClickListener != null)
                onAddMealClickListener.onAddMealClick(mealArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }

    public void addMeal(Meal meal) {
        mealArrayList.add(meal);
        notifyDataSetChanged();
    }

    public void setOnMealClickListener(OnMealClickListener listener) {
        this.onMealClickListener = listener;
    }

    public void setOnLikeMealClickListener(OnLikeMealClickListener listener) {
        this.onLikeMealClickListener = listener;
    }

    public void setOnAddMealClickListener(OnAddMealClickListener listener) {
        this.onAddMealClickListener = listener;
    }
}

