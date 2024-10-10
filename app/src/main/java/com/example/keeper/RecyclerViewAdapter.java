package com.example.keeper;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.keeper.databinding.TaskBinding;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TasksViewHolder> {

    private final List<RecyclerViewModelClass> itemList;

    public RecyclerViewAdapter(List<RecyclerViewModelClass> itemList){
        this.itemList = itemList;
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
        }
    }
}
