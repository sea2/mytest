package com.example.test.mytestdemo.xmlPull.XmlParserDemo;

/**
 * Created by lhy on 2018/1/17.
 */

public class Rank360Info {


    String level;
    String name;
    String detailUrl;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }


    @Override
    public String toString() {
        return "Rank360Info{" +
                "level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                '}';
    }
}
