package com.zhoukp.signer.module.functions.sign.record;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoukp.signer.R;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/4/6 13:47
 * @email 275557625@qq.com
 * @function
 */
public class SignRecordAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SignRecordBean.DataBean> recordBean;

    public SignRecordAdapter(Context context, ArrayList<SignRecordBean.DataBean> recordBean) {
        this.context = context;
        this.recordBean = recordBean;
    }

    @Override
    public int getCount() {
        return recordBean.size();
    }

    @Override
    public SignRecordBean.DataBean getItem(int position) {
        return recordBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.sign_record_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTime = convertView.findViewById(R.id.tvTime);
            viewHolder.tvContent = convertView.findViewById(R.id.tvContent);
            viewHolder.tvStatus = convertView.findViewById(R.id.tvStatus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTime.setText(getItem(position).getTime());
        viewHolder.tvContent.setText(getItem(position).getContent());
        viewHolder.tvStatus.setText(getStatus(getItem(position).getStatus()));

        return convertView;
    }

    class ViewHolder {
        TextView tvTime, tvContent, tvStatus;
    }

    private String getStatus(String status) {
        if (status.equals("1")) {
            return "准时";
        } else if (status.equals("2")) {
            return "迟到";
        } else {
            return "缺席";
        }
    }
}
