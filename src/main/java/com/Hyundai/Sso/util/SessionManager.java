package com.Hyundai.Sso.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

	public void setMemberSession(HttpServletRequest request, String setter){
		
		HttpSession session = request.getSession();
		
		session.setAttribute("SSO_ID", setter);
		
	}
	
	public String getMemberSession(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		String sSO_ID = "";
		
		sSO_ID = (String) session.getAttribute("SSO_ID");

        return sSO_ID;		
	}
}
