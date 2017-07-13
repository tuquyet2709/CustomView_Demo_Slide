package com.example.tuquyet.customview_demo_slider;

import android.view.View;

/**
 * Created by tuquyet on 13/07/2017.
 */
public interface SlideListener {
    enum SlideState {
        OPEN,
        CLOSE
    }
    void onSlide(View v, float progress, SlideState state);
}
