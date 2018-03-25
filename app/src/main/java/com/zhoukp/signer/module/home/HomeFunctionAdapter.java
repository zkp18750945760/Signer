package com.zhoukp.signer.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/1/30 11:43
 * @email 275557625@qq.com
 * @function
 */

public class HomeFunctionAdapter extends RecyclerView.Adapter<HomeFunctionAdapter.HomeViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeFunctionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_header, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        switch (position) {
            case 0:
                //科创
                holder.ivIcon.setBackgroundResource(R.drawable.icon_cotrun);
                holder.tvText.setText("科创");
                break;
            case 1:
                //会议签到
                holder.ivIcon.setBackgroundResource(R.drawable.icon_signer);
                holder.tvText.setText("签到");
                break;
            case 2:
                //综测
                holder.ivIcon.setBackgroundResource(R.drawable.icon_alltest);
                holder.tvText.setText("综测");
                break;
            case 3:
                //支书会议
                holder.ivIcon.setBackgroundResource(R.drawable.icon_meeting);
                holder.tvText.setText("支书会议");
                break;
            case 4:
                //台账
                holder.ivIcon.setBackgroundResource(R.drawable.icon_ledger);
                holder.tvText.setText("台账");
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvText;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvText = itemView.findViewById(R.id.tvText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(view, getLayoutPosition());
                    }

                }
            });
        }
    }

    /**
     * 点击某个item的监听接口
     */
    public interface OnItemClickListener {
        /**
         * 某个item被点击的回调
         *
         * @param view     view
         * @param position position
         */
        void OnItemClick(View view, int position);
    }
}
