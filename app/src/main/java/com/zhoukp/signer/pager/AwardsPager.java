package com.zhoukp.signer.pager;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.AwardsPagerListViewAdapter;
import com.zhoukp.signer.bean.AwardsBean;
import com.zhoukp.signer.utils.AssetsHelper;
import com.zhoukp.signer.view.DropDownListView;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/1/31 20:00
 * @email 275557625@qq.com
 * @function
 */

public class AwardsPager extends BasePager {

    private DropDownListView dropDownListView;
    private ListView listView;

    private AwardsPagerListViewAdapter adapter;

    private ArrayList<String> datas;

    public AwardsPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_awards, null);
        dropDownListView = view.findViewById(R.id.dropDownListView);
        listView = view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        datas.add("2014-2015学年");
        datas.add("2015-2016学年");
        datas.add("2016-2017学年");
        datas.add("2017-2018学年");
        dropDownListView.setItemsData(datas);

        AwardsBean bean = new Gson().fromJson(AssetsHelper.readAssetsTxt(context, "awards_json"), AwardsBean.class);
        adapter = new AwardsPagerListViewAdapter(context, bean);
        listView.setAdapter(adapter);
    }
}
