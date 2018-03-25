package com.zhoukp.signer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.bean.FirstLedgerBean;

/**
 * @author zhoukp
 * @time 2018/2/4 18:58
 * @email 275557625@qq.com
 * @function
 */

public class FirstLedgerListViewAdapter extends BaseAdapter {

    private Context context;
    private FirstLedgerBean bean;

    public FirstLedgerListViewAdapter(Context context, FirstLedgerBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public FirstLedgerBean.DataBean getItem(int position) {
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
            convertView = View.inflate(context, R.layout.firstledger_listview_item, null);
            holder = new ViewHolder();
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            holder.tvPeriod = convertView.findViewById(R.id.tvPeriod);
            holder.tvCourse = convertView.findViewById(R.id.tvCourse);
            holder.tvSignStatus = convertView.findViewById(R.id.tvSignStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvDate.setText(getItem(position).getDate() + "-" + getItem(position).getWeek());
        holder.tvCourse.setText(getItem(position).getCourse());
        holder.tvPeriod.setText(getItem(position).getPeriod());
        holder.tvSignStatus.setText(getItem(position).getSignstatus());

        return convertView;
    }

    private class ViewHolder {
        TextView tvDate, tvPeriod, tvCourse, tvSignStatus;
    }
}
