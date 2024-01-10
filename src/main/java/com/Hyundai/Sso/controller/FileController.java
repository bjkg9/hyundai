package com.Hyundai.Sso.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/fileDown", method = RequestMethod.POST)
public class FileController {
	
	@Autowired
	ResourceLoader resourceLoader;
/*
	@ResponseBody
	@RequestMapping(value="/fileUpload",method= {RequestMethod.POST,RequestMethod.GET})
	public void fileUpload(Model model, @RequestParam(value="upload",required=false)MultipartFile files
			,HttpServletRequest request,  HttpServletResponse response)throws Exception {

		String savePath = request.getServletContext().getRealPath("ckeditor\\upload")+ "\\";
		
		 try {
	        	
		        String origFilename = files.getOriginalFilename();
		        String filename = new MD5Generator(origFilename).toString();
		              
		        String filePath = savePath + "\\" + origFilename;
		     
				files.transferTo(new File(filePath));
					
				System.out.println("*********************** savePath : *********"+ savePath);
				     
			} catch (IllegalStateException | IOException e1) {
			
				e1.printStackTrace();
			}
	 
	}


	  @GetMapping("/download")
	  public void download(HttpServletResponse response) throws IOException {
	    
	    String r_name = "HMG R&D_Information Protection Manual_KR.pdf";
	    
	    String path = "D:/downloadFlie/"+r_name;
	    
	    byte[] fileByte = FileUtils.readFileToByteArray(new File(path));

	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(r_name, "UTF-8")+"\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");

	    response.getOutputStream().write(fileByte);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	  }
	  */
	
	@GetMapping(value = "/download")
    public void download(String l_key, HttpServletResponse response, HttpServletRequest request){

        try {
        	String fileName = "";

        	if(l_key.equals("K")) {
        		fileName = "HMG R&D_Information Protection Manual_KR.pdf";	
        	}else {
        		fileName = "HMG R&D_Information Protection Manual_ENG.pdf";
        	}
        	
        	
            String originFileName = URLDecoder.decode(fileName, "UTF-8");
            String onlyFileName = originFileName;
        	
            File file = new File("D:\\downloadFlie", originFileName);

            if(file.exists()) {
                String agent = request.getHeader("User-Agent");

                //브라우저별 한글파일 명 처리
                if(agent.contains("Trident"))//Internet Explore
                    onlyFileName = URLEncoder.encode(onlyFileName, "UTF-8").replaceAll("\\+", " ");
                    
                else if(agent.contains("Edge")) //Micro Edge
                    onlyFileName = URLEncoder.encode(onlyFileName, "UTF-8");
                    
                else //Chrome
                    onlyFileName = new String(onlyFileName.getBytes("UTF-8"), "ISO-8859-1");
                //브라우저별 한글파일 명 처리

                response.setHeader("Content-Type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=" + onlyFileName);

                InputStream is = new FileInputStream(file);
                OutputStream os = response.getOutputStream();

                int length;
                byte[] buffer = new byte[1024];

                while( (length = is.read(buffer)) != -1){
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@GetMapping("/download2")
	public ResponseEntity<Resource> resouceFileDownload(String l_key){
		try {
			String fileName = "";

        	if(l_key.equals("K")) {
        		fileName = "HMG R&D_Information Protection Manual_KR.pdf";	
        	}else {
        		fileName = "HMG R&D_Information Protection Manual_ENG.pdf";
        	}
        	
			Resource resource = resourceLoader.getResource("classpath:downloadFlie/"+ fileName);	
			File file = resource.getFile();	
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,file.getName())	
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	
					.body(resource);	//파일 넘기기
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest()
						.body(null);
			} catch (Exception e ) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			} 
	}
}
