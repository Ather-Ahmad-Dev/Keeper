package com.example.keeper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.keeper.databinding.BottomSheetFragmentBinding;
import com.example.keeper.databinding.TaskCategoryDialogBinding;
import com.example.keeper.databinding.TaskPriorityDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private BottomSheetFragmentBinding binding;
    private TaskPriorityDialogBinding taskPriorityDialogBinding;
    private TaskCategoryDialogBinding taskCategoryDialogBinding;
    private OnTaskAddedListener onTaskAddedListener;
    private Dialog taskPriorityDialog, taskCategoryDialog;
    private String tag = "";
    private String priority = "";

    public interface OnTaskAddedListener {
        void onTaskAdded(String title, String description, String tag, String priority);
    }

    public void setOnTaskAddedListener(OnTaskAddedListener onTaskAddedListener) {
        this.onTaskAddedListener = onTaskAddedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetFragmentBinding.inflate(inflater, container, false);

        binding.addArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.edittextTaskTitle.getText().toString();
                String description = binding.edittextTaskDescription.getText().toString();

                if (onTaskAddedListener != null && !title.isEmpty() && !description.isEmpty()) {
                    onTaskAddedListener.onTaskAdded(title, description, tag, priority);
                    dismiss();
                } else {
                    Toast.makeText(requireContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDateAndTime();
            }
        });

        binding.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskPriorityDialog == null) {
                    setTaskPriorityDialog();
                }
                taskPriorityDialog.show();
                getSelectedChip();
            }
        });

        binding.tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskCategoryDialog == null) {
                    setTaskCategoryDialog();
                }
                taskCategoryDialog.show();
                getSelectedCategoryChip();
            }
        });

        return binding.getRoot();
    }

    private void getSelectedCategoryChip() {
        ChipGroup categoryChipGroup = taskCategoryDialogBinding.chipGroupCategory;

        categoryChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != View.NO_ID) {
                    Chip selectedChip = taskCategoryDialogBinding.getRoot().findViewById(checkedId);
                    tag = selectedChip.getText().toString();
                    Toast.makeText(requireContext(), tag, Toast.LENGTH_SHORT).show();
                    taskCategoryDialog.dismiss();
                }
            }
        });

    }

    private void setTaskCategoryDialog() {
        taskCategoryDialogBinding = TaskCategoryDialogBinding.inflate(getLayoutInflater());
        taskCategoryDialog = new Dialog(requireContext());
        taskCategoryDialog.setContentView(taskCategoryDialogBinding.getRoot());
        taskCategoryDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void getSelectedChip() {
        ChipGroup chipGroup = taskPriorityDialogBinding.chipGroup;

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != View.NO_ID) {
                    Chip selectedChip = taskPriorityDialogBinding.getRoot().findViewById(checkedId);
                    priority = selectedChip.getText().toString();
                    Toast.makeText(requireContext(), priority, Toast.LENGTH_SHORT).show();
                    taskPriorityDialog.dismiss();
                }
            }
        });

    }

    private void setTaskPriorityDialog() {
        taskPriorityDialogBinding = TaskPriorityDialogBinding.inflate(getLayoutInflater());
        taskPriorityDialog = new Dialog(requireContext());
        taskPriorityDialog.setContentView(taskPriorityDialogBinding.getRoot());
        taskPriorityDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void selectDateAndTime() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Toast.makeText(requireContext(), year + "/" + month + "/" + day, Toast.LENGTH_SHORT).show();
                selectTime();
            }
        }, 2024, 10, 13);

        datePickerDialog.show();
    }

    private void selectTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int mints) {
                Toast.makeText(requireContext(), hour + "/" + mints, Toast.LENGTH_SHORT).show();
            }
        }, 12, 00, true);

        timePickerDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (taskPriorityDialog != null && taskPriorityDialog.isShowing()) {
            taskPriorityDialog.dismiss();
        }
        binding = null;
        taskPriorityDialogBinding = null;
        taskCategoryDialogBinding = null;
    }
}
