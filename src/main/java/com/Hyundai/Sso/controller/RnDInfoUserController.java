package com.Hyundai.Sso.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Hyundai.Sso.dao.ContentsManageDao;
import com.Hyundai.Sso.dto.ContentsDTO;
import com.Hyundai.Sso.dto.PageDTO;

import com.google.gson.Gson;

@Controller
@RequestMapping
@MapperScan(basePackages="com.Hyundai.Sso.dao")
public class RnDInfoUserController {

	@Autowired
	private ContentsManageDao cntsmngDao;
	
	@RequestMapping(value="/RnDInforView")
	public String RnDInforViewr(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="l_key", defaultValue="") String l_key,
			@RequestParam(value="bNo", defaultValue="") String bNo,
			HttpServletResponse response,HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
	
		HttpSession mSession = request.getSession(); 
		
		String SSO_ID = null;
		//SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
	
		String url = "";
		
		if(category.equals("C0002")) {
			url = "user/technical_security";
		}else if(category.equals("C0003")) {
			url = "user/management_security";
		}else {
			url = "user/physical_security";
		}
		
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		param.put("l_key", l_key);
		
		try {				
			
					List<ContentsDTO> pList = cntsmngDao.ContentUserList(param);
				//	System.out.println("size---==============================================================:"+pList.size());
				//	System.out.println("bNo---==============================================================:"+bNo);
										
					model.addAttribute("pList",pList);
					
												
						param.put("category", "C0002");
						List<ContentsDTO> stList = cntsmngDao.ContentUserList(param);
						model.addAttribute("stList",stList);
				
						param.put("category", "C0003");
						List<ContentsDTO> smList = cntsmngDao.ContentUserList(param);
						model.addAttribute("smList",smList);
						
						param.put("category", "C0001");
						List<ContentsDTO> spList = cntsmngDao.ContentUserList(param);
						model.addAttribute("spList",spList);
		
						if(bNo.equals("")||bNo.equals("undefined")) {
							
							if(category.equals("C0001")) {
								bNo = String.valueOf(spList.get(0).NO);
					//			System.out.println("spList size---==============================================================:"+spList.size());
					//			System.out.println("spList.get(1).NO---==============================================================:"+spList.get(0).NO);
							}else if(category.equals("C0002")) {
								bNo = String.valueOf(stList.get(0).NO);
							}else if(category.equals("C0003")) {
								bNo = String.valueOf(smList.get(0).NO);	
							}
						}
						

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("category",category);
		model.addAttribute("bNo",bNo);
		
		System.out.println("SSO_ID:###############################################:"+SSO_ID);
		
		if(SSO_ID.equals("null")||SSO_ID == null) {
			
			url = "user/error";
			System.out.println("SSO_ID -> url :****---****************************:"+url);
		}
		
		return url;
	}
	
	@RequestMapping(value="/RnDInforViewList")
	public String RnDInforViewList(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,HttpServletResponse response,
			HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		HttpSession mSession = request.getSession(); 
		
		String SSO_ID = null;
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		
		String url = "";
		
		System.out.println("category---==============================================================:"+category);
		
		if(category.equals("C0002")) {
			url = "user/technical_security";
		}else if(category.equals("C0003")) {
			url = "user/management_security";
		}else {
			url = "user/physical_security";
		}
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		
		try {				
					List<ContentsDTO> pList = cntsmngDao.ContentUserList(param);
					System.out.println("size---==============================================================:"+pList.size());
					
					model.addAttribute("phList",pList);
											
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		model.addAttribute("category",category);
		
		if(SSO_ID.equals("null")||SSO_ID == null) {
			url = "user/error";
		}
		
		return url;
		
	}
	
	@RequestMapping(value="/ajax_RnDInforSiteMap")
	public void ajax_RnDInforSiteMap(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			HttpServletResponse response, HttpServletRequest request) {
		
		Gson json = new Gson();
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);

		HttpSession mSession = request.getSession(); 
		
		String SSO_ID = null;
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		
		try {				
					List<ContentsDTO> pList = cntsmngDao.ContentUserList(param);
					
					String txt = ""; 
					
					if(pList.size()>0) {
					
						for(int i=0;i<pList.size();i++) {
							txt += "<li><a href=''>"+pList.get(i).TITLE +"</a></li></br>";
						}
					}				
					
					param.put("txt", txt);
					
					  PrintWriter pw = null;
					  response.setContentType("application/json");
					  response.setContentType("text/xml;charset=utf-8");
					  response.setHeader("Cache-Control", "no-cache");
					  pw = new PrintWriter(response.getWriter());  
					  pw.println(json.toJson(param));
					  pw.flush();
					  pw.close();
					  
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		model.addAttribute("category",category);

	}
	
	@RequestMapping(value="/RnDInforViewDetail")
	public void RnDInforDetailList(Model model, @RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="bNo", defaultValue="") String bNo,HttpServletResponse response,
			HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		HttpSession mSession = request.getSession(); 
	
		String SSO_ID = null;
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		
		String url = "";
		
		if(category.equals("C0002")) {
			url = "user/technical_security";
		}else if(category.equals("C0003")) {
			url = "user/management_security";
		}else {
			url = "user/physical_security";
		}
		
        Gson json = new Gson();
				
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("category", category);
		param.put("no", bNo);
		
		try {				
					ContentsDTO cObj = cntsmngDao.ContentView(param);
					
					String html = ""; 
					
					if(cObj != null)  {
						
							html = cObj.CONTENTS;
							cntsmngDao.ReadCountUpdate(param);
					}				
					
        			  param.put("html", html);
					  PrintWriter pw = null;
					  response.setContentType("application/json");
					  response.setContentType("text/xml;charset=utf-8");
					  response.setHeader("Cache-Control", "no-cache");
					  pw = new PrintWriter(response.getWriter());  
					  pw.println(json.toJson(param));
					  pw.flush();
					  pw.close();
					  
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	@RequestMapping(value="/RnDInforSiteMap", method= RequestMethod.POST)
	public String RnDInforSiteMap(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="l_key", defaultValue="") String l_key,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		HttpSession mSession = request.getSession(); 
		String SSO_ID = null;
		String _paddr = "";
		
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
				
		String url = "user/Sitemap";
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		param.put("l_key", l_key);
		
		try {			
			        param.put("category", "C0001");
			//물리 목록	        
					List<ContentsDTO> pList = cntsmngDao.ContentUserList(param);
					model.addAttribute("pList",pList);
					
					param.put("category", "C0002");
					//기술 목록	        
					List<ContentsDTO> tList = cntsmngDao.ContentUserList(param);
					model.addAttribute("tList",tList);
					
					param.put("category", "C0003");
					//관리 목록		        
					List<ContentsDTO> mList = cntsmngDao.ContentUserList(param);
					model.addAttribute("mList",mList);
											
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		model.addAttribute("category",category);
		
		if(category.equals("C0001")) {
			model.addAttribute("paddr","sitemap1");
			
		}else if(category.equals("C0002")) {
			model.addAttribute("paddr","sitemap2");

		}else if(category.equals("C0003")) {
			model.addAttribute("paddr","sitemap3");
			
		}
		
		if(SSO_ID.equals("null")||SSO_ID == null) {
			url = "user/error";
		}
		
		return url;
		
	}

	@RequestMapping(value="/RnDInforSiteMap_en", method= RequestMethod.POST)
	public String RnDInforSiteMap_en(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="l_key", defaultValue="") String l_key,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		HttpSession mSession = request.getSession(); 
		
		String SSO_ID = null;
		String _paddr = "";
		
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));

		String url = "user/Sitemap-en";


		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		param.put("l_key", l_key);
		
		try {			
			        param.put("category", "C0001");
			//물리 목록	        
					List<ContentsDTO> pList = cntsmngDao.ContentUserList(param);
				
					model.addAttribute("pList",pList);
					
					param.put("category", "C0002");
					//기술 목록	        
					List<ContentsDTO> tList = cntsmngDao.ContentUserList(param);
				
					model.addAttribute("tList",tList);				
					param.put("category", "C0003");
					//관리 목록		        
					List<ContentsDTO> mList = cntsmngDao.ContentUserList(param);
					model.addAttribute("mList",mList);
											
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		model.addAttribute("category",category);
		
		if(category.equals("C0001")) {
			model.addAttribute("paddr","sitemap1");
			
		}else if(category.equals("C0002")) {
			model.addAttribute("paddr","sitemap2");

		}else if(category.equals("C0003")) {
			model.addAttribute("paddr","sitemap3");
			
		}
		
		if(SSO_ID.equals("null")||SSO_ID == null) {
			url = "user/error";
		}
		
		return url;
		
	}
	
	@RequestMapping(value="/RnDInforSearch", method= RequestMethod.POST)
	public String RnDInforSearch(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="l_key", defaultValue="") String l_key,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		HttpSession mSession = request.getSession(); 

		String SSO_ID = null;
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));
		
		String url = "user/search";
		
		model.addAttribute("search_key",search_key);
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("l_key", l_key);
		
		try {			
			        
					List<ContentsDTO> pList = cntsmngDao.ContentSearchList(param);
					System.out.println("size---==============================================================:"+pList.size());
					String tmp = String.valueOf(pList.size());
					System.out.println("size---//////////////////////////////////////////////////////////////:"+pList.size());
					
					model.addAttribute("size", tmp);
					
					model.addAttribute("pList",pList);
									
					param.put("search_key", "");
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

		if(SSO_ID.equals("null")||SSO_ID == null) {
			url = "user/error";
		}
		
		return url;
		
	}

	
/**
 * EN SITE 시작
 * 
 */

	
	@RequestMapping(value="/RnDInforView_en", method= RequestMethod.POST)
	public String RnDInforViewr_en(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="l_key", defaultValue="") String l_key,
			@RequestParam(value="bNo", defaultValue="") String bNo,
			HttpServletResponse response,HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 

		HttpSession mSession = request.getSession(); 
		
		String SSO_ID = null;
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));

		String url = "";
		
		if(category.equals("C0002")) {
			url = "user/technical_security-en";
		}else if(category.equals("C0003")) {
			url = "user/management_security-en";
		}else {
			url = "user/physical_security-en";
		}
		
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		param.put("l_key", l_key);
		
		try {				
			
					List<ContentsDTO> pList = cntsmngDao.ContentUserList(param);
				//	System.out.println("size---==============================================================:"+pList.size());
				//	System.out.println("bNo---==============================================================:"+bNo);
								
                    String tmp = String.valueOf(pList.size());
					
					model.addAttribute("size", tmp);
					model.addAttribute("pList",pList);
					
												
						param.put("category", "C0002");
						List<ContentsDTO> stList = cntsmngDao.ContentUserList(param);
						model.addAttribute("stList",stList);
				
						param.put("category", "C0003");
						List<ContentsDTO> smList = cntsmngDao.ContentUserList(param);
						model.addAttribute("smList",smList);
						
						param.put("category", "C0001");
						List<ContentsDTO> spList = cntsmngDao.ContentUserList(param);
						model.addAttribute("spList",spList);
		
						if(bNo.equals("")||bNo.equals("undefined")) {
							
							if(category.equals("C0001")) {
								bNo = String.valueOf(spList.get(0).NO);
					//			System.out.println("spList size---==============================================================:"+spList.size());
					//			System.out.println("spList.get(1).NO---==============================================================:"+spList.get(0).NO);
							}else if(category.equals("C0002")) {
								bNo = String.valueOf(stList.get(0).NO);
							}else if(category.equals("C0003")) {
								bNo = String.valueOf(smList.get(0).NO);	
							}
						}
						

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("category",category);
		model.addAttribute("bNo",bNo);
		
		System.out.println("SSO_ID:###############################################:"+SSO_ID);
		
		if(SSO_ID.equals("null")||SSO_ID == null) {
			
			url = "user/error";
			System.out.println("SSO_ID -> url :****---****************************:"+url);
		}
		
		return url;
	}
	
	@RequestMapping(value="/RnDInforSearch_en", method= RequestMethod.POST)
	public String RnDInforSearch_en(Model model, @RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="l_key", defaultValue="") String l_key,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		HttpSession mSession = request.getSession(); 
		
		String SSO_ID = null;
		SSO_ID = request.getHeader("SSO_ID");
		SSO_ID = String.valueOf(mSession.getAttribute("SSO_ID"));

		String url = "user/search-en";
		
		model.addAttribute("search_key",search_key);
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("l_key", l_key);
		
		try {			
			        
					List<ContentsDTO> pList = cntsmngDao.ContentSearchList(param);
					System.out.println("size---==============================================================:"+pList.size());
					String tmp = String.valueOf(pList.size());
					System.out.println("size---//////////////////////////////////////////////////////////////:"+pList.size());
					
					model.addAttribute("size", tmp);
					
					model.addAttribute("pList",pList);
					
					param.put("search_key", "");
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

		if(SSO_ID.equals("null")||SSO_ID == null) {
			url = "user/error";
		}
		
		return url;
		
	}
}
