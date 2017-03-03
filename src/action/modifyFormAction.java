package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.LogonDBBean;
import board.LogonDataBean;

public class modifyFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		 HttpSession session = request.getSession();
		
		 String id = (String)session.getAttribute("memId");
		 LogonDBBean manager = LogonDBBean.getInstance();
		 LogonDataBean c = manager.getMember(id);
		
		 request.setAttribute("c", c);
		
		
		return "/modifyForm.jsp";
	}

}
