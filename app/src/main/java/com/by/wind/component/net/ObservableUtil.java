package com.by.wind.component.net;

import com.wind.base.event.ActivityLifeCycleEvent;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;


/**
 * Created by wind ic_on 17/2/23.
 */

public class ObservableUtil {

    private static ObservableUtil instance;

    private ObservableUtil() {
    }

    public static ObservableUtil getInstance() {
        if (instance == null) {
            instance = new ObservableUtil();
        }
        return instance;
    }

    /**
     * 添加线程管理并订阅
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     * @param isSave           是否缓存
     * @param forceRefresh     是否强制刷新
     */
    public void toSubscribe(rx.Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh) {
        //数据预处理
        rx.Observable.Transformer<RetrofitResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh).subscribe(subscriber);
    }

    /**
     * 添加线程管理
     *
     * @param ob
     * @param cacheKey     缓存kay
     * @param isSave       是否缓存
     * @param forceRefresh 是否强制刷新
     */
    public Observable compose(Observable ob, String cacheKey, boolean isSave, boolean forceRefresh) {
        //数据预处理
        Observable.Transformer<RetrofitResult<Object>, Object> result = RxHelper.handleResult();
        Observable observable = ob.compose(result);
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh);
        return observable;
    }

}
