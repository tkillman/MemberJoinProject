package action;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import board.LogonDBBean;
import board.LogonDataBean;
import pds.file.FileSaveHelper;


public class inputProAction implements CommandAction{


	
	
	
	
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("utf-8");
		
		LogonDataBean logonDataBean = new LogonDataBean();
		
		LogonDBBean logonDBBean = LogonDBBean.getInstance();
		
		
		logonDataBean.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		//Data �� ����
		
		
		
		// ��û Ÿ���� multipart/form-data �ΰ�? ������ true �ƴϸ� false
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipart) {
			//�ƴ϶�� HttpServletResponse.SC_BAD_REQUEST �߸��� ��û
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		//fileItem�� ������ִ� DiskFileItemFactory ��ü factory ����
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// ���ε� ��û�� ó���� ServletFileUpload ��ü upload ����
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		
		//�޸𸮳� �ӽ� ���丮�� �ø���.
		List<FileItem> items = upload.parseRequest(request);
		
		Iterator<FileItem> iter = items.iterator();
		
		while (iter.hasNext()) {
			FileItem item = iter.next();
			
			if (item.isFormField()) { // type="file" �̸� false ��ȯ �ƴϸ� true ��ȯ
				
				String name = item.getFieldName();
				//System.out.println(name);
				
				if(name.equals("id")){
					
					String id= item.getString("utf-8");
					
					logonDataBean.setId(id);
				
				}
				
				if(name.equals("passwd")){
				String passwd = item.getString("utf-8");
				logonDataBean.setPasswd(passwd);
				}
				
				if(name.equals("name")){
				
				String name1 = item.getString("utf-8");
				logonDataBean.setName(name1);
				
				}
				
				if(name.equals("jumin1")){
				
				String jumin1 = item.getString("utf-8");
				logonDataBean.setJumin1(jumin1);
				
				}
				if(name.equals("jumin2")){
				String jumin2 = item.getString("utf-8");
				logonDataBean.setJumin2(jumin2);
				
				}
				if(name.equals("email")){
				String email = item.getString("utf-8");	
				logonDataBean.setEmail(email);
				
				}
				if(name.equals("blog")){
				String blog = item.getString("utf-8");
				logonDataBean.setBlog(blog);
				}
				if(name.equals("zipcode")){
				String zipcode = item.getString("utf-8");	
				logonDataBean.setZipcode(zipcode);
				
				}
				if(name.equals("address")){
				String address = item.getString("utf-8");	
				logonDataBean.setAddress(address);
				
				}
				
				
			
				
			} else { //���� ó��
				
				String name = item.getFieldName();
				if (name.equals("photo")) { // input �±� ������  file
					
					String realPath = FileSaveHelper.save("c:\\Java\\pds", item.getInputStream());
					
					logonDataBean.setRealPath(realPath);
					
				}
			}
		}
		
		
		
		//DBBean ȸ�����Ը޼ҵ� ����
		logonDBBean.insertMember(logonDataBean);
		
		request.setAttribute("joinOk", "ok");
		
		return "/main.jsp";
	}

}
