package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.LogonDBBean;

public class loginProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String id = request.getParameter("id");
		System.out.println(id);
		
		String passwd  = request.getParameter("passwd");
		
		LogonDBBean manager = LogonDBBean.getInstance();
	    int check= manager.userCheck(id,passwd);
		
	    
	    if(check==1){
	    	
	    	HttpSession session = request.getSession();
	    	session.setAttribute("memId", id);
	    	
	    	
	    	
	    	return "/main.jsp";
	    }
	    
	    
	    request.setAttribute("check", check);
	    
	    
	    
	    
	    return "/loginPro.jsp";
	}

	
	
}
