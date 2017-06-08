package com.example.test.mytestdemo.xmlPull.XmlParserDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.test.mytestdemo.R;

public class XMLParserActivity extends Activity {

	private Button btnSax;
	private Button btnPull;
	private Button btnDom;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_xml);

		btnSax = (Button) findViewById(R.id.btnSAX);
		btnPull = (Button) findViewById(R.id.btnPull);
		btnDom = (Button) findViewById(R.id.btnDom);

		btnSax.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(XMLParserActivity.this, SAXPraserDemo.class);
				startActivity(intent);
			}
		});

		btnPull.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(XMLParserActivity.this, PullPraserDemo.class);
				startActivity(intent);
			}
		});

		btnDom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(XMLParserActivity.this, DomPraserDemo.class);
				startActivity(intent);
			}
		});

	}
}