package com.example.easynewspaper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
                fragment = new NewsFragment();
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
