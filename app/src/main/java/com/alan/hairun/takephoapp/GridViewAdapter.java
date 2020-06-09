package com.alan.hairun.takephoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * @author: Alan
 * @date: 2020/5/24 0024
 * @time: 上午 10:24
 * @deprecated:
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public GridViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imageview1);
            viewHolder.textView = convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(list.get(position)).into(viewHolder.imageView);
        viewHolder.textView.setText("长按删除：" + list.get(position).substring(list.get(position).lastIndexOf("/") + 1));

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
