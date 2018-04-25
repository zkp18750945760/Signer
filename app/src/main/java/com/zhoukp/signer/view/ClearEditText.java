package com.zhoukp.signer.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/4/21 16:11
 * @email 275557625@qq.com
 * @function
 */
public class ClearEditText extends RelativeLayout implements View.OnClickListener {

    public ImageButton ibDelete, ibHideKeyboard;
    public EditText etText;

    //当输入框状态改变时，会调用相应的方法
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        //在文字改变后调用
        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                hideBtn();// 隐藏按钮
            } else {
                showBtn();// 显示按钮
            }
        }
    };

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.edit_text, this, true);

        init();

        etText.addTextChangedListener(textWatcher);//为输入框绑定一个监听文字变化的监听器

        ibHideKeyboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(context);
            }
        });
    }

    private void init() {
        ibDelete = findViewById(R.id.ibDelete);
        ibHideKeyboard = findViewById(R.id.ibHideKeyboard);
        etText = findViewById(R.id.etText);

        ibDelete.setOnClickListener(this);
    }

    //隐藏键盘
    public void hideKeyBoard(Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //设置按钮不可见
    public void hideBtn() {
        if (ibDelete.isShown()) {
            ibDelete.setVisibility(View.GONE);
        }
    }

    //设置按钮可见
    public void showBtn() {
        if (!ibDelete.isShown()) {
            ibDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        etText.setText("");
    }
}
