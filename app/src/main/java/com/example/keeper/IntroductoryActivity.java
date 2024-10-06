package com.example.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.keeper.databinding.ActivityIntroductoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroductoryActivity extends AppCompatActivity {

    private ActivityIntroductoryBinding binding;
    private ViewPagerAdapter adapter;
    private Intent intent;
    private FirebaseAuth myAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityIntroductoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View viewDecor = getWindow().getDecorView();
        viewDecor.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );

        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();

        if (currentUser != null){
            startActivity(new Intent(IntroductoryActivity.this, MainActivity.class));
        }

        adapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0){
                    binding.back.setVisibility(View.GONE);
                }else {
                    binding.back.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(IntroductoryActivity.this, WellcomeActivity.class);
                startActivity(intent);
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                    binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                } else {
                    intent = new Intent(IntroductoryActivity.this, WellcomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.viewPager.getCurrentItem() > 0) {
                    binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1);
                }
            }
        });
    }
}