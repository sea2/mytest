package com.example.test.mytestdemo.xmlPull;

import java.io.InputStream;
import java.util.List;

/**
 * Created by lhy on 2017/6/5.
 */

public interface IBeautyParser {

    /**
     *
     * 解析输入流，获取Beauty列表
     * @param is
     * @return
     * @throws Exception
     */
    public List<Beauty> parse(InputStream is) throws Exception;

    /**
     *
     * 序列化Beauty对象集合，得到XML形式的字符串
     * @param beauties
     * @return
     * @throws Exception
     */
    public String serialize(List<Beauty> beauties) throws Exception;
}
