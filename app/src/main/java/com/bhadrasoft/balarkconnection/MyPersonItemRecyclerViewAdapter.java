package com.bhadrasoft.balarkconnection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhadrasoft.balarkconnection.PersonListFragment.OnListFragmentInteractionListener;
import com.bhadrasoft.balarkconnection.models.User;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPersonItemRecyclerViewAdapter extends RecyclerView.Adapter<MyPersonItemRecyclerViewAdapter.ViewHolder> {

    private static final String STRING_BLANK = " ";
    private final List<User> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPersonItemRecyclerViewAdapter(List<User> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_personitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        User user = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.mPersonName.setText(user.getFirstName()+STRING_BLANK+user.getLastName());
        holder.mPersonStatus.setText(user.getNativePlace());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPersonName;
        public final TextView mPersonStatus;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPersonName = (TextView) view.findViewById(R.id.fragment_personitem_fullname);
            mPersonStatus = (TextView) view.findViewById(R.id.fragment_personitem_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPersonStatus.getText() + "'";
        }
    }
}
