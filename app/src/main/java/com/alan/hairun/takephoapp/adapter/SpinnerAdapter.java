package com.alan.hairun.takephoapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.gen.ProjectBeanDao;
import com.alan.hairun.takephoapp.R;
import com.alan.hairun.takephoapp.bean.ProjectBean;
import com.alan.hairun.takephoapp.config.MyApplication;

import java.util.List;

/**
 * @author: Alan
 * @date: 2020/5/10 0010
 * @time: 上午 12:55
 * @deprecated:
 */
public class SpinnerAdapter extends BaseAdapter {
    Context context;
    List<String> list;

    public SpinnerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void removeItem(int position){
        list.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String bean){
        list.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = view.findViewById(R.id.tvName);
//            viewHolder.imgDelet = view.findViewById(R.id.imgDelet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(list.get(i));
      /*  //删除
        viewHolder.imgDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("删除提示");
                builder.setMessage("确定删除该项目及相关照片吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DaoSession daoSession = MyApplication.getINSTANT().getDaoSession();
                        daoSession.getProjectBeanDao().queryBuilder().where(ProjectBeanDao.Properties.Name.eq(list.get(i)))
                                .buildDelete().executeDeleteWithoutDetachingEntities();
                        removeItem(i);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });*/
        return view;
    }

    class ViewHolder {
        TextView tvName;
//        ImageButton imgDelet;
    }

}
