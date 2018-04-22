package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/3/27 21:30
 * @email 275557625@qq.com
 * @function
 */

public class XlsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<XlsBean> datas;

    private Bitmap excel, word, pdf;

    public XlsAdapter(Context context, ArrayList<XlsBean> datas) {
        this.context = context;
        this.datas = datas;
        excel = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_xls);
        word = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_word);
        pdf = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_pdf);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public XlsBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.xls_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ivHead = convertView.findViewById(R.id.ivHead);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvSize = convertView.findViewById(R.id.tvSize);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(getItem(position).getName());
        viewHolder.tvSize.setText(getSize(Long.parseLong(getItem(position).getSize())));
        if (getItem(position).getName().contains("xls")) {
            viewHolder.ivHead.setImageBitmap(excel);
        } else if (getItem(position).getName().contains("pdf")) {
            viewHolder.ivHead.setImageBitmap(pdf);
        } else {
            viewHolder.ivHead.setImageBitmap(word);
        }

        return convertView;
    }

    public static String getSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    class ViewHolder {
        TextView tvName, tvSize;
        ImageView ivHead;
    }
}
