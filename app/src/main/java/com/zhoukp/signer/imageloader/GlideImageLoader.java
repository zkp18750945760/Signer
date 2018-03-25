package com.zhoukp.signer.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @author zhoukp
 * @time 2018/1/29 19:48
 * @email 275557625@qq.com
 * @function
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context)
                //图片地址
                .load(path)
                .into(imageView);
    }
}