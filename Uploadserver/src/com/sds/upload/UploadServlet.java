package com.sds.upload;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet{
	DiskFileItemFactory factory;
	ServletFileUpload upload;
	String savePath;
	
	//이 서블릿 객체의 인스턴스가 메모리에 생성된 직후,
	public void init(ServletConfig config) throws ServletException {
		savePath = config.getInitParameter("savePath");
		savePath = config.getServletContext().getRealPath(savePath);
		System.out.println(savePath);
		
		factory = new DiskFileItemFactory();
		upload = new ServletFileUpload(factory);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FileOutputStream fos = null;
		InputStream is = null;
		
		try {
			List<FileItem> items = upload.parseRequest(req); /// 업로드 수행
			
			//improved for문 : 다른 언어의 for each 문과 같다
			for(FileItem item:items){
				if(!item.isFormField()){  //text filed가 아니라면....
					
					System.out.println("파일 아이템 발견"+item.getName());
					is = item.getInputStream();
					fos = new FileOutputStream(savePath+item.getName());
					
					byte[] b = new byte[1024];
					
					int flag=-1;
					
					while(true){
						flag=is.read(b); //읽기
						if(flag==-1)break;
						fos.write(b); //쓰기
						System.out.println(b);
					}
					System.out.println("복사완료");
				}
			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fos!=null){
				fos.close();
			}
			if(is!=null){
				is.close();
			}
		}
	}
	
	public void destroy() {
	}
	
	
}
