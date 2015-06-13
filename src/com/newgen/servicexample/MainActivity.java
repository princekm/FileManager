package com.newgen.servicexample;

import java.io.File;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener,
		OnItemLongClickListener {

	private File[] files;
	private RelativeLayout root;
	private ListView listView;
	private LinearLayout fileChooser;
	private TextView filePathView;
	private CustomAdapter adapter;
	private static String currDir, prevDir, homeDir;
	private static Stack backStack, forwStack;
	private boolean longClicked = false;
	static {
		currDir = "/";
		prevDir = "/";
		homeDir = "/";
		backStack = new Stack();
		forwStack = new Stack();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		populateRootFiles();
	}

	private void initComponents() {
		root = (RelativeLayout) findViewById(R.id.root);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		fileChooser = (LinearLayout) findViewById(R.id.file_chooser);
		filePathView = (TextView) findViewById(R.id.file_path);

	}

	private void setBackground(View view, String path) {
		view.setBackgroundDrawable(Drawable.createFromPath(new File(path)
				.getAbsolutePath()));
	}

	private void populateRootFiles() {
		File dirs = new File(homeDir);
		filePathView.setText(homeDir);
		if (dirs.exists()) {
			files = dirs.listFiles();
			adapter = new CustomAdapter(this, R.layout.row, null, files);
			listView.setAdapter(adapter);
			backStack.clear();
			backStack.push(dirs);
		}
	}

	public void onBackPressed() {
		if (!backStack.peek().toString().equals("/")) {
			forwStack.push(backStack.pop());
			File prevFile = new File(backStack.peek().toString());
			files = prevFile.listFiles();
			filePathView.setText(prevFile.toString());
			adapter = new CustomAdapter(this, R.layout.row, null, files);
			listView.setAdapter(adapter);
		} else
			super.onBackPressed();
	}

	public void showFileChooser(View view) {
		if (fileChooser.getVisibility() == View.GONE)
			fileChooser.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int index, long arg3) {
		if (!longClicked) {
			Intent intent=null;
			File selectedFile = new File(backStack.peek() + "/"
					+ files[index].getName());
			if (selectedFile.isDirectory()) {
				if (selectedFile.listFiles() != null) {
					files = selectedFile.listFiles();
					backStack.push(selectedFile);
					filePathView.setText(selectedFile.toString());
					adapter = new CustomAdapter(this, R.layout.row, null, files);
					listView.setAdapter(adapter);
				} else
					Toast.makeText(this, "Permission Denied",
							Toast.LENGTH_SHORT).show();
			} else {
				try {
					intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(selectedFile),
							getMimeType(selectedFile.getName()));

					startActivity(intent);
				} catch (Exception e) {
//					Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
	//						.show();

				}
			}
		}
		longClicked = false;

	}

	private String getMimeType(String url) {
		if (!url.contains("."))
			return null;
		int index = url.lastIndexOf(".");
		url = url.toLowerCase();
		String type = url.substring(index + 1);//
		MimeTypeMap map = MimeTypeMap.getSingleton();
		type = map.getMimeTypeFromExtension(type);
		return type;
	}

	public void prevDir(View view) {
		if (!backStack.peek().toString().equals("/")) {
			forwStack.push(backStack.pop());
			File prevFile = new File(backStack.peek().toString());
			files = prevFile.listFiles();
			filePathView.setText(prevFile.toString());
			adapter = new CustomAdapter(this, R.layout.row, null, files);
			listView.setAdapter(adapter);
		}
	}

	public void homeDir(View view) {
		currDir = "/";
		prevDir = "/";
		homeDir = "/";
		populateRootFiles();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> list, View view, int index, long arg3) {
		Toast.makeText(this, files[index].toString(), 3).show();
//		longClicked = true;
		return true;
	}
}
