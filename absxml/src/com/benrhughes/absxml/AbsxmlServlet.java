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
import java.net.URLDecoder;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AbsxmlServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/xml");
		
		String pubFileName = URLDecoder.decode(req.getParameter("pubFileName"), "UTF-8");
		int sheet = Integer.parseInt(req.getParameter("sheet"));

		XMLConverter converter = XMLConverter.getInstance();
		
		String URI = constructURI(pubFileName);
		String data ="";
		
		try{
			data = converter.getData(URI, sheet);
		}
		catch (Exception e){
			resp.setContentType("text/plain");
			e.printStackTrace(resp.getWriter());
		}
		/*}
		catch(Exception e){
			String msg = "The decoded URL attempting to be accessed is: " + URI;
			System.err.println(msg);
			e.printStackTrace();	
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("<p>" + msg + "</p>");
			sb.append("\n");
			sb.append("Servlet failed:<BR/>");
			sb.append(e.toString());
			
			data = sb.toString();
		}*/
		
		resp.getWriter().write(data);
	}
	
	private String constructURI(String pubFileName) {
		// Result needs to look like: 
		//	http://www.ausstats.abs.gov.au/ausstats/meisubs.nsf/LatestTimeSeries/<filename>/$FILE/<filename.ext>
		String URI = "http://www.ausstats.abs.gov.au/ausstats/meisubs.nsf/LatestTimeSeries/"
			+ pubFileName + "/$FILE/" + pubFileName + ".xls";
		return URI;
	}
}
