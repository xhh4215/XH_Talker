package com.xiaohei.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;
import com.xiaohei.common.R;
import com.xiaohei.factory.model.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setUp(RequestManager manager, Author author){
        if (author==null)
            return;
        setUp(manager, R.drawable.default_portrait,author.getPortrait());
    }
    public void setUp(RequestManager manager,int resourceId,String url){
        if (url==null)
            url ="";
        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .dontAnimate()
                .into(this);
    }
}
