package com.example.surface.hotelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogInFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth auth;

    private static View view;


    //private static EditText username;

    private static EditText password, emailText;
    private static Button btnLog, btnSignInLog;
    private static LinearLayout loginLayout;
    private static Animation shake;
    private static FragmentManager fragmentManager;

   public LogInFragment() {

   }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance) {
       view = inflater.inflate(R.layout.fragment_log_in, container, false);
       initViews();
       setListeners();

       auth = FirebaseAuth.getInstance();

       return view;
   }

   private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        //username = (EditText) view.findViewById(R.id.etUsername);
        emailText = (EditText) view.findViewById(R.id.etEmailLogIn);
        password = (EditText) view.findViewById(R.id.etPassword);
        btnLog = (Button) view.findViewById(R.id.btnLogIn);
        btnSignInLog = (Button) view.findViewById(R.id.btnSignInLog);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
   }

   private  void setListeners() {
        btnLog.setOnClickListener(this);
        btnSignInLog.setOnClickListener(this);

   }

   public void signUp(View v) {
       fragmentManager
               .beginTransaction()
               .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
               .replace(R.id.frame, new SignUpFragment(),
                       Utils.SignUpFragment).commit();
   }

   @Override
    public void onClick(View v) {

       if (v.getId() == R.id.btnLogIn) {
           validate();
       }
       else if (v.getId() == R.id.btnSignInLog) {
           fragmentManager
                   .beginTransaction()
                   .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                   .replace(R.id.frame, new SignUpFragment(),
                           Utils.SignUpFragment).commit();
       }
   }

   private void LoginUser(String email, final String password) {
       try{
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        loginLayout.startAnimation(shake);
                        Toast.makeText(getActivity(), "Authentication error. Please enter a valid email and password or sign up",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Login Succesful", Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.putExtra("userName", emailText.getText().toString());
                        startActivity(intent);
                    }
                }
            });
       }
       catch (Exception ex) {
           Toast.makeText(getActivity(), "An error has occurred", Toast.LENGTH_SHORT)
                   .show();
       }
   }

   private void validate() {
       //String getUsername = username.getText().toString();
       String getPass = password.getText().toString();

       if (getPass.equals("") || getPass.length() == 0) {
           loginLayout.startAnimation(shake);
           Toast.makeText(getActivity(), "You should enter both username and password",
                   Toast.LENGTH_LONG).show();
       }
       else {
           LoginUser(emailText.getText().toString(), password.getText().toString());

       }
    }
}
