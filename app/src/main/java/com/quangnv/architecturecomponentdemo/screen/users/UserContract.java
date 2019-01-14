package com.quangnv.architecturecomponentdemo.screen.users;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.screen.BasePresenter;

import java.util.List;

public interface UserContract {

    interface View {

        void showUsers(List<User> users);

        void showNoUser();

        void notifyUserClear(User user);
    }

    interface Presenter extends BasePresenter<View> {

        void clearUser(User user);
    }
}
