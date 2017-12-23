package com.bhadrasoft.balarkconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bhadrasoft.balarkconnection.models.Address;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditAddressActiity extends AppCompatActivity implements View.OnClickListener, DatabaseReference.CompletionListener {

    @BindView(R.id.activity_edit_address_input_area)
    EditText etArea;

    @BindView(R.id.activity_edit_address_input_city)
    EditText etCity;

    @BindView(R.id.activity_edit_address_input_country)
    EditText etCountry;

    @BindView(R.id.activity_edit_address_input_district)
    EditText etDisctrict;

    @BindView(R.id.activity_edit_address_input_pincode)
    EditText etPincode;

    @BindView(R.id.activity_edit_address_input_state)
    EditText etState;

    @BindView(R.id.activity_edit_address_input_street)
    EditText etStreet;

    @BindView(R.id.activity_edit_address_button_save)
    Button buttonSave;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_actiity);
        init();
        initListeners();
        loadAddress();
    }

    private void loadAddress() {
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
            case R.id.activity_edit_address_button_save:
                saveUserAddress();
                break;
        }
    }

    private void saveUserAddress() {

        //current user id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference addressReference = database.getReference().child("addresses");

        Map<String, Object> userAddress = new HashMap<>();
        userAddress.put(Address.STREET, etStreet.getText().toString());
        userAddress.put(Address.USER_ID, userId);
        userAddress.put(Address.AREA, etArea.getText().toString());
        userAddress.put(Address.CITY, etCity.getText().toString());
        userAddress.put(Address.COUNTRY, etCountry.getText().toString());
        userAddress.put(Address.DISTRICT, etDisctrict.getText().toString());
        userAddress.put(Address.PINCODE, etPincode.getText().toString());
        userAddress.put(Address.STATE, etState.getText().toString());

        addressReference.child(userId).updateChildren(userAddress, this);
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
