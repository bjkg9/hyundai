package com.Hyundai.Sso.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.Hyundai.Sso.dao.ContentsManageDao;
import com.Hyundai.Sso.dto.ContentsDTO;
import com.Hyundai.Sso.dto.ManagerDTO;
import com.Hyundai.Sso.util.SessionManager;
import com.google.gson.Gson;

@Controller
@RequestMapping
@MapperScan(basePackages="com.Hyundai.Sso.dao")
public class RnDInfoMainController {

	@Autowired
	private ContentsManageDao cntsmngDao;
	
	@RequestMapping(value="/RnDInforMain", method = RequestMethod.GET)
	public String RnDInforMain(Model model, HttpServletResponse response, HttpServletRequest request) {
		
		String url = "user/index";
				
		String ipChk = "";
		
		String l_key = "K";
		
		String SSO_ID = null;
		String gSO_ID = "";
		SSO_ID = request.getHeader("SSO_ID");
        //SSO_ID = "7700";
		
        HttpSession mSession = request.getSession(); 
        
        mSession.setAttribute("SSO_ID",SSO_ID);
		
		gSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		System.out.println("SSO_ID---==============================================================:"+gSO_ID);
				
		try {				
            //client ip
			 ipChk = c_getRemoteAddr(request);
		     		
			 HashMap<String,String> param = new HashMap<String,String>();
			
			    param.put("ip", ipChk);
			    
			    ManagerDTO mf = cntsmngDao.MemberInfo(param);
			    
			    if((mf != null)&&!(mf.ip.equals(""))){
			    	model.addAttribute("info",mf.info);
			    	url = "redirect:/physical_List";
			    }
			    
			    param.put("l_key", l_key);
									
				param.put("category", "C0002");
				List<ContentsDTO> stList = cntsmngDao.ContentUserList(param);
				model.addAttribute("stList",stList);
		
				param.put("category", "C0003");
				List<ContentsDTO> smList = cntsmngDao.ContentUserList(param);
				model.addAttribute("smList",smList);
				
				param.put("category", "C0001");
				List<ContentsDTO> spList = cntsmngDao.ContentUserList(param);
				model.addAttribute("spList",spList);
				
				
			
				System.out.println("ip---==============================================================:"+ipChk);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}

	private void redirect(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@RequestMapping(value="/RnDInforHome", method = RequestMethod.GET)
	public String RnDInforHome(Model model, HttpServletResponse response, HttpServletRequest request) {
		
		String url = "user/index";
				
	    HttpSession mSession = request.getSession(); 
		 
		//String ipChk = "";
		String l_key = "K";
		
		String SSO_ID = null;
		
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		
		System.out.println("SSO_ID---********************************************************************:"+SSO_ID);
		
		try {				
            //client ip
			// ipChk = c_getRemoteAddr(request);
			
			HashMap<String,String> param = new HashMap<String,String>();
			
			  //  param.put("ip", ipChk);
			    
			  //  ManagerDTO mf = cntsmngDao.MemberInfo(param);
			    
			  /*  if((mf != null)&&!(mf.ip.equals(""))){
			    	model.addAttribute("info",mf.info);
			    	url = "redirect:/physical_List";
			    }
              */
			    param.put("l_key", l_key);
			    
				param.put("category", "C0002");
				List<ContentsDTO> stList = cntsmngDao.ContentUserList(param);
				model.addAttribute("stList",stList);
		
				param.put("category", "C0003");
				List<ContentsDTO> smList = cntsmngDao.ContentUserList(param);
				model.addAttribute("smList",smList);
				
				param.put("category", "C0001");
				List<ContentsDTO> spList = cntsmngDao.ContentUserList(param);
				model.addAttribute("spList",spList);
				
				//int no = spList.get(0).NO;
			
				//System.out.println("ip---==============================================================:"+no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}
	@RequestMapping(value="/EN", method = RequestMethod.GET)
	public String RnDInforMain_En(Model model, HttpServletRequest request) {
		
		String url = "user/index-en";
		
		String l_key = "E";
		
		try {				
			
			HashMap<String,String> param = new HashMap<String,String>();		
									
			    param.put("l_key", l_key);
			    
				param.put("category", "C0002");
				List<ContentsDTO> stList = cntsmngDao.ContentUserList(param);
				model.addAttribute("stList",stList);
		
				param.put("category", "C0003");
				List<ContentsDTO> smList = cntsmngDao.ContentUserList(param);
				model.addAttribute("smList",smList);
				
				param.put("category", "C0001");
				List<ContentsDTO> spList = cntsmngDao.ContentUserList(param);
				model.addAttribute("spList",spList);
			

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return url;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public void RnDInforOut(Model model, HttpServletResponse response, HttpServletRequest request) {
			
		Gson json = new Gson();
		
		HashMap<String,String> param = new HashMap<String,String>();
		
		String ipChk = "";
		
		String l_key = "K";
		
		String SSO_ID = "";
		String gSO_ID = "";
		SSO_ID = request.getHeader("SSO_ID");
        
		
        HttpSession mSession = request.getSession(); 
        		
        SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		System.out.println("SSO_ID---==============================================================:"+SSO_ID);
		
		//if(!SSO_ID.equals("")||SSO_ID != null) {
        	mSession.removeAttribute("SSO_ID");
		//mSession.invalidate();
        //}
		String html = "ok"; 
		
		try {
			  param.put("html", html);
			  PrintWriter pw = null;
			  response.setContentType("application/json");
			  response.setContentType("text/xml;charset=utf-8");
			  response.setHeader("Cache-Control", "no-cache");
			  pw = new PrintWriter(response.getWriter());  
			  pw.println(json.toJson(param));
			  pw.flush();
			  pw.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/IP", method = RequestMethod.GET)
	public String RnDInforMain_IP(Model model, HttpServletRequest request) {
		
		String url = "admin/regist_IP";
		
		return url;
	}
	
	public static String c_getRemoteAddr(HttpServletRequest request) {
	
	    String ip = null;
	    request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	    
	    ip = request.getHeader("X-Forwarded-For");
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("Proxy-Client-IP"); 
	
	    } 
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	
	    } 
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("HTTP_CLIENT_IP"); 
	
	    } 
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	
	    }
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("X-Real-IP"); 
	
	    }
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("X-RealIP"); 
	
	    }
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	        ip = request.getHeader("REMOTE_ADDR");
	
	    }
	
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	
	            ip = request.getRemoteAddr(); 
	
	        }
	
			return ip;
	
	}
	
}
