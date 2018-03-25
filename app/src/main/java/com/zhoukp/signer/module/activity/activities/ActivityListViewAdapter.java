package com.zhoukp.signer.module.activity.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.activity.ActivityBean;
import com.zhoukp.signer.module.login.UserUtil;

/**
 * @author zhoukp
 * @time 2018/1/31 13:22
 * @email 275557625@qq.com
 * @function
 */

public class ActivityListViewAdapter extends BaseAdapter {

    private Context context;
    private ActivityBean bean;
    private ActivityFragmentPresenter presenter;

    public ActivityListViewAdapter(Context context, ActivityBean bean, ActivityFragmentPresenter presenter) {
        this.context = context;
        this.bean = bean;
        this.presenter = presenter;
    }

    public void setBean(ActivityBean bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getActivities().size();
    }

    @Override
    public ActivityBean.ActivitiesBean getItem(int position) {
        return bean.getActivities().get(position);
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
        holder.tvTheme.setText(getItem(position).getActName());
        holder.tvDate.setText(getItem(position).getActTime());
        holder.tvAddress.setText(getItem(position).getActLocation());
        if (!getItem(position).isEnroll()) {
            holder.tvJoin.setText("立即报名");
            //报名活动点击事件的处理
            holder.tvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.applyActivities(UserUtil.getInstance().getUser().getUserId(),
                            getItem(position).getActName(),
                            getItem(position).getActTime());
                }
            });
        } else {
            holder.tvJoin.setText("已报名");
            holder.tvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.cancelApplyActivities(UserUtil.getInstance().getUser().getUserId(),
                            getItem(position).getActName(),
                            getItem(position).getActTime());
                }
            });

        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvTheme, tvDate, tvAddress, tvJoin;
    }
}
