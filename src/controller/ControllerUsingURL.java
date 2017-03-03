package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommandAction;




@WebServlet(
		urlPatterns = "*.do"
		,initParams ={
				@WebInitParam(
						name = "property"
						,value = "WEB-INF/commandHandlerURI.properties"
						
						)
		}
		)
public class ControllerUsingURL extends HttpServlet{

	
	private Map commandHandlerMap = new java.util.HashMap();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		String configFile = config.getInitParameter("property");
		Properties prop = new Properties();
		FileInputStream fis = null;
		
		try {
			
			String configFilePath = config.getServletContext().getRealPath(configFile);
			fis = new FileInputStream(configFilePath);
			prop.load(fis);
			
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException ex) {
				}
			
		}
		
		Iterator keyIter = prop.keySet().iterator();
		while (keyIter.hasNext()) {
			
			String command = (String) keyIter.next();
			String handlerClassName = prop.getProperty(command);
			try {
				Class handlerClass = Class.forName(handlerClassName);
				Object handlerInstance = handlerClass.newInstance();
				commandHandlerMap.put(command, handlerInstance);
				
			} catch (ClassNotFoundException e) {
				throw new ServletException(e);
			} catch (InstantiationException e) {
				throw new ServletException(e);
			} catch (IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
		
		
		
		
	}

	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}


	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String view = null;
	    CommandAction com=null;
		try {
				req.setCharacterEncoding("utf-8");
	           
				String command = req.getRequestURI(); // /MVC_board/MVC/writeForm.do
	            
	            if (command.indexOf(req.getContextPath()) == 0) {// /MVC_board
	               
	            command = command.substring(req.getContextPath().length());
	               //   /MVC/WriteForm.do
	               
	            }
	            com = (CommandAction)commandHandlerMap.get(command); 
	           
	            //요청 처리후 반환될 상대주소경로를 String 형태로 return 해준다.
	            view = com.requestPro(req, resp);
	            
	        } catch(Throwable e) {
	            throw new ServletException(e);
	        }  
		
			//
	       
		RequestDispatcher dispatcher =req.getRequestDispatcher(view);
	        dispatcher.forward(req, resp);
		
	}
	
	
	
	
}
