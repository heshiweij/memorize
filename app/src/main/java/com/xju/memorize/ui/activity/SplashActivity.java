package com.xju.memorize.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.xju.memorize.R;
import com.xju.memorize.util.StatusBarUtil;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("NonConstantResourceId")
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.toString();

    private RelativeLayout one_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        one_rl = findViewById(R.id.one_rl);
        StatusBarUtil.setGradientColor(SplashActivity.this, one_rl);
        StatusBarUtil.setLightStatusBar(SplashActivity.this, true, true);

        new Handler().postDelayed(new Runnable() {
            private Intent intent;

            @Override
            public void run() {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
