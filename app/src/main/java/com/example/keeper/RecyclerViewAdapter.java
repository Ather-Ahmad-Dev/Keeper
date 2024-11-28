package com.example.keeper;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.keeper.databinding.TaskBinding;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TasksViewHolder> {

    private final Context context;
    private final List<RecyclerViewModelClass> itemList;
    private final ActivityResultLauncher<Intent> editTaskLauncher;

    public RecyclerViewAdapter(Context context,List<RecyclerViewModelClass> itemList, ActivityResultLauncher<Intent> editTaskLauncher){
        this.context = context;
        this.itemList = itemList;
        this.editTaskLauncher = editTaskLauncher;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskBinding binding = TaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return  new TasksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
            RecyclerViewModelClass item = itemList.get(position);
            holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder{

        private final TaskBinding binding;

        public TasksViewHolder(TaskBinding binding){
                super(binding.getRoot());
                this.binding = binding;
        }

        public void bind(RecyclerViewModelClass item){
                binding.taskTitle.setText(item.getTaskTitle());
                binding.taskTime.setText(item.getTaskTime());
                binding.checkbox.setChecked(item.isChecked());
                binding.tag.setText(item.getTag());
                binding.priority.setText(item.getPriority());

                String tagText = item.getTag();
                if(Objects.equals(tagText, "Grocery")){
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.grocery_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.bread));

                } else if (Objects.equals(tagText, "Work")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.work_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.briefcase));

                } else if (Objects.equals(tagText, "Sport")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.sport_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.sportmini));

                } else if (Objects.equals(tagText, "Design")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.design_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.designmini));

                } else if (Objects.equals(tagText, "University")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.university_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.hat));

                } else if (Objects.equals(tagText, "Social")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.social_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.megaphone));

                } else if (Objects.equals(tagText, "Music")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.music_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.musicmini));

                } else if (Objects.equals(tagText, "Health")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.health_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.heartbeat));

                } else if (Objects.equals(tagText, "Movie")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.movie_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.videocamera));

                } else if (Objects.equals(tagText, "home")) {
                    binding.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.home_background)));
                    binding.tag.setChipIcon(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.homemini));
                }

                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TaskDetailActivity.class);

                        String taskTitle = binding.taskTitle.getText().toString();
                        String taskDescription = binding.taskTime.getText().toString();
                        String taskCategory = binding.tag.getText().toString();
                        String taskPriority = binding.priority.getText().toString();
                        boolean isChecked = binding.checkbox.isChecked();

                        intent.putExtra("TASK_TITLE", taskTitle);
                        intent.putExtra("TASK_DESCRIPTION", taskDescription);
                        intent.putExtra("TASK_CATEGORY", taskCategory);
                        intent.putExtra("TASK_PRIORITY", taskPriority);
                        intent.putExtra("TASK_CHECK", isChecked);
                        intent.putExtra("TASK_POSITION", getAdapterPosition());

                        editTaskLauncher.launch(intent);
//                        context.startActivity(intent);

                    }
                });
        }
    }
}
