package com.zhoukp.signer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.bean.AwardsBean;

/**
 * @author zhoukp
 * @time 2018/1/31 20:40
 * @email 275557625@qq.com
 * @function
 */

public class AwardsPagerListViewAdapter extends BaseAdapter {

    private Context context;
    private AwardsBean bean;

    public AwardsPagerListViewAdapter(Context context, AwardsBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public AwardsBean.DataBean getItem(int position) {
        return bean.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.awards_listview_item, null);
            holder = new ViewHolder();
            holder.tvItem = convertView.findViewById(R.id.tvItem);
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItem.setText(getItem(position).getItem());
        holder.tvDate.setText(getItem(position).getDate());
        return convertView;
    }

    private class ViewHolder {
        TextView tvItem, tvDate;
    }
}
