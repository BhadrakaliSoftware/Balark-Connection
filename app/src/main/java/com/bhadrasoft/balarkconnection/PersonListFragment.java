package com.bhadrasoft.balarkconnection;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhadrasoft.balarkconnection.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PersonListFragment extends Fragment implements ValueEventListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = PersonListFragment.class.getSimpleName();
    private OnListFragmentInteractionListener mListener;
    private ArrayList<User> users = new ArrayList<>();

    RecyclerView recyclerView;
    SwipeRefreshLayout SwipeRefreshLayout;

    public PersonListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PersonListFragment newInstance(int columnCount) {
        PersonListFragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void fetchUserProfiles() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference().child("users");
        userReference.limitToFirst(10);
        userReference.addValueEventListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personitem_list, container, false);

        //Set the ids
        SwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.list);
        SwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setAdapter(new MyPersonItemRecyclerViewAdapter(users, mListener));
        fetchUserProfiles();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        SwipeRefreshLayout.setRefreshing(false);
        users.clear();

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(dataSnapshot.getValue());
        Log.d(TAG, "onDataChange: " + jsonElement.getAsJsonObject());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonElement.toString());
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    User user = gson.fromJson(jsonObject.get(key).toString(), User.class);
                    users.add(user);
                    Log.d(TAG, "onDataChange: " + jsonObject.get(key).toString());
                }
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        SwipeRefreshLayout.setRefreshing(false);
        Log.d(TAG, "dataset cancelled: ");
    }

    @Override
    public void onRefresh() {
        //refresh items
        refreshItems();
    }

    private void refreshItems() {
        //fetch users
        this.fetchUserProfiles();
    }

    public interface OnListFragmentInteractionListener {
    }

}
