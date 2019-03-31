package com.example.idong.mbanking.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by idong on 01/02/2018.
 */

public class CustomImageViewRounded extends android.support.v7.widget.AppCompatImageView {
    public static float radius = 1800.0f;

    public CustomImageViewRounded(Context context) {
        super(context);
    }

    public CustomImageViewRounded(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageViewRounded(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 36.0f;
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
