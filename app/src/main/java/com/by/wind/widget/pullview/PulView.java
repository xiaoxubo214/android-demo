package com.by.wind.widget.pullview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DELL on 2018/3/12.
 */

public class PulView extends View {
    private Paint mCirclePaint;
    private int mCircleRadius = 200;

    public PulView(Context context) {
        super(context);
        init();
    }

    public PulView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PulView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PulView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFF000000);
        mCirclePaint = p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = getWidth()>>1;
        float y = getHeight()>>1;
        canvas.drawCircle(x,y,mCircleRadius,mCirclePaint);
    }
}
