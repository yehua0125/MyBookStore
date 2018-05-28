package service.impl;

import java.util.List;

import dao.BookDao;
import dao.CategoryDao;
import dao.UserDao;
import domain.Book;
import domain.Category;
import domain.Page;
import domain.User;
import service.BusinessService;
import utils.DaoFactory;

public class BusinessServiceImpl implements BusinessService{

	
	private UserDao userDao=DaoFactory.getInstance().createDao("dao.impl.UserDaoImpl", UserDao.class);
	private CategoryDao categorydao=DaoFactory.getInstance().createDao("dao.impl.CategoryDaoImpl", CategoryDao.class);
	private BookDao bookDao=DaoFactory.getInstance().createDao("dao.impl.BookDaoImpl", BookDao.class);
	
	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		categorydao.add(category);
	}

	@Override
	public Category findCategory(String id) {
		// TODO Auto-generated method stub
		return categorydao.find(id);
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return categorydao.getAll();
	}
	
	public void addBook(Book book){
		bookDao.add(book);
	}
	
	public Book findBook(String id){
		return bookDao.find(id);
	}
	
	//获得分页数据
	public Page getBookPageData(String pagenum){
		int totalrecord=bookDao.getTotalRecord();
		Page page=null;
		if(pagenum == null){
			page=new Page(1,totalrecord);
		}else{
			page=new Page(Integer.parseInt(pagenum),totalrecord);
		}
		List<Book> list=bookDao.getPageData(page.getStartindex(), page.getPagesize());
		page.setList(list);
		return page;
	}
	//获得某个类型的图书
	public Page getBookPageData(String pagenum,String category_id){
		int totalrecord=bookDao.getTotalRecord(category_id);
		Page page=null;
		if(pagenum == null){
			page=new Page(1,totalrecord);
		}else{
			page=new Page(Integer.parseInt(pagenum),totalrecord);
		}
		List<Book> list=bookDao.getPageData(page.getStartindex(), page.getPagesize());
		page.setList(list);
		return page;
	}
	
	//注册用户
	public void registerUser(User user){
		userDao.add(user);
	}
	public User findUser(String id){
		
		return userDao.find(id);
	}
	public User userLogin(String username,String password){
		return userDao.find(username, password);
	}
	
	
	
	
}
