package com.example.test.mytestdemo.xmlPull.XmlParserDemo;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.SimpleAdapter;

import com.example.test.mytestdemo.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PullPraserDemo extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.xml_list, new String[]{"id", "name"}, new int[]{R.id.textId, R.id.textName});
        setListAdapter(adapter);
    }

    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        XmlResourceParser xrp = getResources().getXml(R.xml.channels);

        try {
            // 直到文档的结尾处
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 如果遇到了开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();// 获取标签的名字
                    if (tagName.equals("item")) {
                        Map<String, String> map = new HashMap<String, String>();
                        String id = xrp.getAttributeValue(null, "id");// 通过属性名来获取属性值
                        map.put("id", id);
                        String url = xrp.getAttributeValue(1);// 通过属性索引来获取属性值
                        map.put("url", url);
                        map.put("name", xrp.nextText());
                        list.add(map);
                    }
                }
                xrp.next();// 获取解析下一个事件
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }


    public void pullGetRank360() {
        AssetManager assetManager = this.getAssets();
        //使用IO流读取json文件内容
        try {
            InputStream is = assetManager.open("rank360.xml");
            List<Rank360Info> persons = getRank360Infos(is);
            for (Rank360Info rank360Info : persons) {
                Log.i("tag", rank360Info.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * ------------------------使用PULL解析XML-----------------------
     *
     * @param inStream
     */
    public List<Rank360Info> getRank360Infos(InputStream inStream) {
        try {
            List<Rank360Info> rank360Infos = null;
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(inStream, "UTF-8");
            int event = pullParser.getEventType();// 触发第一個事件
            Rank360Info rank360Info = null;
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        rank360Infos = new ArrayList<Rank360Info>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("a".equals(pullParser.getName())) {
                            //获取class的属性
                            String classStr = pullParser.getAttributeValue(null, "class");
                            if (classStr != null && classStr.equals("doc-color-link")) {
                                String name = pullParser.nextText().trim();
                                rank360Info = new Rank360Info();
                                rank360Info.setName(name);
                            }
                            if (classStr != null && classStr.equals("doc-color-link more-info")) {
                                String url = pullParser.nextText().trim();
                                if (rank360Info == null) rank360Info = new Rank360Info();
                                rank360Info.setDetailUrl(url);
                            }
                        }
                        if ("td".equals(pullParser.getName())) {
                            String classStr = pullParser.getAttributeValue(null, "class");
                            if (classStr != null && classStr.equals("pingji")) {
                                String level = pullParser.nextText().trim();
                                if (rank360Info == null) rank360Info = new Rank360Info();
                                rank360Info.setLevel(level);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("tr".equals(pullParser.getName())) {
                            if (rank360Infos != null && rank360Info != null) {
                                rank360Infos.add(rank360Info);
                                rank360Info = null;
                            }
                        }
                        break;
                }
                event = pullParser.next();
            }
            return rank360Infos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
