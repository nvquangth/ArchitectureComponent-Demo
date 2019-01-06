package com.quangnv.architecturecomponentdemo.data.source.local;

import com.quangnv.architecturecomponentdemo.data.model.User;
import com.quangnv.architecturecomponentdemo.data.source.UserDataSource;
import com.quangnv.architecturecomponentdemo.data.source.local.sqlite.UserDao;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;

public class UserLocalDataSource implements UserDataSource.Local {

    private static UserLocalDataSource sInstance;

    private UserDao mUserDao;

    private UserLocalDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    public static UserLocalDataSource getInstance(UserDao userDao) {
        if (sInstance == null) {
            sInstance = new UserLocalDataSource(userDao);
        }
        return sInstance;
    }

    @Override
    public Completable insert(final User user) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                try {
                    mUserDao.insert(user);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Completable update(final User user) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    mUserDao.update(user);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Completable delete(final User user) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                try {
                    mUserDao.delete(user);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<User> getUser(final int userId) {
        return Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() {
                return mUserDao.getUser(userId);
            }
        });
    }

    @Override
    public Observable<List<User>> getUsers() {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() {
                return mUserDao.getUsers();
            }
        });
    }

    @Override
    public Completable deleteAll() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                try {
                    mUserDao.deleteAll();
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<User>> search(final String q) {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() {
                return mUserDao.search(q);
            }
        });
    }
}
