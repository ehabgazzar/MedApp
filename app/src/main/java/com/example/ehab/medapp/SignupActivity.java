package com.example.ehab.medapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ehab.medapp.fragments.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.auth_up_container)
    ConstraintLayout constraintLayout;
    @BindView(R.id.auth_fragment_container)
    FrameLayout fragment_container;

    @BindView(R.id.auth_up_name)
    EditText etName;
    @BindView(R.id.auth_up_email)
    EditText etEmail;
    @BindView(R.id.auth_up_age)
    EditText etAge;
    @BindView(R.id.auth_up_password)
    EditText etPassword;

    @BindView(R.id.auth_bt_signup)
    Button btSignUp;
    @BindView(R.id.auth_bt_login)
    Button btLogin;

    private Fragment fragment;

    private FirebaseAuth mAuth;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(etEmail.getText().toString(),etPassword.getText().toString());
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragment = new LoginFragment();
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.auth_fragment_container, fragment);

                fragmentTransaction.commit();
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d("createAccount:" , email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Task", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("task", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed."+task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required.");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etName.setError("Required.");
            valid = false;
        } else {
            etName.setError(null);
        }


        String age = etAge.getText().toString();
        if (TextUtils.isEmpty(age)) {
            etAge.setError("Required.");
            valid = false;
        } else {
            etAge.setError(null);
        }

        return valid;
    }
}
