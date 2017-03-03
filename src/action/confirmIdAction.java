package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.LogonDBBean;

public class confirmIdAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		 request.setCharacterEncoding("utf-8");
		 String id = request.getParameter("id");
		 LogonDBBean manager = LogonDBBean.getInstance();
		 int check= manager.confirmId(id);
		
	
		 request.setAttribute("id", id);
		 request.setAttribute("check", check);
		 
		 
		return "/confirmId.jsp";
	}

	
	
	
}
