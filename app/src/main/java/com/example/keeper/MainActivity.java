package com.example.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.keeper.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth myAuth;
    private FirebaseUser firebaseUser;
    private final String HOME = "Home";
    private final String CALENDER = "Calender";
    private final String FOCUS = "Focus";
    private final String PROFILE = "Profile";


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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user();

        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAuth.signOut();
                startActivity(new Intent(MainActivity.this, WellcomeActivity.class));
                finish();
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.setOnTaskAddedListener((title, description, tag, priority) -> {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.framelayout);
                    if(currentFragment instanceof  HomeFragment){
                        ((HomeFragment) currentFragment).addTask(title, description, tag, priority);
                    }
                });
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

            }
        });

        View viewDecor = getWindow().getDecorView();
        viewDecor.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        replaceFragment( new HomeFragment());
        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.index){
                replaceFragment(new HomeFragment());
                binding.title.setText(HOME);
            } else if (item.getItemId() == R.id.calender) {
                replaceFragment(new CalenderFragment());
                binding.title.setText(CALENDER);
            } else if (item.getItemId() == R.id.focus) {
                replaceFragment(new FocusFragment());
                binding.title.setText(FOCUS);
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragment());
                binding.title.setText(PROFILE);
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    private void user(){
        UserManager userManager = new UserManager();
        String userName = firebaseUser.getDisplayName();
        userManager.addUser(new User(userName));
    }
}