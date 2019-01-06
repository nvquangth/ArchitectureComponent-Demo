package com.quangnv.architecturecomponentdemo.screen.create_user;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.util.rx.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 06/01/2019
 */

public class CreateUserPresenter implements CreateUserContract.Presenter {

    private CreateUserContract.View mView;
    private UserRepository mRepository;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mCompositeDisposable;

    public CreateUserPresenter(UserRepository repository, BaseSchedulerProvider scheduler) {
        mRepository = repository;
        mScheduler = scheduler;
        mCompositeDisposable = new CompositeDisposable();
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
        Disposable disposable = mRepository.insert(user)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        mView.showAddUserSuccessful();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
