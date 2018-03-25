package com.zhoukp.signer.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhoukp.signer.pager.BasePager;

/**
 * @author zhoukp
 * @time 2018/1/29 19:04
 * @email 275557625@qq.com
 * @function
 */
@SuppressLint("ValidFragment")
public class ReplaceFragment extends Fragment {

    private BasePager basePager;

    public ReplaceFragment() {
    }

    public ReplaceFragment(BasePager basePager) {
        this.basePager = basePager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return basePager.rootView;
    }
}
