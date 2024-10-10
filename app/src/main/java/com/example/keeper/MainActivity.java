package com.example.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keeper.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth myAuth;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<RecyclerViewModelClass> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myAuth = FirebaseAuth.getInstance();

        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAuth.signOut();
                startActivity(new Intent(MainActivity.this, WellcomeActivity.class));
                finish();
            }
        });

        View viewDecor = getWindow().getDecorView();
        viewDecor.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        binding.allTasks.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
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
}