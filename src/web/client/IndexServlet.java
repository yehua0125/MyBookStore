package web.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Page;
import service.impl.BusinessServiceImpl;

public class IndexServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public IndexServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method =request.getParameter("method");
		if(method.equalsIgnoreCase("getAll")){		
			getAll(request, response);
			
		}else if(method.equalsIgnoreCase("listBookWithCategory")){
			
		}
	}
	
	private void getAll(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		BusinessServiceImpl service=new BusinessServiceImpl();
		List<Category> categories =service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum=request.getParameter("pagenum");
		Page page=service.getBookPageData(pagenum);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/body.jsp").forward(request, response);
	}
	
	
	public void listBookWithCategory(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		BusinessServiceImpl service=new BusinessServiceImpl();
		String category_id=request.getParameter("category_id");
		List<Category> categories=service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum=request.getParameter("pagenum");
		Page page=service.getBookPageData(pagenum,category_id);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/body.jsp").forward(request, response);
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
