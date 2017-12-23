package com.bhadrasoft.balarkconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bhadrasoft.balarkconnection.models.Occupation;
import com.bhadrasoft.balarkconnection.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditOccupationActivity extends AppCompatActivity implements View.OnClickListener, DatabaseReference.CompletionListener {

    @BindView(R.id.activity_edit_occupation_input_job_title)
    EditText etJobTitle;

    @BindView(R.id.activity_edit_occupation_input_location)
    EditText etLocation;

    @BindView(R.id.activity_edit_occupation_button_save)
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_occupation);
        init();
        initListeners();
    }

    private void initListeners() {
        buttonSave.setOnClickListener(this);
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_edit_occupation_button_save:
                saveUserOccupation();
            break;
        }
    }

    private void saveUserOccupation() {

        //current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference occupationReference = database.getReference().child("occupation");

        Map<String,Object> userOccupation = new HashMap<>();
        userOccupation.put(Occupation.JOB_TITLE, etJobTitle.getText().toString());
        userOccupation.put(Occupation.JOB_LOCATION, etLocation.getText().toString());
        userOccupation.put(Occupation.OCCUPATION_ID, userId);
        userOccupation.put(Occupation.USER_ID, userId);

        occupationReference.updateChildren(userOccupation, this);

    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (databaseError != null) {
            System.out.println("Data could not be saved " + databaseError.getMessage());
        } else {
            System.out.println("Data saved successfully.");
            finish();
        }
    }
}
