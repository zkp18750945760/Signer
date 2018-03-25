package com.zhoukp.signer.module.activity.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.activity.VolunteerBean;
import com.zhoukp.signer.module.login.UserUtil;

/**
 * @author zhoukp
 * @time 2018/1/31 13:22
 * @email 275557625@qq.com
 * @function
 */

public class VolunteerListViewAdapter extends BaseAdapter {

    private Context context;
    private VolunteerBean bean;
    private VolunteerFragmentPresenter presenter;

    public VolunteerListViewAdapter(Context context, VolunteerBean bean, VolunteerFragmentPresenter presenter) {
        this.context = context;
        this.bean = bean;
        this.presenter = presenter;
    }

    public void setBean(VolunteerBean bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getVolunteers().size();
    }

    @Override
    public VolunteerBean.VolunteersBean getItem(int position) {
        return bean.getVolunteers().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.sports_listview_item, null);
            holder = new ViewHolder();
            holder.tvTheme = convertView.findViewById(R.id.tvTheme);
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            holder.tvAddress = convertView.findViewById(R.id.tvAddress);
            holder.tvJoin = convertView.findViewById(R.id.tvJoin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTheme.setText(getItem(position).getVolName());
        holder.tvDate.setText(getItem(position).getVolTime());
        if (!getItem(position).isEnroll()) {
            //未报名
            holder.tvJoin.setText("立即报名");
            holder.tvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.applyVolunteers(UserUtil.getInstance().getUser().getUserId(),
                            getItem(position).getVolName(),
                            getItem(position).getVolTime());
                }
            });
        } else {
            //已报名
            holder.tvJoin.setText("已报名");
            holder.tvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.cancelApplyVolunteers(UserUtil.getInstance().getUser().getUserId(),
                            getItem(position).getVolName(),
                            getItem(position).getVolTime());
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvTheme, tvDate, tvAddress, tvJoin;
    }
}
