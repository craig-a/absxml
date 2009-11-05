package com.benrhughes.absxml;

import java.io.IOException;

import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.benrhughes.absxml.XMLConverter;

@SuppressWarnings("serial")
public class E2X extends HttpServlet {

	/*
	 * Note to self:  Servlets are multi-threaded. Any instance variables  
	 * will not be thread safe unless manually synchronised
	 */

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// These properties need to be set to get out through the ABS proxy
		// They will need to be removed and/or modified if the servlet is run on an external server
		// NB, if run on an internal server, the server will need to be set up with access 
		// to the (external) ABS website. Talk to DaDs and/or IT Security
		System.getProperties().put("proxySet", "true" );
		System.getProperties().put("http.proxyHost", "yoda.corp.abs.gov.au");
		System.getProperties().put("http.proxyPort", "8080");
		System.getProperties().put("http.nonProxyHosts", "localhost|*.corp.abs.gov.au|*.dev.abs.gov.au");
		System.setProperty("http.maxRedirects", "50");
		
		response.setContentType("text/html");
		
		//TODO: Input validation!
		String pubFileName = URLDecoder.decode(request.getParameter("pubFileName"), "UTF-8");
		int sheet = Integer.parseInt(request.getParameter("sheet"));
		
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
		
		response.getWriter().write(data);
	
	}

	private String constructURI(String pubFileName) {
		// Result needs to look like: 
		//	http://www.ausstats.abs.gov.au/ausstats/abs@archive.nsf/LatestTimeSeries/<filename>/$FILE/<filename.ext>
		String URI = "http://www.ausstats.abs.gov.au/ausstats/abs@archive.nsf/LatestTimeSeries/"
				+ pubFileName.substring(0, pubFileName.length() -4) //drop the last 4 chars, ie ".xls"
				+ "/$FILE/" + pubFileName;
		return URI;
	}

}
