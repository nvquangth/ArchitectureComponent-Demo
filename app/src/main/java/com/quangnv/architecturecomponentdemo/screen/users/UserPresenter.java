package com.quangnv.architecturecomponentdemo.screen.users;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.data.source.UserListener;

import java.util.List;

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;
    private UserRepository mRepository;

    public UserPresenter(UserRepository repository) {
        mRepository = repository;
    }

    @Override
    public void getUsers() {
        mRepository.getUsers(new UserListener() {
            @Override
            public void onGetAllUserComplete(List<User> users) {
                if (users == null || users.isEmpty()) {
                    mView.showNoUser();
                    return;
                }
                mView.showUsers(users);
            }
        });
    }

    @Override
    public void setView(UserContract.View view) {
        mView = view;
    }
}
