package com.zhoukp.signer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhoukp.signer.R;
import com.zhoukp.signer.bean.MeetingSignBean;
import com.zhoukp.signer.module.functions.sign.SignedHeadIconsBean;
import com.zhoukp.signer.utils.AssetsHelper;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ImageHelper;

/**
 * @author zhoukp
 * @time 2018/2/2 18:36
 * @email 275557625@qq.com
 * @function
 */

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MeetingViewHolder> {

    private Context context;
    private SignedHeadIconsBean bean;

    public MeetingRecyclerViewAdapter(Context context, SignedHeadIconsBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeetingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.metting_sign_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        SignedHeadIconsBean.DataBean dataBean = bean.getData().get(position);
        Glide.with(context).load(Constant.BaseUrl + dataBean.getHeadIconUrl()).into(holder.ivHead);
    }

    @Override
    public int getItemCount() {
        return bean.getData().size();
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        ImageView ivHead;

        MeetingViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.ivHead);
        }
    }
}
