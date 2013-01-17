package _02_login.filter;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import _01_register.model.*;

public class LoginFilter implements Filter {
	Collection<String> url = new ArrayList<String>();
	String servletPath;
	String contextPath;
	String requestURI;
	@SuppressWarnings("unchecked")
	public void init(FilterConfig fConfig) throws ServletException {
		Enumeration<String> e = fConfig.getInitParameterNames();
		while (e.hasMoreElements()) {
			String path = e.nextElement();
			url.add(fConfig.getInitParameter(path));
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		boolean isRequestedSessionIdValid = false;
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			servletPath = req.getServletPath();  
			contextPath = req.getContextPath();
			requestURI  = req.getRequestURI();
			isRequestedSessionIdValid = req.isRequestedSessionIdValid();			
//			System.out.println("-----------------------------------");
//			System.out.println("000---> isRequestedSessionIdValid:" + isRequestedSessionIdValid);
//			System.out.println("000---> requestURI:" + requestURI);
//			System.out.println("000---> requestURL:" + req.getRequestURL());
//			System.out.println("000---> getQueryString:" + req.getQueryString());
			if (mustLogin()) {
				if (checkLogin(req)) {
//					System.out.println("222--->需要登入，已經登入,requestURI:" + requestURI);
//					System.out.println("222--->需要登入，已經登入,requestURL:" + req.getRequestURL());
//					System.out.println("222--->需要登入，已經登入,getQueryString:" + req.getQueryString());
					chain.doFilter(request, response);
				} else {
					HttpSession session = req.getSession();
					session.setAttribute("requestURI", requestURI);
					if ( ! isRequestedSessionIdValid ) {
						session.setAttribute("timeOut", "使用逾時，請重新登入");
					}
//					System.out.println("333--->需要登入，尚未登入:" + requestURI);
					resp.sendRedirect(contextPath + "/_02_login/login.jsp");
					return;
				}
			} else {
//				System.out.println("444--->不需要登入:" + requestURI);
				chain.doFilter(request, response);
			}
		} else {
			throw new ServletException("Request / Response 型態錯誤");
		}
	}
	private boolean checkLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		MemberBean loginToken = (MemberBean) session.getAttribute("LoginOK");
		if (loginToken == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean mustLogin() {
		boolean login = false;
		for (String sURL : url) {
			if (sURL.endsWith("*")) {
				sURL = sURL.substring(0, sURL.length() - 1);
				if (servletPath.startsWith(sURL)) {
					login = true;
					break;
				}
			} else {
				if (servletPath.equals(sURL)) {
					login = true;
					break;
				}
			}
		}
		return login;
	}
	@Override
	public void destroy() {
	}
}