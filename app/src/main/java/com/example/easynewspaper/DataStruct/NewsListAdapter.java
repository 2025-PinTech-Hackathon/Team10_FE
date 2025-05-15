package com.example.easynewspaper.DataStruct;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.easynewspaper.HomeBaseActivity;
import com.example.easynewspaper.R;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<NewsListItem> {
    final Context context;
    private List<NewsListItem> itemList;

    public NewsListAdapter(Context context, List<NewsListItem> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view, parent, false);
        }

        NewsListItem item = itemList.get(position);

        TextView idTxt = convertView.findViewById(R.id.newsIdTxt);
        TextView headerTxtView = convertView.findViewById(R.id.itemHeadTxt);
        TextView contentTxtView = convertView.findViewById(R.id.itemContentTxt);
        Button detailLoadBtn = convertView.findViewById(R.id.LoadNewsBtn);

        idTxt.setText(((Long)item.id).toString());
        headerTxtView.setText(item.headerTxt);
        contentTxtView.setText(item.contentTxt);
        detailLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeBaseActivity.getInstance().openDetailNews(item.id);
            }
        });

        return convertView;
    }
}

