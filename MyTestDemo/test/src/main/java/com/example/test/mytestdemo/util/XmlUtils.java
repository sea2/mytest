package com.example.test.mytestdemo.util;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhy on 2019/4/4.
 */
public class XmlUtils {

    private Map<String, String> configMap;

    private static XmlUtils mZJConfigUtils = null;

    public static XmlUtils getInstance() {
        if (mZJConfigUtils == null)
            mZJConfigUtils = new XmlUtils();
        return mZJConfigUtils;
    }


    public String getGameId() {
        return getValue("gameinfo", "gameId");
    }

    public String getSDKVersionName() {
        return getValue("gameinfo", "sdkVersionName");
    }


    private String getValue(String nodeName, String attrsName) {
//        if (configMap == null || configMap.size() <= 0) configMap = readXML2Map();
        if (nodeName != null && attrsName != null)
            return configMap.get(nodeName.trim().concat("_").concat(attrsName.trim()));
        else return "";
    }

    public Map<String, String> readXML2Map(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream inputStream = context.getAssets().open("JZConfig.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        int attrCount = parser.getAttributeCount();
                        for (int i = 0; i < attrCount; i++) {
                            map.put(name + "_" + parser.getAttributeName(i), parser.getAttributeValue(i));
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        break;
                }
                eventType = parser.next();
            }
            inputStream.close();
            return map;
        } catch (Exception e) {
            return map;
        }
    }
}
