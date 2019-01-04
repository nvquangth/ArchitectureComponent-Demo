package com.quangnv.architecturecomponentdemo.screen.users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quangnv.architecturecomponentdemo.Injection;
import com.quangnv.architecturecomponentdemo.R;
import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.screen.create_user.CreateUserActivity;

import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, UserContract.View {

    private static final int REQUEST_CODE = 100;
    private RecyclerView mRecyclerUser;
    private Button mButtonAdd;

    private UserAdapter mUserAdapter;
    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Users");

        initViews();
        registerListener();

        UserRepository repository = Injection.provideUserRepository(this);
        mPresenter = new UserPresenter(repository);
        mPresenter.setView(this);

        mRecyclerUser.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mUserAdapter = new UserAdapter(this);
        mRecyclerUser.setAdapter(mUserAdapter);
        mPresenter.getUsers();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_create_user) {
            gotoCreateUserScreen();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mPresenter.getUsers();
        }
    }

    @Override
    public void showUsers(List<User> users) {
        mUserAdapter.setUsers(users);
    }

    @Override
    public void showNoUser() {
        toast("No data");
    }

    private void gotoCreateUserScreen() {
        startActivityForResult(CreateUserActivity.getIntent(this), REQUEST_CODE);
    }

    private void initViews() {
        mRecyclerUser = findViewById(R.id.recycler_user);
        mButtonAdd = findViewById(R.id.button_create_user);
    }

    private void registerListener() {
        mButtonAdd.setOnClickListener(this);
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
