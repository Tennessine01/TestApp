package vn.edu.usth.englishdictionary.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.englishdictionary.databinding.ActivitySplashScreenBinding;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainActivity.class));
            SplashScreen.this.finish();
        }, SPLASH_TIME_OUT);
    }
}
