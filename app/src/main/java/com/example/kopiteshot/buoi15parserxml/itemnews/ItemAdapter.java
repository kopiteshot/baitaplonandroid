package com.example.kopiteshot.buoi15parserxml.itemnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kopiteshot.buoi15parserxml.R;

import java.util.ArrayList;

/**
 * Created by Kopiteshot on 5/14/2017.
 */

public class ItemAdapter extends ArrayAdapter<ItemNew> {
    private ArrayList<ItemNew> itemNews;
    private LayoutInflater layoutInflater;

    public ItemAdapter(@NonNull Context context, @NonNull ArrayList<ItemNew> itemNews) {
        super(context, android.R.layout.simple_list_item_1, itemNews);
        this.itemNews = itemNews;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.des);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemNew itemNew = itemNews.get(position);
        viewHolder.title.setText(itemNew.getTitle());
        Glide.with(convertView.getContext()).load(itemNew.getImage()).into(viewHolder.image);
        viewHolder.description.setText(itemNew.getPubDate());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
    }
}
