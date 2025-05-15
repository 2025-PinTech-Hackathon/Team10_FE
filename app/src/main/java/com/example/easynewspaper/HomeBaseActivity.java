package com.example.easynewspaper;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //View fragment = findViewById(R.id.MenuFragment);
        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_base);

        openFragment(EFragment.News);
    }

    public void openFragment(EFragment eFragment) {
        Fragment fragment;
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        switch (eFragment){
            case News:
                fragment = new NewsFragment();
                break;
            case Quiz:
                fragment = new NewsFragment();
                break;
            case Chat:
                fragment = new NewsFragment();
                break;
            default:
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
