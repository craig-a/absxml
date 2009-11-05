package com.benrhughes.absxml;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.*;

//TODO: cache clearing based on embargo time
//TODO: current MEI values (different servlet??)
//TODO: CSS for intro page
//TODO: convert to RESTful service
//TODO: persist cache?

@SuppressWarnings("serial")
public class AbsxmlServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/xml");
		//resp.getWriter().println("Hello, world");
		
		String pubFileName = URLDecoder.decode(req.getParameter("pubFileName"), "UTF-8");
		int sheet = Integer.parseInt(req.getParameter("sheet"));

		XMLConverter converter = XMLConverter.getInstance();
		
		String URI = constructURI(pubFileName);
		String data;
		
		try{
			data = converter.getData(URI, sheet);
		}
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
		}
		
		resp.getWriter().write(data);
//		StringBuilder sb = new StringBuilder();
//		sb.append("<p>The target URI is <a href=\"");
//		sb.append(URI);
//		sb.append("\">");
//		sb.append(URI);
//		sb.append("</a></p>");
//		
//		resp.getWriter().write(sb.toString());

	}
	
	private String constructURI(String pubFileName) {
		// Result needs to look like: 
		//	http://www.ausstats.abs.gov.au/ausstats/meisubs.nsf/LatestTimeSeries/<filename>/$FILE/<filename.ext>
		String URI = "http://www.ausstats.abs.gov.au/ausstats/meisubs.nsf/LatestTimeSeries/"
			+ pubFileName + "/$FILE/" + pubFileName + ".xls";
		return URI;
	}
}
