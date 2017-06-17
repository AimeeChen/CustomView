package com.czh.custom.bazier.wave;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.czh.custom.R;

/**
 * 用白塞尔曲线实现波浪效果
 * Created by chenzhihua on 2017/6/15.
 */

public class WaveActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
    }


}
