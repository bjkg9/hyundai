package com.Hyundai.Sso.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import com.Hyundai.Sso.dto.ManagerDTO;
import com.Hyundai.Sso.dto.PageDTO;

@Controller
@RequestMapping
@MapperScan(basePackages="com.Hyundai.Sso.dao")
public class RnDBoardController {

	@Autowired
	private ContentsManageDao cntsmngDao;
	
	@RequestMapping(value="/physical", method = RequestMethod.GET)
	public String RnDInforMain_En(Model model, HttpServletResponse response,HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "user/physical_security";
		
		return url;
	}
	
	@RequestMapping(value="/physical_List")
	public String RnDInforPhyysical_List(Model model, HttpServletRequest request,
			@RequestParam(value="search_key", defaultValue="") String search_key,
			@RequestParam(value="category", defaultValue="C0001") String category,
			@RequestParam(value="pageNo", defaultValue="1") int pageNo,
			HttpServletResponse response,
			RedirectAttributes redirectAttr) {
		System.out.println("category---==============================================================:"+category);
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/list";
		
		int totalCount = 0;		
			
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("search_key", search_key);
		param.put("category", category);
		
		try {
			
			totalCount = cntsmngDao.ContentListToTalCnt(param);
			
			PageDTO paging = new PageDTO();
			paging.setPageNo(pageNo);
	        paging.setPageSize(10);
	        paging.setTotalCount(totalCount);

	        int start = ((pageNo - 1) * paging.getPageSize()) + 1; // row
			int end = start + paging.getPageSize() - 1;

			
			param.put("startPageNo",String.valueOf(start));
			param.put("endPageNo",String.valueOf(end));
					
			List<ContentsDTO> pList = cntsmngDao.ContentPhysicalList(param);
			System.out.println("size---==============================================================:"+pList.size());
			
			model.addAttribute("paging",paging);
			model.addAttribute("phList",pList);
			model.addAttribute("category",category);
			model.addAttribute("count",totalCount);
			model.addAttribute("startPageNo",paging.getStartPageNo());
			model.addAttribute("endPageNo",paging.getEndPageNo());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return url;
	}

	
	@RequestMapping(value="/physical_Regist", method = RequestMethod.GET)
	public String RnDInforPhyysical_Regist(Model model, HttpServletResponse response, HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/registration";
				
		String savePath = request.getServletContext().getRealPath("uploadFiles")+ "\\";
		
		System.out.println("*********************savePath :"+savePath);
		
		return url;
	}
	
	
	@RequestMapping(value="/technical", method = RequestMethod.GET)
	public String RnDInforRnDInfortechnical(Model model, HttpServletResponse response, HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "user/technical_security";
		
		return url;
	}
	
	@RequestMapping(value="/management", method = RequestMethod.GET)
	public String RnDInforRnDInforManagement(Model model, HttpServletResponse response, HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache");
		
		String url = "user/management_security";
		
		return url;
	}
	
	@RequestMapping(value="/writing", method = RequestMethod.POST)
	public String RnDInforRnDInforRegistration(Model model
			, @RequestParam(value="category", defaultValue="C0001") String category
			, HttpServletResponse response
			,HttpServletRequest request) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache");
		
		String url = "admin/registration";
		
		System.out.println("*********************here ****************************************");
		
		model.addAttribute("category",category);
		
		return url;
	}
	
	/**
	 * 
	 * @param model
	 * @param title
	 * @param contents
	 * @param category
	 * @param request
	 * @param redirectAttr
	 * @return
	 * 
	 * 평문,html 에디터를 통한 저장 process
	 */
	@RequestMapping(value="/write_process", method = RequestMethod.POST)
	public String RnDInforRnDInforWriteProcess(Model model
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
		
        String savePath = request.getServletContext().getRealPath("ckeditor\\upload")+ "\\";
		
		System.out.println("*********************savePath :"+savePath);
		
		
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
			int  inschk = cntsmngDao.ContentInsert(idt);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/physical_List";
	}
	
	/**
	 * 
	 * @param model
	 * @param category
	 * @param request
	 * @param redirectAttr
	 * @return
	 * 
	 * editor 내용 갱신
	 */
	@RequestMapping(value="/update_process", method = RequestMethod.POST)
	public String RnDInforRnDInforUpdateProcess(Model model
			, @RequestParam(value="title", defaultValue="") String title
			, @RequestParam(value="bNo", defaultValue="") int bNo
			, @RequestParam(value="contents", defaultValue="") String contents
			, @RequestParam(value="category", defaultValue="C0001") String category
			, HttpServletResponse response
			, HttpServletRequest request
			, RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
        String savePath = request.getServletContext().getRealPath("ckeditor\\upload")+ "\\";
		
		System.out.println("*********************savePath :"+savePath);
		
		redirectAttr.addAttribute("category",category);
		
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
			int  inschk = cntsmngDao.ContentUpdate(idt);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 
		return "redirect:/physical_List";
	}
	
	
	
	@RequestMapping(value="/imageGileView")
	public void imgView(@RequestParam(value="fileID", defaultValue="") String fileID
			, @RequestParam(value="fileName", defaultValue="") String fileName
			, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String path = request.getServletContext().getRealPath("ckeditor\\upload")+ "\\";
		
		String sDirPath = path + fileID + "_" + fileName;
		
		File imgFile = new File(sDirPath);
		
		if(imgFile.isFile()) {
			
			byte[] buf = new byte[1024];
    		int readByte = 0;
    		int length = 0;
    		byte[] imgBuf = null;
			
    		FileInputStream fileInputStream = null;
    		ByteArrayOutputStream outputStream = null;
    		ServletOutputStream out = null;
    		
    		try {
    			
    			fileInputStream = new FileInputStream(imgFile);
    			outputStream = new ByteArrayOutputStream();
    			out = response.getOutputStream();
    			
    			while((readByte = fileInputStream.read(buf)) != -1){
    				outputStream.write(buf, 0, readByte); 
    			}
    			
    			imgBuf = outputStream.toByteArray();
    			length = imgBuf.length;
    			out.write(imgBuf, 0, length);
    			out.flush();
    			
    		}catch(IOException e) {
    			e.printStackTrace();
    		}finally {
    			outputStream.close();
    			fileInputStream.close();
    			out.close();
    		}
		}
	}
	
	@RequestMapping(value="/delete_List")
	public String RnDInforDelete_List(Model model, HttpServletRequest request,
			@RequestParam(value="boardNo", required=true) List<String> bNoList,
			@RequestParam(value="category", defaultValue="C0001") String category,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		System.out.println("category---==============================================================:"+category);
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "redirect:/physical_List";

		try {
			
			System.out.println("bNoList.size()---==============================================================:"+bNoList.size());
			for(int i=0;i<bNoList.size();i++) {
				System.out.println("no:"+bNoList.get(i));
			}
			cntsmngDao.ContentDelete(bNoList);
			
			model.addAttribute("category",category);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return url;
	}
	
	@RequestMapping(value="/content_View")
	public String RnDInforContent_View(Model model, HttpServletRequest request,
			@RequestParam(value="bNo", defaultValue="") String bNo,
			@RequestParam(value="category", defaultValue="C0001") String category,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/detailView";
		
		redirectAttr.addAttribute("category", category);
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("no", bNo);
		
		try {
			
			
			ContentsDTO vObj = cntsmngDao.ContentView(param);
			
			model.addAttribute("ContentView",vObj);
			model.addAttribute("category",category);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
			
		return url;
	}

	@RequestMapping(value="/manager_update")
	public String RnDInforManager_Update(Model model, HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/manager_ip";
					  
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("ip","");
		
		String ip1,id1 = "";
		String ip2,id2 = "";
		String ip3,id3 = "";
		String ip4,id4 = "";
		String ip5,id5 = "";
		
		try {
			
			List<ManagerDTO> ov = cntsmngDao.ManagerList(param);
			
			model.addAttribute("ipList",ov);
			/*
			for(int i=0;i < ov.size();i++) {
				
				if(i == 0) {
					ManagerDTO tmp =ov.get(i);
					ip1 = tmp.ip;
					id1 = tmp.id;
					model.addAttribute("ip1",ip1);
					model.addAttribute("id1",id1);
				}else if(i == 1 ) {
					ManagerDTO tmp =ov.get(i);
					ip2 = tmp.ip;
					id2 = tmp.id;
					model.addAttribute("id2",id2);
					model.addAttribute("ip2",ip2);
				}else if(i == 2 ) {
					ManagerDTO tmp =ov.get(i);
					ip3 = tmp.ip;
					id3 = tmp.id;
					model.addAttribute("id3",id3);
					model.addAttribute("ip3",ip3);
				}else if(i == 3 ) {
					ManagerDTO tmp =ov.get(i);
					ip4 = tmp.ip;
					id4 = tmp.id;
					model.addAttribute("id4",id4);
					model.addAttribute("ip4",ip4);
				}else if(i == 4 ) {
					ManagerDTO tmp =ov.get(i);
					ip5 = tmp.ip;
					id5 = tmp.id;
					model.addAttribute("id5",id5);
					model.addAttribute("ip5",ip5);
				}
			}
			*/
			
		} catch (Exception e) {

			e.printStackTrace();
		}
        
		return url;
	}
	
	@RequestMapping(value="/manager_updateProcesss")
	public String RnDInforManager_UpdateProcess(Model model, HttpServletRequest request,
			@RequestParam(value="ip1", defaultValue="") String ip1,
			@RequestParam(value="ip2", defaultValue="") String ip2,
			@RequestParam(value="ip3", defaultValue="") String ip3,
			@RequestParam(value="ip4", defaultValue="") String ip4,
			@RequestParam(value="ip5", defaultValue="") String ip5,
			@RequestParam(value="id1", defaultValue="") String id1,
			@RequestParam(value="id2", defaultValue="") String id2,
			@RequestParam(value="id3", defaultValue="") String id3,
			@RequestParam(value="id4", defaultValue="") String id4,
			@RequestParam(value="id5", defaultValue="") String id5,
			@RequestParam(value="ip1_old", defaultValue="") String ip1_old,
			@RequestParam(value="ip2_old", defaultValue="") String ip2_old,
			@RequestParam(value="ip3_old", defaultValue="") String ip3_old,
			@RequestParam(value="ip4_old", defaultValue="") String ip4_old,
			@RequestParam(value="ip5_old", defaultValue="") String ip5_old,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "redirect:/manager_update";
		ArrayList mList = new ArrayList();
		List<String> ipList = new ArrayList();
		
		try {
	        
			if(!ip1.equals("")&&ip1_old.equals("")) {
				System.out.println("ip1 :^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+ip1);
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip1;
	        	mtemp.id = id1;
	        	
	        	mList.add(mtemp);
	        	
			}else if(!ip1.equals("") &&!ip1_old.equals("")) {
				System.out.println("ip1 :------------------------------------------:"+ip1);
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip1;
	        	mtemp.id = id1;
        		mtemp.ip_old = ip1_old;	
	        	
	        	mList.add(mtemp);
			}
			
			if(!ip2.equals("")&&ip2_old.equals("")) {
				System.out.println("ip2 :^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+ip2);
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip2;
	        	mtemp.id = id2;
	        	
	        	mList.add(mtemp);
	        	
			}else if(!ip2.equals("") &&!ip2_old.equals("")) {
				System.out.println("ip2 :~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:"+ip2);
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip2;
	        	mtemp.id = id2;

	        	mtemp.ip_old = ip2_old;	
	        	
	        	mList.add(mtemp);
			}
							
			if(!ip3.equals("")&&ip3_old.equals("")) {
				
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip3;
	        	mtemp.id = id3;
	        	
	        	mList.add(mtemp);
	        	
			}else if(!ip3.equals("")&&!ip3_old.equals("")) {
				
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip3;
	        	mtemp.id = id3;

	        	mtemp.ip_old = ip3_old;	
	        	
	        	mList.add(mtemp);
			}
			
			if(!ip4.equals("") && ip4_old.equals("")) {
				
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip4;
	        	mtemp.id = id4;
	        	
	        	mList.add(mtemp);
			}else if(!ip4.equals("") && !ip4_old.equals("")) {
				
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip4;
	        	mtemp.id = id4;
	      
	        	mtemp.ip_old = ip4_old;	
	        	
	        	mList.add(mtemp);
			}
			
			if(!ip5.equals("") && ip5_old.equals("")) {
				
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip5;
	        	mtemp.id = id5;
	        	
	        	mList.add(mtemp);
			}else if(!ip5.equals("")&& !ip5_old.equals("")) {
				
				ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip5;
	        	mtemp.id = id5;

	        	mtemp.ip_old = ip5_old;	
	        	
	        	mList.add(mtemp);
				
			}

			
				for(int i=0;i<mList.size();i++) {
	        	
		           ManagerDTO ov = (ManagerDTO) mList.get(i);
				  
		          if(ov != null) {

			          if(!(ov.ip_old).equals("")) {
		        		  int check = cntsmngDao.ManagerUpdate(ov); 
			          }else {
			        	  int check = cntsmngDao.ManagerInsert(ov);  
			          }
		          }
				 
		        }
				
				//delete
				
				if(ip1.equals("")&&!ip1_old.equals("")) {
					ipList.add(ip1);
				}
				
				if(ip2.equals("")&&!ip2_old.equals("")) {
					ipList.add(ip2);
				}
				
				if(ip3.equals("")&&!ip3_old.equals("")) {
					ipList.add(ip3);
				}
				
				if(ip4.equals("")&&!ip4_old.equals("")) {
					ipList.add(ip4);
				}
				
				if(ip5.equals("")&&!ip5_old.equals("")) {
					ipList.add(ip5);
				}
				
				
				if(ipList.size()>0) {
						cntsmngDao.ManagerDelete(ipList);  
				}
				
		} catch (Exception e) {

			e.printStackTrace();
		}
			
		return url;
	}
	
	@RequestMapping(value="/manager_insertProcesss")
	public String RnDInforManager_InsertProcess(Model model, HttpServletRequest request,
			@RequestParam(value="ip1", defaultValue="") String ip1,
			@RequestParam(value="id1", defaultValue="") String id1,
			@RequestParam(value="ip1_old", defaultValue="") String ip1_old,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "redirect:/manager_update";
		ArrayList mList = new ArrayList();
		List<String> ipList = new ArrayList();
		
		try {
	        
	    		ManagerDTO mtemp = new ManagerDTO();
				
				mtemp.ip = ip1;
	        	mtemp.id = id1;
	        	
	        	int check = cntsmngDao.ManagerInsert(mtemp);  

		} catch (Exception e) {

			e.printStackTrace();
		}
			
		return url;
	}
	
	@RequestMapping(value="/delete_ip")
	public String RnDInforDelete_IP(Model model, HttpServletRequest request,
			@RequestParam(value="ipNo", required=true) List<String> ipNoList,
			@RequestParam(value="category", defaultValue="C0001") String category,
			HttpServletResponse response,RedirectAttributes redirectAttr) {
		System.out.println("category---==============================================================:"+category);
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		
		String url = "admin/manager_ip";

		try {
			
			System.out.println("ipNoList.size()---==============================================================:"+ipNoList.size());
			for(int i=0;i<ipNoList.size();i++) {
				System.out.println("no:"+ipNoList.get(i));
			}
			cntsmngDao.ManagerDelete(ipNoList); 
			
			model.addAttribute("category",category);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return url;
	}
}
