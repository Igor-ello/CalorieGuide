package com.obsessed.calorieguide.views.adapters.intake;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.ObjectItemBinding;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.models.Meal;

import java.util.ArrayList;

public class IntakeAdapter extends RecyclerView.Adapter<IntakeAdapter.IntakeHolder> {
    public ArrayList<Object> objArrayList;
    private OnObjClickListener onObjClickListener;

    public class IntakeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ObjectItemBinding binding;

        public IntakeHolder(@NonNull View itemView) {
            super(itemView);
            binding = ObjectItemBinding.bind(itemView);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onObjClickListener != null) {
                onObjClickListener.onIntakeClick(objArrayList.get(position));
            }
        }

        public void bind(Object object) {
            byte[] imageData = null;

            if(object instanceof Food) {
                Food food = (Food) object;
                binding.tvName.setText(food.getFood_name());
                imageData = food.getPicture();
                Log.d("ObjectAdapter", food.getFood_name());
            } else if(object instanceof Meal) {
                Meal meal = (Meal) object;
                binding.tvName.setText(meal.getMeal_name());
                imageData = meal.getPicture();
                Log.d("ObjectAdapter", meal.getMeal_name());
            } else
                binding.tvName.setText("NULL!!!");

            if(imageData != null) {
                binding.imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
            } else {
                if (object instanceof Food)
                    binding.imageView.setImageResource(R.drawable.food_default);
                if (object instanceof Meal)
                    binding.imageView.setImageResource(R.drawable.meal_default);
            }
        }
    }

    @NonNull
    @Override
    public IntakeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.object_item, parent, false);
        return new IntakeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntakeHolder holder, int position) {
        holder.bind(objArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return objArrayList.size();
    }

    public void addIntake(Object obj) {
        objArrayList.add(obj);
        notifyDataSetChanged();
    }

    public void setOnObjClickListener(OnObjClickListener listener) {
        this.onObjClickListener = listener;
    }
}
