package com.spidertool;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.xcm91.relation.glide.R;

/**
 * Created by long on 2016/10/11.
 * 福利图适配器
 */
public class WelfarePhotoAdapter extends BaseQuickAdapter<ImgInfo> {


    public WelfarePhotoAdapter(Context context) {
        super(context);
    }



    @Override
    protected int attachLayoutRes() {
        return R.layout.item_common_linetogo;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final ImgInfo mImgInfo) {
        final ImageView ivPhoto = holder.getView(R.id.iv_show);
        GlideUrl glideUrl = new GlideUrl(mImgInfo.getImgUrl(), new LazyHeaders.Builder()
                .addHeader("Referer", "http://www.mzitu.com")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0")
                .addHeader("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-cn,zh;q=0.5")
                .addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
                .addHeader("Connection", "keep-alive")
                .build());
        Glide.with(mContext).load(glideUrl).into(ivPhoto);

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, ViewPageActivity.class);
                it.putExtra("url", mImgInfo.getUrl());
                mContext.startActivity(it);
            }
        });
    }




}