package com.example.tuquyet.customview_demo_slider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.example.tuquyet.customview_demo_slider.SlideListener.SlideState.OPEN;

public class MainActivity extends AppCompatActivity implements SlideListener {
    private SlideView mSlideView1;
    private SlideView mSlideView2;
    private SlideView mSlideView3;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideView1 = (SlideView) findViewById(R.id.slideView1);
        mSlideView2 = (SlideView) findViewById(R.id.slideView2);
        mSlideView3 = (SlideView) findViewById(R.id.slideView3);
        mSlideView1.setSlideListener(this);
        mSlideView2.setSlideListener(this);
        mSlideView3.setSlideListener(this);
    }

    @Override
    public void onSlide(View v, float progress, SlideState state) {
        switch (v.getId()) {
            case R.id.slideView1:
                if (state == OPEN) count++;
                break;
            case R.id.slideView3:
                if (state == OPEN) count++;
                break;
        }
        if (count == 2) {
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        }
    }
}

