package com.koushikdutta.widgets;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public final class AnimatedView {
    public static void setOnClickListener(final View view, final OnClickListener listener) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleAnimation scale = new ScaleAnimation(.95f, 1f, .95f, 1f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(250);
                view.startAnimation(scale);
                listener.onClick(view);
            }
        });
    }
}
