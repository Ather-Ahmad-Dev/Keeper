package com.example.keeper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.keeper.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.allTasks.setLayoutManager(new LinearLayoutManager(requireContext()));

        itemList.add(new RecyclerViewModelClass("Title 1", "Timing 1", "University", "1", true));
        itemList.add(new RecyclerViewModelClass("Title 2", "Timing 2", "University", "2", false));
        itemList.add(new RecyclerViewModelClass("Title 3", "Timing 3", "University", "3", true));
        itemList.add(new RecyclerViewModelClass("Title 4", "Timing 4", "University", "4", false));
        itemList.add(new RecyclerViewModelClass("Title 5", "Timing 5", "University", "5", true));
        itemList.add(new RecyclerViewModelClass("Title 6", "Timing 6", "University", "6", false));
        itemList.add(new RecyclerViewModelClass("Title 7", "Timing 7", "University", "7", true));
        itemList.add(new RecyclerViewModelClass("Title 8", "Timing 8", "University", "8", false));
        itemList.add(new RecyclerViewModelClass("Title 9", "Timing 9", "University", "9", true));
        itemList.add(new RecyclerViewModelClass("Title 10", "Timing 10", "University", "10", false));
        itemList.add(new RecyclerViewModelClass("Title 11", "Timing 11", "University", "11", true));
        itemList.add(new RecyclerViewModelClass("Title 12", "Timing 12", "University", "12", false));
        itemList.add(new RecyclerViewModelClass("Title 13", "Timing 13", "University", "13", true));
        itemList.add(new RecyclerViewModelClass("Title 14", "Timing 14", "University", "14", false));
        itemList.add(new RecyclerViewModelClass("Title 15", "Timing 15", "University", "15", true));

        recyclerViewAdapter = new RecyclerViewAdapter(itemList);
        binding.allTasks.setAdapter(recyclerViewAdapter);

        if(recyclerViewAdapter.getItemCount() == 0){
            binding.allTasks.setVisibility(View.GONE);
        }else{
            binding.checklistImage.setVisibility(View.GONE);
            binding.today.setVisibility(View.GONE);
            binding.addTask.setVisibility(View.GONE);
        }
    }

    public void addTask(String title, String description){

            RecyclerViewModelClass newItem = new RecyclerViewModelClass(title, description, "temp", "temp", false);
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