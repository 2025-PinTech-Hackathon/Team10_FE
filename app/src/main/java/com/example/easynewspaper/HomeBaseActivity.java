package com.example.easynewspaper;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
enum EFragment {
    News,
    Quiz,
    Chat,
}

public class HomeBaseActivity extends AppCompatActivity {

    private static HomeBaseActivity instance;

    public static HomeBaseActivity getInstance() {
        return  instance;
    }

    TextView MenuTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //View fragment = findViewById(R.id.MenuFragment);
        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_base);

        MenuTxtView = findViewById(R.id.MenuTxtView);

        openFragment(EFragment.News);

        findViewById(R.id.NewsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(EFragment.News);
            }
        });

        findViewById(R.id.QuizBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(EFragment.Quiz);
            }
        });

        findViewById(R.id.ChatBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(EFragment.Chat);
            }
        });

        findViewById(R.id.ProfileInfoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = getWindow().getDecorView().getRootView();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API 31 이상
                    RenderEffect blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP);
                    rootView.setRenderEffect(blurEffect);
                }

                MainActivity.getInstance().openIntent(EIntent.Profile);
            }
        });
    }

    public void openFragment(EFragment eFragment) {
        Fragment fragment;
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        switch (eFragment){
            case News:
                MenuTxtView.setText("NEWS");
                fragment = new NewsFragment();
                break;
            case Quiz:
                MenuTxtView.setText("QUIZ");
                fragment = new NewsFragment();
                break;
            case Chat:
                MenuTxtView.setText("CHAT");
                fragment = new ChatFragment();
                break;
            default:
                MenuTxtView.setText("NEWS");
                fragment = new NewsFragment();
                break;
        }

        trans.replace(R.id.MenuFragment, fragment);

        trans.commit();
    }

    public void openDetailNews(long id) {
        Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }
}
