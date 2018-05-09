package com.xcm91.relation.iosdialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.xcm91.relation.iosdialog.InvestDetialModel;
import com.xcm91.relation.iosdialog.R;
import com.xcm91.relation.iosdialog.ScreenUtil;

import java.util.List;

/**
 * Created by lhy on 2018/4/3.
 */
public class CustomActionSheetDialog {


    private Display display;
    private Context context;
    private Dialog dialog;
    private boolean showTitle = false;
    private List<InvestDetialModel.OperationalItem> sheetItemList;
    private ListView lv_dialog_bottom_custom;
    private Button btn_dialog_bottom;
    private LinearLayout ll_content_dialog_bottom;

    public CustomActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null)
            display = windowManager.getDefaultDisplay();
    }

    public CustomActionSheetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_custon_action, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(ScreenUtil.getInstance(context).getWidth());

        // 获取自定义Dialog布局中的控件
        lv_dialog_bottom_custom = (ListView) view.findViewById(R.id.lv_dialog_bottom_custom);
        btn_dialog_bottom = (Button) view.findViewById(R.id.btn_dialog_bottom);
        ll_content_dialog_bottom = (LinearLayout) view.findViewById(R.id.ll_content_dialog_bottom);
        btn_dialog_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle_NoAnimation);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
        }
        return this;
    }


    public CustomActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CustomActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public CustomActionSheetDialog setList(List<InvestDetialModel.OperationalItem> sheetItemList) {
        this.sheetItemList = sheetItemList;
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // TODO 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_content_dialog_bottom.getLayoutParams();
            params.height = display.getHeight() / 2;
            ll_content_dialog_bottom.setLayoutParams(params);
        }


    }


    public void show() {
        dialog.show();
        lv_dialog_bottom_custom.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return  sheetItemList.size();
            }

            @Override
            public Object getItem(int position) {
                return sheetItemList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(context).inflate(R.layout.list_item_detail_action, null);
                return view;
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        setSheetItems();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        CustomActionSheetDialog.OnSheetItemClickListener itemClickListener;
        CustomActionSheetDialog.SheetItemColor color;

        public SheetItem(String name, CustomActionSheetDialog.SheetItemColor color, CustomActionSheetDialog.OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
