package action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.LogonDBBean;
import board.ZipcodeBean;

public class zipCheckAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("utf-8");

		
	   String check = request.getParameter("check");
	
	   String area3 = request.getParameter("area4");
	   LogonDBBean manager = LogonDBBean.getInstance();  
	   Vector<ZipcodeBean> zipcodeList = manager.zipcodeRead(area3);
	   int totalList = zipcodeList.size();	// °Ë»öµÈ °¹¼ö°¡ ¸î°µÁö 
		
	   
	 
	   request.setAttribute("check", check);
	   request.setAttribute("zipcodeList", zipcodeList);
	   request.setAttribute("totalList", totalList);
	   
	   
	   return "/ZipCheck.jsp";
	}

	
	
	
}
