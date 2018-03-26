package com.zhoukp.signer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhoukp.signer.R;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/1/31 19:13
 * @email 275557625@qq.com
 * @function
 */

public class DropDownListView extends LinearLayout {

    private TextView editText;
    private ImageView imageView;
    private PopupWindow popupWindow;
    private ArrayList<String> dataList;

    public DropDownListView(Context context) {
        this(context, null);
    }

    public DropDownListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View view = layoutInflater.inflate(R.layout.dropdownlist_view, this, true);
        editText = view.findViewById(R.id.tvContent);
        imageView = view.findViewById(R.id.ivExpand);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    showPopWindow();
                } else {
                    closePopWindow();
                }
            }
        });
    }

    /**
     * 打开下拉列表弹窗
     */
    private void showPopWindow() {
        // 加载popupWindow的布局文件
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View contentView = layoutInflater.inflate(R.layout.dropdownlist_popupwindow, null, false);
        ListView listView = contentView.findViewById(R.id.listView);
        listView.setAdapter(new DropDownListAdapter(getContext(), dataList));
        popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(this, 100, 0);
    }

    /**
     * 关闭下拉列表弹窗
     */
    private void closePopWindow() {
        popupWindow.dismiss();
        popupWindow = null;
    }

    /**
     * 设置数据
     *
     * @param list list
     */
    public void setItemsData(ArrayList<String> list) {
        dataList = list;
        editText.setText(list.get(0));
    }

    /**
     * 数据适配器
     */
    private class DropDownListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<String> mData;
        LayoutInflater inflater;

        DropDownListAdapter(Context ctx, ArrayList<String> data) {
            mContext = ctx;
            mData = data;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.dropdown_list_item, null);
                holder = new ViewHolder();
                holder.tvContent = convertView.findViewById(R.id.tvContent);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //设置数据
            holder.tvContent.setText(getItem(position));
            holder.tvContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(getItem(position));
                    closePopWindow();
                }
            });
            return convertView;
        }

    }

    private class ViewHolder {
        TextView tvContent;
    }
}