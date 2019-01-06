package com.quangnv.architecturecomponentdemo.screen.users;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.repository.UserRepository;
import com.quangnv.architecturecomponentdemo.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;
    private UserRepository mRepository;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mCompositeDisposable;

    public UserPresenter(UserRepository repository, BaseSchedulerProvider scheduler) {
        mRepository = repository;
        mScheduler = scheduler;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getUsers() {
        Disposable disposable = mRepository.getUsers()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) {
                        if (users == null || users.isEmpty()) {
                            mView.showNoUser();
                            return;
                        }
                        mView.showUsers(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.showNoUser();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearUser(final User user) {
        Disposable disposable = mRepository.delete(user)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        mView.notifyUserClear(user);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void setView(UserContract.View view) {
        mView = view;
    }
}
