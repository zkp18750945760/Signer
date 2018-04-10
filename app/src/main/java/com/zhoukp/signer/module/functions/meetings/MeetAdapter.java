package com.zhoukp.signer.module.functions.meetings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.login.UserUtil;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/4/9 22:22
 * @email 275557625@qq.com
 * @function
 */
public class MeetAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MeetBean.DataBean> data;
    private MeetPresenter presenter;

    public MeetAdapter(Context context, ArrayList<MeetBean.DataBean> data, MeetPresenter presenter) {
        this.context = context;
        this.data = data;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MeetBean.DataBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoleder viewHoleder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.meet_item, null);
            viewHoleder = new ViewHoleder();
            viewHoleder.btnMark = convertView.findViewById(R.id.btnMark);
            viewHoleder.tvTheme = convertView.findViewById(R.id.tvTheme);
            viewHoleder.tvDate = convertView.findViewById(R.id.tvDate);
            viewHoleder.tvAbstract = convertView.findViewById(R.id.tvAbstract);
            viewHoleder.tvBrowse = convertView.findViewById(R.id.tvBrowse);
            viewHoleder.ivBrowsed = convertView.findViewById(R.id.ivBrowsed);

            convertView.setTag(viewHoleder);
        } else {
            viewHoleder = (ViewHoleder) convertView.getTag();
        }

        viewHoleder.tvTheme.setText(getItem(position).getTheme());
        viewHoleder.tvDate.setText(getItem(position).getDate());
        viewHoleder.tvAbstract.setText(getItem(position).getAbstractX());
        viewHoleder.tvBrowse.setText(getItem(position).getBrowse());

        if (getItem(position).isRead()) {
            viewHoleder.ivBrowsed.setVisibility(View.VISIBLE);
            viewHoleder.btnMark.setVisibility(View.GONE);

            viewHoleder.btnMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //标记已读
                    presenter.updateDiscussionRead(UserUtil.getInstance().getUser().getUserId(),
                            getItem(position).getTheme(),
                            getItem(position).getDate(), position);
                }
            });

        } else {
            viewHoleder.ivBrowsed.setVisibility(View.GONE);
            viewHoleder.btnMark.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class ViewHoleder {
        TextView tvTheme, tvDate, tvAbstract, tvBrowse;
        Button btnMark;
        ImageView ivBrowsed;
    }
}
