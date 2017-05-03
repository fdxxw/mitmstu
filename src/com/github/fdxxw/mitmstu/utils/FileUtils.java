package com.github.fdxxw.mitmstu.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.util.Log;

public class FileUtils {
	
	public static void storeData(File path,String JSONData) {
    	try {
    		
    		FileWriter w = new FileWriter(path);
    		w.write(JSONData);
    		w.flush();
    		w.close();
    	} catch(Exception e) {
    		Log.e("err", e.getMessage());
    	} finally {
    		
    	}
    }
	
	public static String readData(File path) {
		StringBuffer sb = new StringBuffer();
		try {
			FileReader fr = new FileReader(path);
			char[] buf = new char[1];
			int ch = 0;
			while((ch = fr.read(buf)) != -1) {
				sb.append(buf);
			}
			fr.close();
		} catch(Exception e) {
			
		}
		
		return sb.toString();
	}
}
