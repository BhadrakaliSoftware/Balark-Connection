package com.bhadrasoft.balarkconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bhadrasoft.balarkconnection.Utils.Constants;
import com.bhadrasoft.balarkconnection.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    @BindView(R.id.layout_personal_details_header)
    RelativeLayout tvPersonalDetailHeader;

    @BindView(R.id.layout_personal_details_ll_details)
    LinearLayout llPersonalDetails;

    @BindView(R.id.layout_personal_details_img_edit)
    ImageView imgEditPersonalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        tvPersonalDetailHeader.setOnClickListener(this);
        imgEditPersonalDetails.setOnClickListener(this);
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_personal_details_header:
                if (llPersonalDetails.getVisibility() == View.GONE) {
                    llPersonalDetails.setVisibility(View.VISIBLE);
                } else {
                    llPersonalDetails.setVisibility(View.GONE);
                }
                break;
            case R.id.layout_personal_details_img_edit:
                showEditScreen();
                break;
        }
    }

    private void showEditScreen() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_PROFILE);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        User user = dataSnapshot.getValue(User.class);
        Log.d(TAG, "onDataChange: user" + user);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

        System.out.println("The read failed: " + databaseError.getCode());
    }
}
