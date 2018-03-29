package com.zhoukp.signer.module.functions.ledgers.second;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/3/28 15:00
 * @email 275557625@qq.com
 * @function
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private SecondLedgerBean bean;

    public ListViewAdapter(Context context, SecondLedgerBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public SecondLedgerBean.DataBean getItem(int position) {
        return bean.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.seond_ledger_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvMonth = convertView.findViewById(R.id.tvMonth);
            viewHolder.tvCount = convertView.findViewById(R.id.tvCount);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvMonth.setText(getItem(position).getMonth() + "月");
        viewHolder.tvCount.setText(getItem(position).getCount() + "次");

        return convertView;
    }

    class ViewHolder {
        TextView tvMonth, tvCount;

    }
}
