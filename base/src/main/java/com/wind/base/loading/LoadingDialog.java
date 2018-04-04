package com.wind.base.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

import com.wind.base.R;
import com.wind.base.loading.indicators.BallPulseIndicator;
import com.wind.base.loading.indicators.LoadingIndicatorView;

/**
 *
 */

public class LoadingDialog extends Dialog {

    private Context mContext;
    private boolean mCancelable;
    private final WeakReference<Context> reference;
    private ProgressCancelListener mProgressCancelListener;
    LoadingIndicatorView mDialogView;


    public LoadingDialog(Context context, boolean cancelable) {
        super(context, R.style.indeterminate_dialog);
        mContext = context;
        this.reference = new WeakReference<>(context);
        this.mCancelable = cancelable;
        initUI();
    }

    public LoadingDialog(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super(context, R.style.indeterminate_dialog);
        mContext = context;
        this.reference = new WeakReference<>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.mCancelable = cancelable;
        initUI();
    }


    private void initUI(){

        mContext = reference.get();
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.custom_loding_dialog,null);
        mDialogView = dialogView.findViewById(R.id.indicator);
        mDialogView.setDefaultIndicator(new BallPulseIndicator());
        mDialogView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        setCanceledOnTouchOutside(false);
        setCancelable(mCancelable);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(mProgressCancelListener!=null)
                    mProgressCancelListener.onCancelProgress();
            }
        });
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL
                | Gravity.CENTER_HORIZONTAL);
        dialogWindow.setContentView(dialogView,params);
    }
    @Override
    public void show() {
        mContext  = reference.get();
        try {
            if (!isShowing()&&!((Activity) mContext).isFinishing()) {
                super.show();
                mDialogView.smoothToShow();
            }
        } catch(Exception e) {
        }
    }

    @Override
    public void dismiss() {
        mContext  = reference.get();
        if (isShowing()) {
            super.dismiss();
            mDialogView.smoothToHide();
        }
    }

    @Override
    public void cancel() {
        try {
            super.cancel();
        } catch(Exception e) {
        }
    }

    public interface ProgressCancelListener {
        void onCancelProgress();
    }
}
