package com.czh.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.czh.custom.bazier.circle.CircleActivity;
import com.czh.custom.bazier.wave.WaveActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_path:
                intent = new Intent(MainActivity.this, PathActivity.class);
                break;
            case R.id.btn_wave:
                intent = new Intent(MainActivity.this, WaveActivity.class);
                break;
            case R.id.btn_circle:
                intent = new Intent(MainActivity.this, CircleActivity.class);
                break;
            default:
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}
