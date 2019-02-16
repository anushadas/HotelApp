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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;


public class SignUpFragment extends Fragment implements View.OnClickListener {
        //for firebase
        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;

        private static View view;

        //to hold the values in Spinner
                public static final CharSequence[] REGION_OPTION = {"North America", "Latin America", "Europe", "Africa", "Asia", "Australia"};
        //declaring page elements
        private static EditText passwordSign, passConfirm, emailSign;
        private static Button btnSign;
        private static CheckBox hidePasswordSign;
        private static LinearLayout SignUpLayout;
        private static Animation shake;
        private static FragmentManager fragmentManager;
        private static EditText etfirstName,etlastName;

   public SignUpFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstance) {
            view = inflater.inflate(R.layout.fragment_sign_up, container, false);

            mAuth =  FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            initViews();

            setListeners();


            return view;
        }
        //to initialize the variables
        private void initViews() {

            // Properly initializes and populates the "region" spinner
            Spinner spinner1 = (Spinner) view.findViewById(R.id.spRegion);
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, REGION_OPTION);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
            spinner1.setAdapter(adapter); // Apply the adapter to the spinner


            fragmentManager = getActivity().getSupportFragmentManager();
            passwordSign = (EditText) view.findViewById(R.id.etPasswordSign);
            emailSign = (EditText) view.findViewById(R.id.etEmail);
            passConfirm = (EditText) view.findViewById(R.id.etPassConfirm);
            btnSign = (Button) view.findViewById(R.id.btnSignUp);
            hidePasswordSign = (CheckBox) view.findViewById(R.id.chboxHidePasswordSign);
            SignUpLayout = (LinearLayout) view.findViewById(R.id.signup_layout);
            etfirstName = (EditText) view.findViewById(R.id.etFirstName);
            etlastName = (EditText) view.findViewById(R.id.etLastName);

            shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        }
        //to set onclick listeners for checkbox and button
        private  void setListeners() {
            btnSign.setOnClickListener(this);

            //show and hide password
            hidePasswordSign
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton button,
                                                     boolean isChecked) {


                            if (isChecked) {

                                hidePasswordSign.setText("Hide Password");

                                passwordSign.setInputType(InputType.TYPE_CLASS_TEXT);
                                passwordSign.setTransformationMethod(HideReturnsTransformationMethod
                                        .getInstance());
                            } else {
                                hidePasswordSign.setText("Show Password");

                                passwordSign.setInputType(InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                passwordSign.setTransformationMethod(PasswordTransformationMethod
                                        .getInstance());

                            }

                        }
                    });
        }

        //onclick on the SignUp button
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.btnSignUp) {
                validate();
            }
        }
        //to hold the writing of a user to auth and db
        private void signUpUser(final String email, final String password, final String firstName, final String lastName,final String region){
            try {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete" + task.isSuccessful());
                        if(task.isSuccessful())
                        {
                            // Attempts to login with the recently created user to add it to the database
                            try {

                                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String uid = mAuth.getCurrentUser().getUid().toString();
                                        User user = new User(email, firstName, lastName, region);
                                        mDatabase.child("users").child(uid).setValue(user);
                                        mAuth.signOut(); // Signs out the user, as we only signed in to add it to the DB

                                        fragmentManager
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                                .replace(R.id.frame, new LogInFragment(),
                                                        Utils.LogInFragment).commit();
                                    }
                                });
                            }
                            // if it fails to add the user to the DB
                            catch (Exception ex)    {
                                 Toast.makeText(getActivity(),
                                "Adding User failed, please contact our team at a.matterhotel@gmail.com",
                                Toast.LENGTH_LONG).show();
                            } // End of Catch for signing in


                        }
                        else {
                            Log.d(TAG, "Authentication failed" + task.getException());
                            SignUpLayout.startAnimation(shake);
                            Toast.makeText(getActivity(), "Authentication failed",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
            catch (Exception ex) {
                SignUpLayout.startAnimation(shake);
                Toast.makeText(getActivity(), "An error has occured ¯\\_(ツ)_/¯",
                        Toast.LENGTH_LONG).show();
            }


        }
        //check for a correct email pattern through a regular expression
        public static boolean isValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
        }
        //this method validates the user input and signs him up if everything is correct
        private void validate() {
            //page elements
            String getPass = passwordSign.getText().toString();
            String getEmail = emailSign.getText().toString();
            String getPassConfirm = passConfirm.getText().toString();
            // ADDED to get the values of the new fields
            String firstName = etfirstName.getText().toString();
            String lastName = etlastName.getText().toString();
            Spinner spinner = (Spinner) view.findViewById(R.id.spRegion);
            String region = spinner.getSelectedItem().toString();

            if (getPass.isEmpty()
                || getEmail.isEmpty()
                || getPassConfirm.isEmpty()
                || firstName.isEmpty()
                || lastName.isEmpty())  {

                SignUpLayout.startAnimation(shake);
                Toast.makeText(getActivity(), "All fields are required",
                        Toast.LENGTH_LONG).show();

            }

            else {
                if  (!isValidEmail(getEmail)){
                    emailSign.startAnimation(shake);
                    Toast.makeText(getActivity(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if (getPass.length() < 7) {
                    passwordSign.startAnimation(shake);
                    Toast.makeText(getActivity(), "The password should be at least 8 characters long", Toast.LENGTH_SHORT).show();
                }
                else if (!getPassConfirm.equals(getPass)) {
                    passwordSign.startAnimation(shake);
                    passConfirm.startAnimation(shake);
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    //if the validation was successful, he gets signed up and the object is written to the db
                    signUpUser(emailSign.getText().toString(), passwordSign.getText().toString(),firstName,lastName,region);
                }


            }
        }

}
