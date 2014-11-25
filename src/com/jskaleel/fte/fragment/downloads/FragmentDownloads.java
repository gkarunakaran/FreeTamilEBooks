package com.jskaleel.fte.fragment.downloads;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;

public class FragmentDownloads extends BasicFragment {
	
	private static final String TAG="FragmentDownloads";
	
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;
	private ListView downloadsList;

	public FragmentDownloads() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_downloads, null);
		myPath = (TextView) view.findViewById(R.id.path);
		downloadsList	=	(ListView) view.findViewById(R.id.downloadList);
		
		root = Environment.getExternalStorageDirectory().getPath()+"/Free_Tamil_Ebooks/";
		
		getDir(root);

		return view;
	}

	private void getDir(String dirPath) {

		myPath.setText("Location: " + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();

		if(!dirPath.equals(root))
		{
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());	
		}

		for(int i=0; i < files.length; i++)
		{
			File file = files[i];

			if(!file.isHidden() && file.canRead()){
				path.add(file.getPath());
				if(file.isDirectory()){
					item.add(file.getName() + "/");
				}else{
					item.add(file.getName());
				}
			}	
		}
		
		DownloadsAdapter adapter = new DownloadsAdapter(getActivity(), item, FragmentDownloads.this);
		downloadsList.setAdapter(adapter);	
	}
	
/*	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(path.get(position));

		if (file.isDirectory())
		{
			if(file.canRead()){
				getDir(path.get(position));
			}else{
				new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setTitle("[" + file.getName() + "] folder can't be read!")
				.setPositiveButton("OK", null).show();	
			}	
		}else {
			String extension = (file.toString()).substring(((file.toString()).lastIndexOf(".") + 1), (file.toString()).length());

			if (extension.equals("epub")) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(file), "application/epub");
				ComponentName cn = new ComponentName("org.geometerplus.zlibrary.ui.android", "org.geometerplus.android.fbreader.FBReader");
				intent.setComponent(cn);
				startActivity(intent);
			}
		}
	}*/
}
