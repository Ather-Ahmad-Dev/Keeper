package com.example.keeper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.keeper.databinding.ActivityTaskDetailBinding;
import com.example.keeper.databinding.ConfirmationDialogBinding;
import com.example.keeper.databinding.TaskAndDescripitionEditDialogBinding;
import com.example.keeper.databinding.TaskCategoryDialogBinding;
import com.example.keeper.databinding.TaskPriorityDialogBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class TaskDetailActivity extends AppCompatActivity {

    private ActivityTaskDetailBinding binding;
    private TaskAndDescripitionEditDialogBinding taskAndDescripitionEditDialogBinding;
    private TaskCategoryDialogBinding taskCategoryDialogBinding;
    private TaskPriorityDialogBinding taskPriorityDialogBinding;
    private ConfirmationDialogBinding confirmationDialogBinding;
    private Dialog taskCategoryDialog, taskPriorityDialog;
    private AlertDialog taskAndTitleDialog, deleteConfirmationDialog;
    private String tag = "";
    private String priority = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTaskDetails();
        initiateDialog();

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskAndTitleDialog.show();
            }
        });

        taskAndDescripitionEditDialogBinding.editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = taskAndDescripitionEditDialogBinding.taskTitleEditDialog.getText().toString();
                String description = taskAndDescripitionEditDialogBinding.taskDescriptionEditDialog.getText().toString();
                binding.taskTitle.setText(title);
                binding.taskDescription.setText(description);
                taskAndTitleDialog.dismiss();
            }
        });

        confirmationDialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteConfirmationDialog.dismiss();
            }
        });

        confirmationDialogBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("DELETE_TASK", true);
                resultIntent.putExtra("TASK_POSITION", getIntent().getIntExtra("TASK_POSITION",-1));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        binding.taskTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetailActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Toast.makeText(TaskDetailActivity.this, year + "/" + month + "/" + day, Toast.LENGTH_SHORT).show();
                        selectTime();
                    }
                }, 2024, 10, 13);

                datePickerDialog.show();
            }
        });

        binding.taskCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskCategoryDialog.show();
                getSelectedCategoryChip();
            }
        });

        binding.taskPriorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskPriorityDialog.show();
                getSelectedChip();
            }
        });

        binding.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteConfirmationDialog.show();
            }
        });

        binding.editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("TASK_TITLE", binding.taskTitle.getText().toString());
                resultIntent.putExtra("TASK_DESCRIPTION", binding.taskDescription.getText().toString());
                resultIntent.putExtra("TASK_CATEGORY", binding.taskCategoryButton.getText().toString());
                resultIntent.putExtra("TASK_PRIORITY", binding.taskPriorityButton.getText().toString());
                resultIntent.putExtra("TASK_CHECK", binding.state.isChecked());
                resultIntent.putExtra("TASK_POSITION", getIntent().getIntExtra("TASK_POSITION", -1));

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }


    private void initiateDialog(){
        taskAndDescripitionEditDialogBinding = TaskAndDescripitionEditDialogBinding.inflate(LayoutInflater.from(this));
        taskCategoryDialogBinding = TaskCategoryDialogBinding.inflate(getLayoutInflater());
        taskPriorityDialogBinding = TaskPriorityDialogBinding.inflate(getLayoutInflater());
        confirmationDialogBinding = ConfirmationDialogBinding.inflate(getLayoutInflater());
        taskCategoryDialog = new Dialog(TaskDetailActivity.this);
        taskPriorityDialog = new Dialog(TaskDetailActivity.this);
        taskCategoryDialog.setContentView(taskCategoryDialogBinding.getRoot());
        taskPriorityDialog.setContentView(taskPriorityDialogBinding.getRoot());
        taskAndTitleDialog = new AlertDialog.Builder(this).setView(taskAndDescripitionEditDialogBinding.getRoot()).create();
        deleteConfirmationDialog = new AlertDialog.Builder(this).setView(confirmationDialogBinding.getRoot()).create();

    }

    private void selectTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(TaskDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int mints) {
                Toast.makeText(TaskDetailActivity.this, hour + "/" + mints, Toast.LENGTH_SHORT).show();
            }
        }, 12, 00, true);

        timePickerDialog.show();
    }

    private void getSelectedCategoryChip() {
        ChipGroup categoryChipGroup = taskCategoryDialogBinding.chipGroupCategory;

        categoryChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != View.NO_ID) {
                    Chip selectedChip = taskCategoryDialogBinding.getRoot().findViewById(checkedId);
                    tag = selectedChip.getText().toString();
                    Toast.makeText(TaskDetailActivity.this, tag, Toast.LENGTH_SHORT).show();
                    binding.taskCategoryButton.setText(tag);
                    taskCategoryDialog.dismiss();
                }
            }
        });
    }

    private void getSelectedChip() {
        ChipGroup chipGroup = taskPriorityDialogBinding.chipGroup;

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != View.NO_ID) {
                    Chip selectedChip = taskPriorityDialogBinding.getRoot().findViewById(checkedId);
                    priority = selectedChip.getText().toString();
                    Toast.makeText(TaskDetailActivity.this, priority, Toast.LENGTH_SHORT).show();
                    binding.taskPriorityButton.setText(priority);
                    taskPriorityDialog.dismiss();
                }
            }
        });
    }

    private void setTaskDetails(){

        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra("TASK_TITLE");
        String taskDescription = intent.getStringExtra("TASK_DESCRIPTION");
        String taskCategory = intent.getStringExtra("TASK_CATEGORY");
        String taskPriority = intent.getStringExtra("TASK_PRIORITY");
        boolean isChecked = intent.getBooleanExtra("TASK_CHECK", false);

        binding.taskTitle.setText(taskTitle);
        binding.taskDescription.setText(taskDescription);
        binding.taskCategoryButton.setText(taskCategory);
        binding.taskPriorityButton.setText(taskPriority);
        binding.state.setChecked(isChecked);

    }

}