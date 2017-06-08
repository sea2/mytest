package com.example.test.mytestdemo.xmlPull.XMLHelper;

import com.example.test.mytestdemo.xmlPull.entity.channel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class DomParserHelper {

	public static List<channel> getChannelList(InputStream stream)
	{
		List<channel> list=new ArrayList<channel>();

		//得到 DocumentBuilderFactory 对象, 由该对象可以得到 DocumentBuilder 对象
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();

		try {
			//得到DocumentBuilder对象
			DocumentBuilder builder=factory.newDocumentBuilder();
			//得到代表整个xml的Document对象
			Document document=builder.parse(stream);
			//得到 "根节点"
			Element root=document.getDocumentElement();
			//获取根节点的所有items的节点
			NodeList items=root.getElementsByTagName("item");
			//遍历所有节点
			for(int i=0;i<items.getLength();i++)
			{
				channel chann=new channel();
				Element item=(Element)items.item(i);
				chann.setId(item.getAttribute("id"));
				chann.setUrl(item.getAttribute("url"));
				chann.setName(item.getFirstChild().getNodeValue());
				list.add(chann);
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
