package com.bhadrasoft.balarkconnection;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhadrasoft.balarkconnection.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = AppCompatActivity.class.getSimpleName();

    @BindView(R.id.activity_login_input_email)
    EditText etEmail;

    @BindView(R.id.activity_login_input_password)
    EditText etPassword;

    @BindView(R.id.activity_login_button_login)
    Button buttonLogin;


    @BindView(R.id.activity_login_tv_create_new_account)
    TextView tvCreateNewAccount;

    private FirebaseAuth mFirebaseAuth;
    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setupViews();
        initListeners();
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        etEmail.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        tvCreateNewAccount.setOnClickListener(this);
    }

    private void setupViews() {
    }

    private void init() {
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in and update UI accrodingly
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            gotoMainActivity();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.activity_login_button_login:
                if (isValidInput()) {
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    signInWithEmailAndPassword(email, password);
                }
                break;
            case R.id.activity_login_tv_create_new_account:
                gotoRegistration();
                break;
        }
    }

    private void gotoRegistration() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_REGISTRATION);
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                            gotoMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private boolean isValidInput() {
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
