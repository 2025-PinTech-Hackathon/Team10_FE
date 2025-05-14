package com.example.myapplication.DataStruct;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easynewspaper.R;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ListItem> {
    private Context context;
    private List<ListItem> itemList;

    public CustomAdapter(Context context, List<ListItem> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view, parent, false);
        }

        ListItem item = itemList.get(position);

        TextView headerTxtView = convertView.findViewById(R.id.itemHeadTxt);
        TextView contentTxtView = convertView.findViewById(R.id.itemContentTxt);

        headerTxtView.setText(item.headerTxt);
        contentTxtView.setText(item.contentTxt);

        return convertView;
    }
}

