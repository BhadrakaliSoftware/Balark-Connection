package com.bhadrasoft.balarkconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bhadrasoft.balarkconnection.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bhadrasoft.balarkconnection.models.User.BIRTH_PLACE;
import static com.bhadrasoft.balarkconnection.models.User.BLOOD_GROUP;
import static com.bhadrasoft.balarkconnection.models.User.GAUTRA;
import static com.bhadrasoft.balarkconnection.models.User.GENDER;
import static com.bhadrasoft.balarkconnection.models.User.HEIGHT;
import static com.bhadrasoft.balarkconnection.models.User.LAST_NAME;
import static com.bhadrasoft.balarkconnection.models.User.MIDDLE_NAME;
import static com.bhadrasoft.balarkconnection.models.User.NATIVE;
import static com.bhadrasoft.balarkconnection.models.User.WEIGHT;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, DatabaseReference.CompletionListener, ValueEventListener {

    @BindView(R.id.actvity_edit_profile_input_firstname)
    EditText etFirstName;

    @BindView(R.id.actvity_edit_profile_input_middlename)
    EditText etMiddleName;

    @BindView(R.id.actvity_edit_profile_input_lastname)
    EditText etLastName;

    @BindView(R.id.actvity_edit_profile_input_guatra)
    EditText etGautra;

    @BindView(R.id.actvity_edit_profile_input_native)
    EditText etNative;

    @BindView(R.id.edit_profile_radio_male)
    RadioButton buttonMale;

    @BindView(R.id.edit_profile_radio_female)
    RadioButton buttonFemale;

    @BindView(R.id.actvity_edit_profile_input_birthplace)
    EditText etBirthPlace;

    @BindView(R.id.actvity_edit_profile_input_blood_group)
    EditText etBloodGroup;

    @BindView(R.id.actvity_edit_profile_input_height)
    EditText etHeight;

    @BindView(R.id.actvity_edit_profile_input_weight)
    EditText etWeight;

    @BindView(R.id.activity_edit_profile_button_save)
    Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        initListeners();
        loadUserProfile();
    }

    private void loadUserProfile() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference().child("users").child(userId);

        userReference.addValueEventListener(this);

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
            case R.id.activity_edit_profile_button_save:
                if (isValidInput()) {
                    saveUserProfile();
                } else {

                }
                break;
        }
    }

    private void saveUserProfile() {

        //current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference().child("users").child(userId);

        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put(User.FIRST_NAME, etFirstName.getText().toString());
        userUpdates.put(LAST_NAME, etLastName.getText().toString());
        userUpdates.put(MIDDLE_NAME, etMiddleName.getText().toString());
        userUpdates.put(GAUTRA, etGautra.getText().toString());
        userUpdates.put(NATIVE, etNative.getText().toString());
        userUpdates.put(GENDER, true);
        userUpdates.put(BIRTH_PLACE, etBirthPlace.getText().toString());
        userUpdates.put(BLOOD_GROUP, etBloodGroup.getText().toString());
        userUpdates.put(HEIGHT, etHeight.getText().toString());
        userUpdates.put(WEIGHT, etWeight.getText().toString());

        userReference.updateChildren(userUpdates, this);
    }

    private boolean isValidInput() {
        return true;
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

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        updateUI(user);
    }

    private void updateUI(User user) {
        this.etFirstName.setText(checkforNull(user.getFirstName()).toString());
        this.etLastName.setText(checkforNull(user.getLastName()).toString());
        this.etMiddleName.setText(checkforNull(user.getMiddleName()).toString());
        this.etBirthPlace.setText(checkforNull(user.getBirthPlace()).toString());
        this.etBloodGroup.setText(checkforNull(user.getBloodGroup()).toString());
        this.etNative.setText(checkforNull(user.getNativePlace()).toString());
        this.etGautra.setText(checkforNull(user.getGautra()).toString());
        this.etWeight.setText(checkforNull(user.getWeight()).toString());
        this.etHeight.setText(checkforNull(user.getHeight()).toString());
    }

    private Object checkforNull(Object object) {
        if (object == null) {
            return "";
        } else {
            return object;
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
