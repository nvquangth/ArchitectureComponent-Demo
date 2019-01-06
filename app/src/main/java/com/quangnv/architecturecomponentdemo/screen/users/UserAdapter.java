package com.quangnv.architecturecomponentdemo.screen.users;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.quangnv.architecturecomponentdemo.R;
import com.quangnv.architecturecomponentdemo.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {

    private LayoutInflater mInflater;
    private List<User> mUsers;
    private OnClearUserListenter mOnClearUserListenter;

    public UserAdapter(Context context, OnClearUserListenter onClearUserListenter) {
        mInflater = LayoutInflater.from(context);
        mUsers = new ArrayList<>();
        mOnClearUserListenter = onClearUserListenter;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item, viewGroup, false);
        return new ItemViewHolder(view, mOnClearUserListenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindView(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(List<User> users) {
        if (users == null || users.isEmpty()) return;
        mUsers = users;
        notifyDataSetChanged();
    }

    public void addUser(User user) {
        if (user == null) return;
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1);
    }

    public void removeUser(User user) {
        int position = mUsers.indexOf(user);
        mUsers.remove(position);
        notifyItemRemoved(position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextId;
        private TextView mTextFirstName;
        private TextView mTextLastName;
        private ImageButton mButtonClear;

        private OnClearUserListenter mOnClearUserListenter;

        private User mUser;

        public ItemViewHolder(@NonNull View itemView, OnClearUserListenter onClearUserListenter) {
            super(itemView);

            mTextId = itemView.findViewById(R.id.text_id);
            mTextFirstName = itemView.findViewById(R.id.text_first_name);
            mTextLastName = itemView.findViewById(R.id.text_last_name);
            mButtonClear = itemView.findViewById(R.id.button_clear);
            mButtonClear.setOnClickListener(this);

            mOnClearUserListenter = onClearUserListenter;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button_clear) {
                mOnClearUserListenter.onClearUser(mUser);
            }
        }

        private void bindView(User user) {
            mUser = user;
            mTextId.setText(String.valueOf(user.getId()));
            mTextFirstName.setText(user.getFirstName());
            mTextLastName.setText(user.getLastName());
        }
    }

    interface OnClearUserListenter {

        void onClearUser(User user);
    }
}
