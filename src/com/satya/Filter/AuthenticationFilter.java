package com.satya.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.satya.IConstants;
import com.satya.BusinessObjects.User;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

	private List<String> publicURLs = null;
	private List<String> publicExtensions = null;
    public AuthenticationFilter() {
    	publicURLs = new ArrayList<String>();
    	publicURLs.add("index.jsp");
    	publicURLs.add("header.jsp");
    	publicURLs.add("includeJars.jsp");
    	publicURLs.add("includeJS.jsp");
    	publicURLs.add("User");
    	publicURLs.add("signup.jsp");
    	publicURLs.add("login.jsp");
    	
    	publicExtensions = new ArrayList<String>();
    	publicExtensions.add("html");
    	publicExtensions.add("css");
    	publicExtensions.add("js");
    	publicExtensions.add("swf");
    	publicExtensions.add("txt");
    	publicExtensions.add("jpg");
    	publicExtensions.add("gif");
    	publicExtensions.add("png");
    	publicExtensions.add("woff");
    	publicExtensions.add("ttf");
    	publicExtensions.add("svg");
    }
    Logger log = Logger.getLogger(AuthenticationFilter.class);
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String pageUrl = req.getRequestURI();
		List<String> errorMsgs = new ArrayList<String>();
		String pageExtension = "";
		try{
			if(pageUrl!=null){
				pageUrl = pageUrl.substring(pageUrl.lastIndexOf("/")+1, pageUrl.length());
				pageExtension = pageUrl.substring(pageUrl.lastIndexOf(".")+1);
			}
			if(pageUrl!=null && !publicExtensions.contains(pageExtension)){
				pageUrl = pageUrl.substring(pageUrl.lastIndexOf("/")+1, pageUrl.length());
				if(!pageUrl.equals("") && !publicURLs.contains(pageUrl)){
					//if its not a public URL, then check if user logged in or not
					User user = (User) req.getSession().getAttribute(IConstants.loggedInUser);
					if(user != null){
						chain.doFilter(request, response);
					}else{
						setSourceURL(req);
						errorMsgs.add(IConstants.err_pleaseLogin);
						request.setAttribute(IConstants.errMessages, errorMsgs);
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				}else{
					chain.doFilter(request, response);
				}
			}else{
				chain.doFilter(request, response);
			}
		}catch(Exception e){
			log.error("Exception ouccered" ,e);
			chain.doFilter(request, response);
		}
	}
	private void setSourceURL(HttpServletRequest req){
		String sourceURL = req.getRequestURL().toString();
		 if(req.getQueryString()!= null){
			 sourceURL = req.getRequestURL().append("?").append( 
					 req.getQueryString()).toString();	 
		 }
		 
		 try{
			 int lastIndexOfSlash = sourceURL.lastIndexOf("/");
			 sourceURL = sourceURL.substring(lastIndexOfSlash);
			 HttpSession session = req.getSession();
			 session.setAttribute(IConstants.SOURCE_URL, sourceURL);
		 }
		 catch(Exception e){
			 
		 }
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
