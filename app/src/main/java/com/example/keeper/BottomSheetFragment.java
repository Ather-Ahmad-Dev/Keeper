package com.example.keeper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import com.example.keeper.databinding.TaskPriorityDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private BottomSheetFragmentBinding binding;
    private TaskPriorityDialogBinding taskPriorityDialogBinding;
    private OnTaskAddedListener onTaskAddedListener;
    private Dialog taskPriorityDialog;

    public interface OnTaskAddedListener{
        void onTaskAdded(String title, String description);
    }

    public void setOnTaskAddedListener(OnTaskAddedListener onTaskAddedListener) {
        this.onTaskAddedListener = onTaskAddedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = BottomSheetFragmentBinding.inflate(inflater, container,false);

        binding.addArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = binding.edittextTaskTitle.getText().toString();
                String description = binding.edittextTaskDescription.getText().toString();

                if (onTaskAddedListener != null && !title.isEmpty() && !description.isEmpty()){
                    onTaskAddedListener.onTaskAdded(title, description);
                    dismiss();
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
                if (taskPriorityDialog == null){
                    setTaskPriorityDialog();
                }
                taskPriorityDialog.show();
                getSelectedChip();
            }
        });

        return binding.getRoot();
    }

    private void setTaskPriorityDialog(){
        taskPriorityDialogBinding = TaskPriorityDialogBinding.inflate(getLayoutInflater());
        taskPriorityDialog = new Dialog(requireContext());
        taskPriorityDialog.setContentView(taskPriorityDialogBinding.getRoot());
        taskPriorityDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void getSelectedChip(){
        ChipGroup chipGroup = taskPriorityDialogBinding.chipGroup;
        taskPriorityDialogBinding.setPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedChipID = chipGroup.getCheckedChipId();
                if (selectedChipID != View.NO_ID){
                    Chip selectedChip = taskPriorityDialogBinding.getRoot().findViewById(selectedChipID);
                    String chipText =   selectedChip.getText().toString();
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    taskPriorityDialog.dismiss();
                } else {
                    Toast.makeText(requireContext(), "Select Priority", Toast.LENGTH_SHORT).show();
                }
            }
        });
        taskPriorityDialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskPriorityDialog.dismiss();
            }
        });
    }

    private void selectDateAndTime(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Toast.makeText(requireContext(), year + "/" + month + "/" + day, Toast.LENGTH_SHORT).show();
                selectTime();

            }
        }, 2024, 10, 13);

        datePickerDialog.show();
    }

    private void selectTime(){
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
        if (taskPriorityDialog != null && taskPriorityDialog.isShowing()){
            taskPriorityDialog.dismiss();
        }
        binding = null;
        taskPriorityDialogBinding = null;
    }
}
