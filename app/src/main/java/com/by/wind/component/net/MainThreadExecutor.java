package com.by.wind.component.net;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.by.wind.util.img.Logger;
import com.by.wind.util.img.Logger;

import java.util.concurrent.Executor;

/**
 * Created by DELL on 2018/3/22.
 */

public class MainThreadExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(@NonNull Runnable runnable) {
        Logger.i("MainThreadExecutor",handler.toString());
        handler.post(runnable);
    }
}
