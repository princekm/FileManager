package com.newgen.servicexample;


import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends ArrayAdapter  {
	LayoutInflater inflater;
	File files[];
	int resIds[];
	public CustomAdapter(Context context, int resource,int []resIds, File files[]) {
		super(context, resource, files);
		this.files=files;
		this.resIds=resIds;
		inflater=(LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		    View rowView = inflater.inflate(R.layout.row, parent, false);
		    TextView textView = (TextView) rowView.findViewById(R.id.text);
		    View imageView = (View) rowView.findViewById(R.id.image);
		    FM.TYPE type;
		    if(files[position].isDirectory())
		    	type=FM.TYPE.DIR;
		    else
		    	type=FM.getType(files[position].toString());
		    switch(type){
		    	case IMAGE:
		    	        Bitmap bitmap = BitmapFactory.decodeFile(files[position].toString());
		    	        BitmapDrawable bd = new BitmapDrawable(bitmap);
		    	        imageView.setBackgroundDrawable(bd);break;
		    	case TXT:imageView.setBackgroundResource(R.drawable.txt);break;
		    	case PDF:imageView.setBackgroundResource(R.drawable.pdf);break;
		    	case AUDIO:imageView.setBackgroundResource(R.drawable.audio);break;
		    	case VIDEO:	imageView.setBackgroundResource(R.drawable.pdf);break;
		    	case APK:
		    		String APKFilePath = files[position].toString(); //For example...
		    	     PackageManager pm = parent.getContext().getPackageManager();
		    	     PackageInfo    pi = pm.getPackageArchiveInfo(APKFilePath, 0);
		    	     pi.applicationInfo.sourceDir       = APKFilePath;
		    	     pi.applicationInfo.publicSourceDir = APKFilePath;
		    	   Drawable APKicon = pi.applicationInfo.loadIcon(pm);
		    	   imageView.setBackgroundDrawable(APKicon);break;
		    	case DIR:imageView.setBackgroundResource(R.drawable.folder);break;
		    	default:imageView.setBackgroundResource(R.drawable.unknown);
		    }
		    
		    textView.setText(""+files[position].getName());
		    return rowView;	
		    
	}	
}
