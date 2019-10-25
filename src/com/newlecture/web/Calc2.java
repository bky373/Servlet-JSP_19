package com.newlecture.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; chareset = UTF-8");
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
			
		int v = 0;
		
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		//계산
		if(op.equals("=")) {
			
//			int x = (Integer)application.getAttribute("value");
//			int x = (Integer)session.getAttribute("value");
			int x = 0;
			for(Cookie c : cookies)
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			
			int y = v;
//			String operator = (String)application.getAttribute("op");
//			String operator = (String)session.getAttribute("op");
			
			String operator = "";
			for(Cookie c : cookies) 
				if(c.getName().equals("op")) { 
					operator = c.getValue();
					break;
				}
					
			int result = 0;
	 		
			if(operator.equals("+")) result = x + y;
			else result = x - y;

			response.getWriter().printf("result is %d\n", result);
		}
		//값을 저장
		else {
//			application.setAttribute("value", v);
//			application.setAttribute("op", op);

//			session.setAttribute("value", v);
//			session.setAttribute("op", op);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/calc2");
			valueCookie.setMaxAge(24*60*60);//쿠키유효기간(브라우저 꺼도 만료기간 내 쿠키는 살아있음)
			opCookie.setPath("/calc2");//MaxAge 설정하지 않으면 브라우저 끄면 쿠키 삭제됨
			response.addCookie(valueCookie);
			response.addCookie(opCookie);
			
			response.sendRedirect("calc2.html");
		}
	}

}
