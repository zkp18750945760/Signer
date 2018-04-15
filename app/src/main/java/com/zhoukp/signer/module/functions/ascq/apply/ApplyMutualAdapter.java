package com.zhoukp.signer.module.functions.ascq.apply;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;

/**
 * @author zhoukp
 * @time 2018/4/12 16:26
 * @email 275557625@qq.com
 * @function
 */
public class ApplyMutualAdapter extends BaseAdapter {

    private Context context;
    private ClazzStudentsBean data;
    private ApplyMutualPresenter presenter;

    public ApplyMutualAdapter(Context context, ClazzStudentsBean data, ApplyMutualPresenter presenter) {
        this.context = context;
        this.data = data;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return data.getData().size();
    }

    @Override
    public ClazzStudentsBean.DataBean getItem(int position) {
        return data.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.apply_mutual_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvID = convertView.findViewById(R.id.tvID);
            viewHolder.btnSubmit = convertView.findViewById(R.id.btnSubmit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(getItem(position).getStuName());
        viewHolder.tvID.setText(getItem(position).getStuId());

        if (getItem(position).isIsInvite()) {
            viewHolder.btnSubmit.setText("已邀请");
            viewHolder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginBean.UserBean user = UserUtil.getInstance().getUser();
                    presenter.cancelMutual(user.getUserId(), user.getUserGrade(),
                            user.getUserMajor(), user.getUserClass(), getItem(position).getStuId(), position);
                }
            });
        } else {
            viewHolder.btnSubmit.setText("邀请");
            viewHolder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginBean.UserBean user = UserUtil.getInstance().getUser();
                    presenter.applyMutual(user.getUserId(), user.getUserGrade(),
                            user.getUserMajor(), user.getUserClass(), getItem(position).getStuId(), position);
                }
            });
        }


        return convertView;
    }

    class ViewHolder {
        TextView tvName, tvID;
        Button btnSubmit;
    }
}
