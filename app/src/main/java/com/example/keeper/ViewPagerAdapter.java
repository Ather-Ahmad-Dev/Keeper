package com.example.keeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.keeper.databinding.IntroductorySliderLayoutBinding;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private final Context context;

    int[] images = {
            R.drawable.manage_your_tasks,
            R.drawable.create_daily_routine,
            R.drawable.organize_your_task
    };

    int[] titles = {
            R.string.manage_your_tasks_title,
            R.string.create_daily_routine_title,
            R.string.organize_your_tasks_title
    };

    int[] descriptions = {
            R.string.manage_your_tasks_description,
            R.string.create_daily_routine_description,
            R.string.organize_your_tasks_description
    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate using View Binding
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IntroductorySliderLayoutBinding binding = IntroductorySliderLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolder holder, int position) {
        // Bind data to the views
        holder.binding.imageView.setImageResource(images[position]);
        holder.binding.title.setText(titles[position]);
        holder.binding.description.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

   public static class ViewHolder extends RecyclerView.ViewHolder {
        IntroductorySliderLayoutBinding binding;
        public ViewHolder(IntroductorySliderLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
   }

}
