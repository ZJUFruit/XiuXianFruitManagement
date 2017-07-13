package com.example.fruit.salerapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by fruit on 2017/7/12.
 */

public class Scan extends AppCompatActivity {
    private ImageView imgRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initView();
    }

    public void initView () {
        imgRotate = (ImageView) findViewById(R.id.scan_rotate_image);
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imgRotate.startAnimation(operatingAnim);
    }
}
