package com.example.test.mytestdemo.xmlPull.XmlParserDemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.xmlPull.XMLHelper.DomParserHelper;
import com.example.test.mytestdemo.xmlPull.entity.channel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomPraserDemo extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.xml_list, new String[] { "id", "name" }, new int[] {
						R.id.textId, R.id.textName });
		setListAdapter(adapter);
	}

	private List<Map<String, String>> getData() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		InputStream stream = getResources().openRawResource(R.raw.channels);
		List<channel> channlist = DomParserHelper.getChannelList(stream);

		for (int i = 0; i < channlist.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			channel chann = (channel) channlist.get(i);
			map.put("id", chann.getId());
			map.put("url", chann.getUrl());
			map.put("name", chann.getName());
			list.add(map);
		}

		return list;
	}

}
