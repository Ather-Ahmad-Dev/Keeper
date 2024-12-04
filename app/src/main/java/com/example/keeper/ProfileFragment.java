package com.example.keeper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.keeper.databinding.ChangeAccountNameDialogBinding;
import com.example.keeper.databinding.ChangePassowrdDialogBinding;
import com.example.keeper.databinding.FragmentProfileBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private FirebaseUser firebaseUser;
    private FirebaseAuth myAuth;

    private ChangeAccountNameDialogBinding changeAccNameDialogBinding;
    private ChangePassowrdDialogBinding changePassDialogBinding;

    private Dialog changeAccNameDialog, changeAccPassDialog;

    private UserManager userManager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        userManager = new UserManager();
        initializeDialogs();
        setOnClickListeners();

        myAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        binding.userName.setText(userManager.getUserName());

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    private void initializeDialogs() {
        changeAccNameDialogBinding = ChangeAccountNameDialogBinding.inflate(getLayoutInflater());
        changePassDialogBinding = ChangePassowrdDialogBinding.inflate(getLayoutInflater());
        changeAccNameDialog = new Dialog(requireContext());
        changeAccPassDialog = new Dialog(requireContext());
        changeAccNameDialog.setContentView(changeAccNameDialogBinding.getRoot());
        changeAccPassDialog.setContentView(changePassDialogBinding.getRoot());
    }

    private void setOnClickListeners(){

        binding.accText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccNameDialog.show();
            }
        });

        changeAccNameDialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccNameDialog.dismiss();
            }
        });

        changeAccNameDialogBinding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserName = changeAccNameDialogBinding.newUserName.getText().toString();
                userManager.setUserName(newUserName);
                binding.userName.setText(newUserName);
                changeAccNameDialog.dismiss();
            }
        });

        binding.passText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccPassDialog.show();
            }
        });

        changePassDialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccPassDialog.dismiss();
            }
        });

        changePassDialogBinding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = firebaseUser.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email
                        , changePassDialogBinding.oldUserPass.getText().toString());

                firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                            firebaseUser.updatePassword(changePassDialogBinding.newUserPass.getText().toString())
                                    .addOnCompleteListener(updateTask -> {
                                       if (updateTask.isSuccessful()){
                                           Toast.makeText(requireContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                           changeAccPassDialog.dismiss();
                                       }else {
                                           Toast.makeText(requireContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                                       }
                                    });
                   }else {
                       Toast.makeText(requireContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show();
                   }
                });
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAuth.signOut();
                startActivity(new Intent(requireContext(), WellcomeActivity.class));
            }
        });
    }
}