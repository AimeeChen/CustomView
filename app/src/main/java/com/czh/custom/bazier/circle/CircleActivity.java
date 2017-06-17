package com.czh.custom.bazier.circle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.czh.custom.R;

/**
 * 用贝塞尔曲线画圆
 * Created by chenzhihua on 2017/6/16.
 */

public class CircleActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
    }
}
