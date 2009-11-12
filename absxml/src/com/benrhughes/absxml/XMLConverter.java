/*Copyright (c) 2009 Ben Hughes

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package com.benrhughes.absxml;

import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

/**
 * XMLConverter
 * @author Ben Hughes
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
	
	public static StringBuilder convert(Workbook workbook, String uri, int sheet) throws IOException
	{
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" ?>");
		sb.append("\n");
		sb.append("\n");
		sb.append("<workbook>");
		sb.append("\n");

		Sheet s = workbook.getSheet(sheet);

		// add 1 to the sheet number so that it aligns with the passed  parameter
		sb.append("  <sheet number=\"" + sheet+1 + "\">");
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
