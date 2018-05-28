package web.manager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



import org.xml.sax.InputSource;

import service.impl.BusinessServiceImpl;
import utils.WebUtils;
import dao.CategoryDao;
import domain.Book;
import domain.Category;
import domain.Page;

public class BookServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BookServlet() {
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
		String method = request.getParameter("method");
		if(method.equalsIgnoreCase("addUI")){
			addUI(request,response);
		}
		if(method.equalsIgnoreCase("add")){
			add(request,response);
		}	
		if(method.equalsIgnoreCase("list")){
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
	
	private void list(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		String pagenum = request.getParameter("pagenum");
		BusinessServiceImpl service=new BusinessServiceImpl();
		Page page= service.getBookPageData(pagenum);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manager/listbook.jsp").forward(request, response);
	}
	
	private void add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		try {
			Book book=doupLoad(request);
			BusinessServiceImpl service=new BusinessServiceImpl();
			book.setId(WebUtils.makeID());
			service.addBook(book);
			request.setAttribute("message", "添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("message", "添加失败");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
		
	}

	private Book doupLoad(HttpServletRequest request){
		//把上传的图片保存到images目录中，并把request中的请求参数封装到Book中
		//这个地方有一个bug 文件没有上传到images 上传了 在服务器中
		//注意item是表单中的每一项
		Book book=new Book();
		try {
			DiskFileItemFactory factory =new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item : list){
				if(item.isFormField()){
					//普通表单
					String name=item.getFieldName();
					String value=item.getString("UTF-8");
					BeanUtils.setProperty(book, name, value);
				}else{
					//文件表单
					String filename=item.getName();
					String savefilename=makeFileName(filename);//得到保存在硬盘的文件名
					String savepath=this.getServletContext().getRealPath("/images");
					//D:\apache-tomcat-8.5.29\webapps\MyBookStore\images\
					InputStream in =item.getInputStream();
					FileOutputStream out=new FileOutputStream(savepath+"\\"+savefilename);
					int len=0;
					byte buffer[] =new byte[1024];
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
					}
					in.close();
					out.close();
					item.delete();
					book.setImage(savefilename);
				}
			}
			return book;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String makeFileName(String filename){
		String ext=filename.substring(filename.lastIndexOf(".")+1);//得到文件类型
		return UUID.randomUUID().toString()+"."+ext;
	}
	
	private void addUI(HttpServletRequest request,HttpServletResponse response)
		throws ServletException,IOException{
		BusinessServiceImpl service=new BusinessServiceImpl();
		//在添加图书前得到分类信息 并传给addbook的选择项
		List<Category> category=service.getAllCategory();
		request.setAttribute("categories", category);
		request.getRequestDispatcher("/manager/addBook.jsp").forward(request, response);
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
