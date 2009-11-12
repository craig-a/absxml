package com.benrhughes.absxml;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SheetFactory {

	public static String getSheet(String uri, int sheetNo) throws IOException, BiffException {
		sheetNo--; // Workbook starts counting sheets at 0, but people usually start at 1
		
		String key = uri + sheetNo;
		
		StringBuilder data = Cache.get(key);
		
		if (data == null){
			// the data it not cached.. lets get it			
			URL url = new URL(uri);
			URLConnection uc = url.openConnection();
			uc.setConnectTimeout(10000); //max allowed by appengine

			Workbook w = Workbook.getWorkbook(uc.getInputStream());

			// convert and cache all the sheets in the workbook
			int n = w.getNumberOfSheets();
			for(int i=0; i<n; i++){
				Cache.add(uri+i, XMLConverter.convert(w, uri, i));
			}

			// the data will now be in the cache, so get it
			data = Cache.get(key);
		}
		
		return data.toString();
	}
	
}
