package com.quangnv.architecturecomponentdemo.screen.create_user;

import com.quangnv.architecturecomponentdemo.screen.BasePresenter;

/**
 * Created by quangnv on 06/01/2019
 */

public interface CreateUserContract {

    interface View {

        void showValidateFirstName();

        void showValidateLastName();

        void showAddUserSuccessful();
    }

    interface Presenter extends BasePresenter<View> {

        void createUser(String firstName, String lastName);
    }
}
