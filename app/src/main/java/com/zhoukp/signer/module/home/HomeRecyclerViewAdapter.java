package com.zhoukp.signer.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/3/21 19:14
 * @email 275557625@qq.com
 * @function
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.KpViewHolder> {
    /**
     * 头部布局
     */
    public static final int TYPE_HEADER = 0;
    /**
     * 其他布局
     */
    public static final int TYPE_NORMAL = 1;

    private Context context;
    private ScheduleBean bean;

    /**
     * 头部布局
     */
    private View headerView;

    /**
     * 设置头部布局
     *
     * @param headerView headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return headerView;
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置item的点击事件
     *
     * @param onItemClickListener onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeRecyclerViewAdapter(Context context, ScheduleBean bean) {
        this.context = context;
        this.bean = bean;
    }

    /**
     * 获取对应位置的视图类型
     *
     * @param position position
     * @return ViewType
     */
    @Override
    public int getItemViewType(int position) {
        if (headerView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   ViewGroup
     * @param viewType viewType
     * @return KpViewHolder
     */
    @Override
    public HomeRecyclerViewAdapter.KpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null && viewType == TYPE_HEADER) {
            return new KpViewHolder(headerView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_normal, parent, false);
        return new KpViewHolder(itemView);
    }

    /**
     * 为ViewHolder绑定数据
     *
     * @param holder   holder
     * @param position position
     */
    @Override
    public void onBindViewHolder(HomeRecyclerViewAdapter.KpViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }

        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvWeek.setText(getItem(position).getWeek());
            holder.tvDate.setText(getItem(position).getDate() + "");
            holder.tvContent.setText(getItem(position).getContent());
            holder.tvTime.setText(getItem(position).getTime());
            holder.tvAddress.setText(getItem(position).getAddress());
        }
    }

    /**
     * 类似ListView中的getItem方法
     *
     * @param position position
     * @return ScheduleBean.DataBean
     */
    public ScheduleBean.DataBean getItem(int position) {
        return bean.getData().get(position - 1);
    }

    /**
     * 因为有headerView，因此返回的itemCount比链表数据的size大1
     *
     * @return size + 1
     */
    @Override
    public int getItemCount() {
        if (bean == null) {
            return 1;
        }
        return bean.getData().size() + 1;
    }


    public class KpViewHolder extends RecyclerView.ViewHolder {
        TextView tvWeek, tvDate, tvContent, tvTime, tvAddress;

        public KpViewHolder(View itemView) {
            super(itemView);

            if (itemView == headerView) {
                return;
            }

            tvWeek = itemView.findViewById(R.id.tvWeek);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvAddress = itemView.findViewById(R.id.tvAddress);

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
