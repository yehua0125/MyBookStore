package web.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
import utils.WebUtils;
import domain.Category;

public class CategoryServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CategoryServlet() {
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
		String method=request.getParameter("method");
		if(method.equals("add")){
			add(request,response);
		}
		if(method.equals("listall")){
			list(request,response);
		}

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

	private void add(HttpServletRequest request,HttpServletResponse response)
		throws ServletException,IOException{
		try{
			String name=request.getParameter("name");
			String description=request.getParameter("descriprion");
			
			Category category=new Category();
			category.setName(name);
			category.setDescription(description);
			category.setId(WebUtils.makeID());
			
			BusinessServiceImpl service=new BusinessServiceImpl();
			service.addCategory(category);
			request.setAttribute("message", "添加成功");
		}catch (Exception e){
			e.printStackTrace();
			request.setAttribute("message", "添加失败");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
		
	}
	
	private void list(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
			List<Category> categories=new ArrayList();
			BusinessServiceImpl service=new BusinessServiceImpl();
			categories = service.getAllCategory();
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/manager/listcategory.jsp").forward(request, response);
		
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
