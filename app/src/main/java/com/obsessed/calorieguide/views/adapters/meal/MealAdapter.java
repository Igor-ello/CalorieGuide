package com.obsessed.calorieguide.views.adapters.meal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.MealItemBinding;
import com.obsessed.calorieguide.views.adapters.meal.listeners.OnLikeMealClickListener;
import com.obsessed.calorieguide.views.adapters.meal.listeners.OnMealClickListener;
import com.obsessed.calorieguide.data.models.Meal;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealHolder> {
    public ArrayList<Meal> mealArrayList;
    private OnMealClickListener onMealClickListener;
    private OnLikeMealClickListener onLikeMealClickListener;

    public MealAdapter(ArrayList<Meal> mealArrayList) {
        this.mealArrayList = mealArrayList;
    }

    public class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MealItemBinding binding;

        public MealHolder(@NonNull View itemView) {
            super(itemView);
            binding = MealItemBinding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
            binding.btLike.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onMealClickListener != null) {
                onMealClickListener.onMealClick(mealArrayList.get(position));
            }
        }

        public void bind(Meal meal) {
            binding.tvName.setText(meal.getMeal_name());
            binding.tvCalories.setText(String.valueOf(meal.getTotal_calories()));
            binding.tvDescription.setText(meal.getDescription());
            binding.btLike.setImageResource(meal.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active);

            Log.d("MealAdapter", meal.getMeal_name() + " " + meal.getIsLiked());

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
    public MealAdapter.MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealAdapter.MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        holder.bind(mealArrayList.get(position));
        holder.binding.btLike.setOnClickListener(v -> {
            if (onLikeMealClickListener != null) {
                onLikeMealClickListener.onLikeMealClick(mealArrayList.get(position), holder.binding.btLike);
            }
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
}
