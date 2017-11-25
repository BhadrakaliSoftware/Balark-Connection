package com.bhadrasoft.balarkconnection;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bhadrasoft.balarkconnection.Utils.Constants;
import com.bhadrasoft.balarkconnection.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, DatabaseReference.CompletionListener {

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    private DatePickerDialog datePickerDialog;

    @BindView(R.id.actvity_registration_input_firstname)
    EditText txtFirstName;

    @BindView(R.id.actvity_registration_input_middlename)
    EditText txtMiddleName;

    @BindView(R.id.actvity_registration_input_lastname)
    EditText txtLastName;

    @BindView(R.id.activity_registration_btn_register)
    Button btnRegister;

    @BindView(R.id.actvity_registration_input_email)
    EditText txtEmail;

    @BindView(R.id.actvity_registration_input_password)
    EditText txtPassword;

    @BindView(R.id.actvity_registration_input_dob)
    EditText txtDOB;

    @BindView(R.id.actvity_registration_input_gautra)
    EditText txtGautra;

    @BindView(R.id.actvity_registration_input_pob)
    EditText txtBirthplace;

    @BindView(R.id.radio_male)
    RadioButton radioMale;

    @BindView(R.id.radio_female)
    RadioButton radioFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        initListeners();
        setupView();
    }

    private void setupView() {
        radioMale.setSelected(true);
    }

    private void initListeners() {
        btnRegister.setOnClickListener(this);
        txtDOB.setOnClickListener(this);
    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_registration_btn_register:
                registerUser();
                break;
            case R.id.activity_registration_layout_dob:
                showDatePicker();
                break;
        }
    }

    private void showDatePicker() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, 2017, 8, 7);
            datePickerDialog.show();
        }
    }

    private void registerUser() {

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                createUser(task);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void createUser(Task<AuthResult> task) {
        Log.d(TAG, "createUser: " + task.getResult().getUser().getUid());

        // Create user
        User user = new User();
        user.setUserId(task.getResult().getUser().getUid());
        user.setFirstName(txtFirstName.getText().toString());
        user.setLastName(txtLastName.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setBirthPlace(txtBirthplace.getText().toString());
        user.setMale(radioMale.isSelected());

        //firebase database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        // Add user entry to the database
        DatabaseReference databaseReference = reference.child("users");
        databaseReference.child(task.getResult().getUser().getUid()).setValue(user, this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String formattedDate = dayOfMonth + "/" + month + "/" + year;
        this.txtDOB.setText(formattedDate);
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

        if (databaseError != null) {
            Toast.makeText(this, databaseError.getMessage(), Toast.LENGTH_SHORT);
        } else {
            finish();
        }
    }
}
