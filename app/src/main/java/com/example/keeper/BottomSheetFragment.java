package com.example.keeper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.keeper.databinding.BottomSheetFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private BottomSheetFragmentBinding binding;
    private OnTaskAddedListener onTaskAddedListener;

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

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;
    }
}
