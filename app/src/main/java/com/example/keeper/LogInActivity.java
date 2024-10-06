package com.example.keeper;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.keeper.databinding.ActivityLogInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;
    private Intent intent;
    private FirebaseAuth myAuth;
    private SignInClient onTapClient;
    private BeginSignInRequest signInRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View viewDecor = getWindow().getDecorView();

        viewDecor.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        myAuth = FirebaseAuth.getInstance();

        binding.main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view != null) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.emailEditText.getText().toString().isEmpty()
                || binding.passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(LogInActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    myAuth.signInWithEmailAndPassword(binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString())
                            .addOnCompleteListener(LogInActivity.this ,new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(LogInActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                        } else {
                                            Toast.makeText(LogInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                }
                            });
                }
            }
        });

        onTapClient = Identity.getSignInClient(this);

        integrateGoogleSignIn();

        binding.loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        binding.loginWithGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                List<String> scopes =
                        new ArrayList<String>() {
                            {
                                add("user:email");
                            }
                        };
                provider.setScopes(scopes);

                Task<AuthResult> pendingResultTask = myAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    pendingResultTask
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LogInActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                } else {
                    myAuth
                            .startActivityForSignInWithProvider( LogInActivity.this, provider.build())
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LogInActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                }
                //onClick Ends
            }
        });

    }

    public void integrateGoogleSignIn(){
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("252405576859-adk8qjtf6fgaj68imcup9q1r4el76cop.apps.googleusercontent.com")
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();
    }

    public void signInWithGoogle(){
        onTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try{
                            startIntentSenderForResult(
                                    beginSignInResult.getPendingIntent().getIntentSender(), 1,
                                    null, 0,0,0
                            );
                        } catch (IntentSender.SendIntentException e){

                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogInActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                try{
                    SignInCredential credential = onTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String userName = credential.getId();
                    String password = credential.getPassword();
                } catch (ApiException e){

                }
                break;
        }
    }

}