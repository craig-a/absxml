package com.benrhughes.absxml;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

/**
 * XMLConverter
 * @author hughbe
 * 
 * The XMLConverter class is a singleton. It contains one public method (getData)
 * which takes the URI of an Excel spreadsheet and a sheet number and converts the
 * sheet from the spreadsheet into a simple XML doc, which is returned as a string.
 * 
 * The results of the conversion are cached in memory to improve performance.
 * 
 * XMLConverter is thread-safe. A single instance of XMLConverter is maintained
 * across threads, allowing any thread to access the cache.
 */
public class XMLConverter {

	/*
	 * Static members
	 */
	private static XMLConverter _xmlConverter;

	/**
	 * Gets the single instance of XMLConverter. 
	 * Method is synchronized to ensure that multiple threads cannot create multiple instances
	 */
	public static synchronized XMLConverter getInstance(){
		if (_xmlConverter == null){
			_xmlConverter = new XMLConverter();	
		}
		return _xmlConverter;
	}


	/*
	 * Instance members
	 */

	// _ht must be synchronized on write to be thread-safe
	private Hashtable<String, StringBuilder> _ht = new Hashtable<String, StringBuilder>();

	private XMLConverter(){

	}

	public String getData(String uri, int sheet) throws Exception{
		StringBuilder data = new StringBuilder();

		String key = uri + sheet;
		
		data = _ht.get(key);
		if (data == null){
			try{
				URL url = new URL(uri);
				URLConnection uc = url.openConnection();

				Workbook w = Workbook.getWorkbook(uc.getInputStream());

				data = generateXML(w, uri, sheet);

				synchronized(_ht){
					_ht.put(key, data);
				}
			}
			catch (Exception e){
				throw e;
			}
		}
		
		return data.toString();
	}
	
	private StringBuilder generateXML(Workbook workbook, String uri, int sheet) throws IOException
	{
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" ?>");
		sb.append("\n");
		sb.append("\n");
		sb.append("<workbook>");
		sb.append("\n");

		Sheet s = workbook.getSheet(sheet);

		sb.append("  <sheet number=\"" + sheet + "\">");
		sb.append("\n");
		sb.append("    <name><![CDATA["+s.getName()+"]]></name>");
		sb.append("\n");

		Cell[] row = null;

		for (int i = 0 ; i < s.getRows() ; i++)
		{
			sb.append("    <row number=\"" + i + "\">");
			sb.append("\n");
			row = s.getRow(i);

			for (int j = 0 ; j < row.length; j++)
			{
				if (row[j].getType() != CellType.EMPTY)
				{
					sb.append("      <col number=\"" + j + "\">");
					sb.append("<![CDATA["+row[j].getContents()+"]]>");
					sb.append("</col>");
					sb.append("\n");
				}
			}
			sb.append("    </row>");
			sb.append("\n");
		}
		sb.append("  </sheet>");
		sb.append("\n");
		
		sb.append("<dataSource>");
		sb.append("<![CDATA["+uri+"]]>");
		sb.append("</dataSource>");
		sb.append("\n");
		
		sb.append("</workbook>");
		sb.append("\n");
		
		return sb;
	}
}
