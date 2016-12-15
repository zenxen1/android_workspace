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
	
	//�� ���� ��ü�� �ν��Ͻ��� �޸𸮿� ������ ����,
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
			List<FileItem> items = upload.parseRequest(req); /// ���ε� ����
			
			//improved for�� : �ٸ� ����� for each ���� ����
			for(FileItem item:items){
				if(!item.isFormField()){  //text filed�� �ƴ϶��....
					
					System.out.println("���� ������ �߰�"+item.getName());
					is = item.getInputStream();
					fos = new FileOutputStream(savePath+item.getName());
					
					byte[] b = new byte[1024];
					
					int flag=-1;
					
					while(true){
						flag=is.read(b); //�б�
						if(flag==-1)break;
						fos.write(b); //����
						System.out.println(b);
					}
					System.out.println("����Ϸ�");
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
