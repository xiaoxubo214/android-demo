package com.by.wind.component.net;

import android.support.annotation.NonNull;
import com.wind.base.event.ActivityLifeCycleEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by wind ic_on 17/2/23.
 */

public class RxHelper {
    /**
     * 利用Observable.takeUntil()停止网络请求
     *
     * @param event
     * @param lifecycleSubject
     * @param <T>
     * @return
     */
    @NonNull
    public <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceObservable) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return sourceObservable.takeUntil(compareLifecycleObservable);
            }
        };
    }


    /**
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<RetrofitResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<RetrofitResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<RetrofitResult<T>> tObservable) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return tObservable.flatMap(new Func1<RetrofitResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(RetrofitResult<T> result) {
                        if ("0".equals(result.getCode())) {
                            if (result.getData() != null) {
                                return createData(result.getData());
                            } else {
                                return (Observable<T>) createMsg(result.getMsg());
                            }

                        } else {
                            return Observable.error(new ServerException(result.getCode(), result.getMsg()));
                        }
                    }
                }).takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<RetrofitResult<T>, T> handleResult() {
        return tObservable -> tObservable.flatMap(result -> {
            if ("0".equals(result.getCode())) {
                if (result.getData() != null) {
                    return createData(result.getData());
                } else {
                    return (Observable<T>) createMsg(result.getMsg());
                }

            } else {
                return Observable.error(new ServerException(result.getCode(), result.getMsg()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onCompleted();
                    subscriber.onNext(data);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }

    /**
     * 创建成功的返回数据
     *
     * @param data
     * @return
     */
    private static Observable<String> createMsg(final String data) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
