package com.quangnv.architecturecomponentdemo.screen.create_user;

import android.arch.persistence.room.util.StringUtil;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;

/**
 * Created by quangnv on 06/01/2019
 */

public class CreateUserPresenter implements CreateUserContract.Presenter {

    private CreateUserContract.View mView;
    private UserRepository mRepository;

    public CreateUserPresenter(UserRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(CreateUserContract.View view) {
        mView = view;
    }

    @Override
    public void createUser(String firstName, String lastName) {
        if (firstName.isEmpty()) {
            mView.showValidateFirstName();
            return;
        }
        if (lastName.isEmpty()) {
            mView.showValidateLastName();
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        saveUser(user);
    }

    private void saveUser(User user) {
        mRepository.insert(user);
        mView.showAddUserSuccessful();
    }
}
