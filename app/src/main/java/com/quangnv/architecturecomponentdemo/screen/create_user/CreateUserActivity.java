package com.quangnv.architecturecomponentdemo.screen.create_user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quangnv.architecturecomponentdemo.util.Injection;
import com.quangnv.architecturecomponentdemo.R;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.util.rx.BaseSchedulerProvider;
import com.quangnv.architecturecomponentdemo.util.rx.SchedulerProvider;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener,
        CreateUserContract.View {

    private EditText mTextFirstName;
    private EditText mTextLastName;
    private Button mButtonSave;

    private CreateUserContract.Presenter mPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, CreateUserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        getSupportActionBar().setTitle("Create user");

        initViews();
        registerListener();

        UserRepository repository = Injection.provideUserRepository(this);
        BaseSchedulerProvider scheduler = SchedulerProvider.getInstance();
        mPresenter = new CreateUserPresenter(repository, scheduler);
        mPresenter.setView(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_save) {
            String firstName = mTextFirstName.getText().toString();
            String lastName = mTextLastName.getText().toString();
            mPresenter.createUser(firstName, lastName);
        }
    }

    @Override
    public void showValidateFirstName() {
        toast("First name is not empty!");
    }

    @Override
    public void showValidateLastName() {
        toast("Last name is not empty!");
    }

    @Override
    public void showAddUserSuccessful() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void initViews() {
        mTextFirstName = findViewById(R.id.text_first_name);
        mTextLastName = findViewById(R.id.text_last_name);
        mButtonSave = findViewById(R.id.button_save);
    }

    private void registerListener() {
        mButtonSave.setOnClickListener(this);
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
