package com.wind.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.wind.base.event.ActivityLifeCycleEvent;

import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Wind on 2017/11/17.
 */
public abstract class BaseFragment extends Fragment {
    public final String TAG = BaseFragment.class.getSimpleName();
    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();
    protected InputMethodManager inputMethodManager;
    protected Context context;
    protected View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewId = getContentViewId();
        if (viewId != -1 && mRootView == null) {
            this.context = getActivity();
            mRootView = inflater.inflate(viewId, container, false);
            mRootView.setBackgroundColor(ContextCompat.getColor(context,R.color.half_white));
            ButterKnife.bind(this, mRootView);//绑定framgent
            initAllView(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onStart() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START);
        super.onStart();
    }

    @Override
    public void onResume() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
        super.onResume();
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        super.onDestroy();
    }

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    public <E extends View> E findViewById(View root, int id, Class<E> cls) {

        View view = root.findViewById(id);

        if (view != null) return (E) view;

        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /*if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }*/
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    public abstract int getContentViewId();

    protected abstract void initAllView(Bundle savedInstanceState);
}
