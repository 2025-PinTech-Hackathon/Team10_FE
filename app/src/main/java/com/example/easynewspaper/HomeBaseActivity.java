package com.example.easynewspaper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;


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

        NewsFragment fragment = new NewsFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.MenuFragment, fragment);

        trans.commit();
    }

    public void openDetailNews(long id) {
        Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }
}
