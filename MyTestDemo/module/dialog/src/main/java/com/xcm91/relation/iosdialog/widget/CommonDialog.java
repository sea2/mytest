package com.xcm91.relation.iosdialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xcm91.relation.iosdialog.R;
import com.xcm91.relation.iosdialog.ScreenUtil;


/**
 * 普通dialog
 *
 * @author yejingjie
 */
public class CommonDialog extends Dialog {
    private View mView;
    private TextView tvTitle = null;// 标题
    private TextView tvContent = null;// 内容
    private String TAG = this.getClass().getSimpleName();

    private Button btnConfirm = null;// PositiveButton
    private Button btnCancel = null;// NegativeButton

    public CommonDialog(Context context) {
        super(context, R.style.commonDialog);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        mView = getLayoutInflater().inflate(R.layout.dialog_common_for_ios, null);
        setContentView(mView);
        initViews();
    }

    /**
     * 设置标题头
     *
     * @param title
     */
    public void setTitle(String title) {
        if (title != null) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (null != title) {
            setTitle(title.toString());
        }
    }


    /**
     * 设置内容
     *
     * @param content
     */
    public void setMessage(String content) {
        if (content != null) {
            tvContent.setVisibility(View.VISIBLE);
            /*if (dialogContent.indexOf("\n") >= 0) {
                tvContent.setGravity(Gravity.LEFT);
			}*/
            tvContent.setText(content);
        }
    }

    @SuppressWarnings("deprecation")
    public void setContentHtml(String content) {
        if (content != null) {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(Html.fromHtml(content));
        }
    }

    public void setContentColor(int color) {
        tvContent.setTextColor(color);
    }

    //内容的布局
    public void setContentGravity(int gravityInt) {
        if (tvContent != null) tvContent.setGravity(gravityInt);
    }

    public void setContent(Spanned spanned) {
        if (null != spanned) {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(spanned);
        }
    }

    /**
     * 设置确定按钮
     *
     * @param strConfirm
     */
    public void setPositiveButton(String strConfirm) {
        setPositiveButton(strConfirm, null);
    }

    /**
     * 设置确定按钮
     *
     * @param strConfirm
     * @param listener
     */
    public void setPositiveButton(String strConfirm, final View.OnClickListener listener) {
        btnConfirm.setVisibility(View.VISIBLE);
        if (null != strConfirm) {
            btnConfirm.setText(strConfirm);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onClick(btnConfirm);
                }
            }
        });
    }

    /**
     * 设置取消按钮
     *
     * @param strCancel
     */
    public void setNegativeButton(String strCancel) {
        setNegativeButton(strCancel, null);
    }

    /**
     * 设置取消按钮
     *
     * @param strCancel
     * @param listener
     */
    public void setNegativeButton(String strCancel, final View.OnClickListener listener) {
        btnCancel.setVisibility(View.VISIBLE);
        if (null != strCancel) {
            btnCancel.setText(strCancel);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onClick(btnCancel);
                }
            }
        });


    }


    /**
     * window层显示dialog,不依附与Activity(注：必须加入 <uses-permission
     * android:name="android.permission.SYSTEM_ALERT_WINDOW" />)
     */
    public void setShowOnWindow() {
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);// windows层显示
    }

    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        btnConfirm = (Button) findViewById(R.id.tv_left_submit);
        btnCancel = (Button) findViewById(R.id.tv_right_submit);

    }

    @Override
    public void show() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
        if (getWindow() != null) {
            WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (ScreenUtil.getInstance(getContext()).getWidth() * 0.85); // 宽度设置为屏幕的0.9
            this.getWindow().setAttributes(p);
        }
        super.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }

    @Override
    protected void onStop() {
        super.onStop();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }

    @Override
    public void cancel() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
        super.cancel();

    }


    @Override
    public void dismiss() {
        super.dismiss();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }
}
