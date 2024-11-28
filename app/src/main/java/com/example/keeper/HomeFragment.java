package com.example.keeper;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.keeper.databinding.FragmentHomeBinding;
import com.example.keeper.databinding.TaskBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TaskBinding taskBinding;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<RecyclerViewModelClass> itemList = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        taskBinding = TaskBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.allTasks.setLayoutManager(new LinearLayoutManager(requireContext()));

        itemList.add(new RecyclerViewModelClass("Title 1", "Timing 1", "University", "1", true));
        itemList.add(new RecyclerViewModelClass("Title 2", "Timing 2", "University", "2", false));

        recyclerViewAdapter = new RecyclerViewAdapter(requireContext(),itemList);
        binding.allTasks.setAdapter(recyclerViewAdapter);

        if(recyclerViewAdapter.getItemCount() == 0){
            binding.allTasks.setVisibility(View.GONE);
        }else{
            binding.checklistImage.setVisibility(View.GONE);
            binding.today.setVisibility(View.GONE);
            binding.addTask.setVisibility(View.GONE);
        }
    }

    public void addTask(String title, String description, String tag, String priority){

            RecyclerViewModelClass newItem = new RecyclerViewModelClass(title, description, tag, priority, false);
            itemList.add(newItem);
            if (recyclerViewAdapter != null){
                recyclerViewAdapter.notifyItemInserted(itemList.size() - 1);
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}