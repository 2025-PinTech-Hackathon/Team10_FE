package com.example.easynewspaper;


import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffect blur = RenderEffect.createBlurEffect(10f, 10f, Shader.TileMode.CLAMP);
            findViewById(R.id.ProfileBgImgView).setRenderEffect(blur);
        }
    }
}
