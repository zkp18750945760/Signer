package com.zhoukp.signer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.bean.AllTestBean;

/**
 * @author zhoukp
 * @time 2018/2/4 19:59
 * @email 275557625@qq.com
 * @function
 */

public class AllTestListViewAdapter extends BaseAdapter {

    private Context context;
    private AllTestBean bean;

    public AllTestListViewAdapter(Context context, AllTestBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public AllTestBean.DataBean getItem(int position) {
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
            convertView = View.inflate(context, R.layout.alltest_listview_item, null);
            holder = new ViewHolder();
            holder.tvItem = convertView.findViewById(R.id.tvItem);
            holder.tvFull = convertView.findViewById(R.id.tvFull);
            holder.tvScore = convertView.findViewById(R.id.tvScore);
            holder.tvAbstract = convertView.findViewById(R.id.tvAbstract);
            holder.btnBrowse = convertView.findViewById(R.id.btnBrowse);
            holder.btnSubmit = convertView.findViewById(R.id.btnSubmit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvItem.setText(getItem(position).getItem());
        holder.tvAbstract.setText(getItem(position).getAbstractX());
        holder.tvFull.setText("/" + getItem(position).getFull());
        holder.tvScore.setText(getItem(position).getScore() + "");

        return convertView;
    }

    private class ViewHolder {
        TextView tvItem, tvFull, tvScore, tvAbstract;
        Button btnBrowse, btnSubmit;
    }
}
