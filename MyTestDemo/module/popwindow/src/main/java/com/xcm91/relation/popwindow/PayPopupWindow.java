package com.xcm91.relation.popwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by lhy on 2018/1/16.
 */

public class PayPopupWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private Activity mContext;
    private Button btn_confirm_pay;
    private TextView newPayWay;
    private ImageView btn_close_popupwindow;

    public PayPopupWindow(final Activity context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pay_popu_window, null);
        int width = new ScreenUtil(context).getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ScreenUtil.dip2px(mContext, 440));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        /*设置触摸外面时消失*/
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        //显示popular
        initPopupWindowView();
        initListener();
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }

    public void initPopupWindowView() {
        btn_confirm_pay = (Button) conentView.findViewById(R.id.btn_confirm_pay);
        newPayWay = (TextView) conentView.findViewById(R.id.tv_select_new_pay);
        btn_close_popupwindow = (ImageView) conentView.findViewById(R.id.btn_close_popupwindow);
    }


    private void initListener() {
        btn_confirm_pay.setOnClickListener(this);
        newPayWay.setOnClickListener(this);
        btn_close_popupwindow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认支付按钮
            case R.id.btn_confirm_pay:
                break;
            case R.id.tv_select_new_pay:
                break;
            case R.id.btn_close_popupwindow:
                this.dismiss();
                break;
        }
    }


}