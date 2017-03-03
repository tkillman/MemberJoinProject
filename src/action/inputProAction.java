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
		
		//Data 에 세팅
		
		
		
		// 요청 타입이 multipart/form-data 인가? 맞으면 true 아니면 false
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipart) {
			//아니라면 HttpServletResponse.SC_BAD_REQUEST 잘못된 요청
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		//fileItem을 만들어주는 DiskFileItemFactory 객체 factory 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 업로드 요청을 처리할 ServletFileUpload 객체 upload 생성
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		
		//메모리나 임시 디렉토리에 올린다.
		List<FileItem> items = upload.parseRequest(request);
		
		Iterator<FileItem> iter = items.iterator();
		
		while (iter.hasNext()) {
			FileItem item = iter.next();
			
			if (item.isFormField()) { // type="file" 이면 false 반환 아니면 true 반환
				
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
				
				
			
				
			} else { //파일 처리
				
				String name = item.getFieldName();
				if (name.equals("photo")) { // input 태그 네임이  file
					
					String realPath = FileSaveHelper.save("c:\\Java\\pds", item.getInputStream());
					
					logonDataBean.setRealPath(realPath);
					
				}
			}
		}
		
		
		
		//DBBean 회원가입메소드 실행
		logonDBBean.insertMember(logonDataBean);
		
		request.setAttribute("joinOk", "ok");
		
		return "/main.jsp";
	}

}
