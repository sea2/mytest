package com.example.test.mytestdemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.util.DensityUtils;
import com.example.test.mytestdemo.utils.ArraysUtils;
import com.example.test.mytestdemo.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MyCenterMenuAdapter extends BaseAdapter {
    String titleStr = "title";
    private List<String> menuDataList;
    private Context context;
    LayoutInflater inflater;
    List<Integer> listFlag;

    public MyCenterMenuAdapter(Context context) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMenuDataList(List<String> menuList) {
        if (menuDataList == null)
            menuDataList = new ArrayList<String>();
        else menuDataList.clear();
        if (listFlag == null) listFlag = new ArrayList<Integer>();
        else listFlag.clear();

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (ArraysUtils.isEmpty(menuDataList)) {
            return 0;
        }
        return menuDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int returnInt = 1;
        //调整逻辑分配type
        return returnInt;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return menuDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            switch (type) {
                case 0:
                    convertView = inflater.inflate(R.layout.item_common_empty, null);
                    viewHolder.tv_titleinfo = (TextView) convertView.findViewById(R.id.tv_titleinfo);
                    break;
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_common_linetogo, null);
                    viewHolder.tv_item_leftname = (TextView) convertView.findViewById(R.id.tv_item_leftname);
                    viewHolder.tv_item_rightname = (TextView) convertView.findViewById(R.id.tv_item_rightname);
                    viewHolder.ll_view_line = (LinearLayout) convertView.findViewById(R.id.ll_view_line);
                    viewHolder.iv_maun_icon = (ImageView) convertView.findViewById(R.id.iv_maun_icon);
                    viewHolder.rl_useful_record = (RelativeLayout) convertView.findViewById(R.id.rl_useful_record);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String menuItem = menuDataList.get(position);
        int positionSize = position + 1;
        if (menuItem != null) {
            switch (type) {
                case 0:
                    viewHolder.tv_titleinfo.setText("");
                    break;
                case 1:
                    if (!StringUtils.isEmpty(menuItem)) {
                        Drawable common_goto = ContextCompat.getDrawable(context, R.drawable.arrow_down);
                        common_goto.setBounds(0, 0, common_goto.getMinimumWidth(), common_goto.getMinimumHeight());
                        viewHolder.tv_item_rightname.setCompoundDrawablePadding((int) DensityUtils.dp2px(context, 5f));
                        viewHolder.tv_item_rightname.setCompoundDrawables(null, null, common_goto, null);
                        viewHolder.rl_useful_record.setBackgroundResource(R.drawable.arrow_up);
                    } else {
                        viewHolder.tv_item_rightname.setCompoundDrawables(null, null, null, null);
                        viewHolder.rl_useful_record.setBackgroundResource(R.color.background);
                        viewHolder.tv_item_rightname.setText("1111");
                    }
            }
        }
        return convertView;
    }


    private static class ViewHolder {
        TextView tv_item_leftname;
        TextView tv_item_rightname;
        TextView tv_titleinfo;
        LinearLayout ll_view_line;
        RelativeLayout rl_useful_record;
        ImageView iv_maun_icon;
    }
}
