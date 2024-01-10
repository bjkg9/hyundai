package com.Hyundai.Sso.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Hyundai.Sso.dao.ContentsManageDao;
import com.Hyundai.Sso.dto.ContentsDTO;
import com.Hyundai.Sso.dto.PageDTO;

@Controller
@RequestMapping
@MapperScan(basePackages="com.Hyundai.Sso.dao")
public class RnDBoard_ENController {

	@Autowired
	private ContentsManageDao cntsmngDao;
	
	@RequestMapping(value="/physicalEN_List")
	public String RnDInforPhyysicalEN_List(Model model, HttpServletRequest request,
			@RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="pageNo", defaultValue="1") int pageNo,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		System.out.println("category---==============================================================:"+category);
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/list-en";
		
		int totalCount = 0;
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		
		try {
			
			totalCount = cntsmngDao.ContentListToTalCnt_EN(param);
			
			PageDTO paging = new PageDTO();
			paging.setPageNo(pageNo);
	        paging.setPageSize(10);
	        paging.setTotalCount(totalCount);

	        int start = ((pageNo - 1) * paging.getPageSize()) + 1; // row
			int end = start + paging.getPageSize() - 1;

			
			param.put("startPageNo",String.valueOf(start));
			param.put("endPageNo",String.valueOf(end));
					
			List<ContentsDTO> pList = cntsmngDao.ContentPhysicalList_EN(param);
			System.out.println("size---==============================================================:"+pList.size());
			
			model.addAttribute("paging",paging);
			model.addAttribute("phList",pList);
			model.addAttribute("category",category);
			model.addAttribute("count",totalCount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return url;
	}
	
	@RequestMapping(value="/ENwriting", method = RequestMethod.POST)
	public String RnDInforRnDInforRegistration(Model model
			, @RequestParam(value="category", defaultValue="C0001") String category
			,HttpServletResponse response
			,HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/registration-en";
		
		System.out.println("*********************here ****************************************");
		
		model.addAttribute("category",category);
		
		return url;
	}
	
	@RequestMapping(value="/ENwrite_process", method = RequestMethod.POST)
	public String RnDInforRnDInforENWriteProcess(Model model
			, @RequestParam(value="title", defaultValue="") String title
			, @RequestParam(value="contents", defaultValue="") String contents
			, @RequestParam(value="category", defaultValue="C0001") String category
			, HttpServletRequest request
			, HttpServletResponse response
			, RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		redirectAttr.addAttribute("category", category);
		
		String SSO_ID = request.getHeader("SSO_ID") ;
		String writer = "";
		
		System.out.println("*********************title :"+title);
		System.out.println("*********************contents :"+contents);
		System.out.println("*********************category :"+category);
		System.out.println("*********************SSO_ID :"+SSO_ID);
		
		
		
		ContentsDTO idt = new ContentsDTO();
		
		idt.TITLE = title;
		idt.CONTENTS = contents;
		idt.CATEGORY = category;
		idt.WRITER = SSO_ID;
		
		
		try {
			/*단순 문구 DB저장 */
			int  inschk = cntsmngDao.ContentInsert_EN(idt);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/physicalEN_List";
	}
	
	
	@RequestMapping(value="/ENupdate_process", method = RequestMethod.POST)
	public String RnDInforRnDInforENUpdateProcess(Model model
			, @RequestParam(value="title", defaultValue="") String title
			, @RequestParam(value="bNo", defaultValue="") int bNo
			, @RequestParam(value="contents", defaultValue="") String contents
			, @RequestParam(value="category", defaultValue="C0001") String category
			, HttpServletRequest request
			, HttpServletResponse response
			, RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
        String savePath = request.getServletContext().getRealPath("ckeditor\\upload")+ "\\";
		
		System.out.println("*********************savePath :"+savePath);
		
		redirectAttr.addAttribute("category", category);
		
		String SSO_ID = request.getHeader("SSO_ID") ;
		String writer = "";
		
		System.out.println("*********************title :"+title);
		System.out.println("*********************contents :"+contents);
		System.out.println("*********************category :"+category);
		System.out.println("*********************SSO_ID :"+SSO_ID);
		
        ContentsDTO idt = new ContentsDTO();
		
		idt.TITLE = title;
		idt.CONTENTS = contents;
		idt.CATEGORY = category;
		idt.WRITER = SSO_ID;
		idt.NO = bNo;
		
		try {
			/*단순 문구 DB저장 */
			int  inschk = cntsmngDao.ContentUpdate_EN(idt);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 
		return "redirect:/physicalEN_List";
	}
	
	@RequestMapping(value="/ENdelete_List")
	public String RnDInforDelete_List(Model model, HttpServletRequest request,
			@RequestParam(value="boardNo", required=true) List<String> bNoList,
			@RequestParam(value="category", defaultValue="C0001") String category,
			HttpServletResponse response,
			RedirectAttributes redirectAttr) {
		System.out.println("category---==============================================================:"+category);
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache");
		
		String url = "redirect:/physicalEN_List";

		try {
			
			System.out.println("bNoList.size()---==============================================================:"+bNoList.size());
			for(int i=0;i<bNoList.size();i++) {
				System.out.println("no:"+bNoList.get(i));
			}
			cntsmngDao.ContentDelete_EN(bNoList);
			
			model.addAttribute("category",category);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return url;
	}
	
	@RequestMapping(value="/ENcontent_View")
	public String RnDInforContent_View(Model model, HttpServletRequest request,
			@RequestParam(value="bNo", defaultValue="") String bNo,
			@RequestParam(value="category", defaultValue="C0001") String category,
			HttpServletResponse response,
			RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache");
		
		String url = "admin/detailView-en";
		
		redirectAttr.addAttribute("category", category);
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("no", bNo);
		
		try {
			
			
			ContentsDTO vObj = cntsmngDao.ContentView_EN(param);
			
			model.addAttribute("ContentView",vObj);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
			
		return url;
	}
}
