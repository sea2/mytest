package com.example.test.mytestdemo.xmlPull.XmlParserDemo;

import android.app.ListActivity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.example.test.mytestdemo.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
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


    /**
     * xml格式字符串解析
     *
     * @param xmlData
     * @throws Exception
     */
    public void parseXMLWithPull(String xmlData) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        String name = "";
        String version = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                // 开始解析某个结点
                case XmlPullParser.START_TAG: {
                    if ("name".equals(nodeName)) {
                        name = parser.nextText();
                    } else if ("version".equals(nodeName)) {
                        version = parser.nextText();
                    }
                    break;
                }
                // 完成解析某个结点
                case XmlPullParser.END_TAG: {
                    if ("app".equals(nodeName)) {
                        Log.d("MainActivity", "name is " + name);
                        Log.d("MainActivity", "version is " + version);
                    }
                    break;
                }
                default:
                    break;
            }
            eventType = parser.next();
        }
    }


}
