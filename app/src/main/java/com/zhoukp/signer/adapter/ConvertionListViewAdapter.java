package com.zhoukp.signer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.bean.ConvertionBean;

/**
 * @author zhoukp
 * @time 2018/2/3 20:40
 * @email 275557625@qq.com
 * @function
 */

public class ConvertionListViewAdapter extends BaseAdapter {

    private Context context;
    private ConvertionBean bean;

    public ConvertionListViewAdapter(Context context, ConvertionBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public ConvertionBean.DataBean getItem(int position) {
        return bean.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoleder holeder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.convertion_listview_item, null);
            holeder = new ViewHoleder();
            holeder.tvTheme = convertView.findViewById(R.id.tvTheme);
            holeder.tvDate = convertView.findViewById(R.id.tvDate);
            holeder.tvAbstract = convertView.findViewById(R.id.tvAbstract);
            holeder.tvBrowse = convertView.findViewById(R.id.tvBrowse);
            holeder.btnMark = convertView.findViewById(R.id.btnMark);
            holeder.ivBrowsed = convertView.findViewById(R.id.ivBrowsed);
            convertView.setTag(holeder);
        } else {
            holeder = (ViewHoleder) convertView.getTag();
        }

        holeder.tvTheme.setText(getItem(position).getTheme());
        holeder.tvDate.setText(getItem(position).getDate());
        holeder.tvAbstract.setText(getItem(position).getAbstractX());
        holeder.tvBrowse.setText(getItem(position).getBrowse());

        holeder.btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holeder.ivBrowsed.setVisibility(View.VISIBLE);
                holeder.btnMark.setVisibility(View.GONE);
            }
        });

        return convertView;
    }

    private class ViewHoleder {
        TextView tvTheme, tvDate, tvAbstract, tvBrowse;
        Button btnMark;
        ImageView ivBrowsed;
    }
}
