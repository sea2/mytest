package com.spidertool;

/**
 * Created by lhy on 2018/10/24.
 */
public class ImgInfo {

    private String url;
    private String imgUrl;


    public ImgInfo(String url, String imgUrl) {
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ImgInfo{" +
                "url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
