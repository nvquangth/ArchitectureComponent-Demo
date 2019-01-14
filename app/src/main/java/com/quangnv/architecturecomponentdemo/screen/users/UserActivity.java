package com.quangnv.architecturecomponentdemo.screen.users;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quangnv.architecturecomponentdemo.R;
import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.screen.create_user.CreateUserActivity;
import com.quangnv.architecturecomponentdemo.util.Injection;
import com.quangnv.architecturecomponentdemo.util.rx.BaseSchedulerProvider;
import com.quangnv.architecturecomponentdemo.util.rx.SchedulerProvider;

import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener,
        UserAdapter.OnClearUserListenter,
        UserContract.View {

    private static final int REQUEST_CODE = 100;
    private RecyclerView mRecyclerUser;
    private Button mButtonAdd;

    private UserAdapter mUserAdapter;
    private UserContract.Presenter mPresenter;
    private UserViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Users");

        initViews();
        registerListener();

        UserRepository repository = Injection.provideUserRepository(this);
        BaseSchedulerProvider scheduler = SchedulerProvider.getInstance();
        mPresenter = new UserPresenter(repository, scheduler);
        mPresenter.setView(this);

        mRecyclerUser.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mUserAdapter = new UserAdapter(this, this);
        mRecyclerUser.setAdapter(mUserAdapter);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.setRepository(repository);
        mViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users == null || users.isEmpty()) {
                    showNoUser();
                    return;
                }
                mUserAdapter.setUsers(users);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_create_user) {
            gotoCreateUserScreen();
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

    @Override
    public void notifyUserClear(User user) {
//        mUserAdapter.removeUser(user);
    }

    @Override
    public void onClearUser(User user) {
        mPresenter.clearUser(user);
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
