package service.impl;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dao.BookDao;
import dao.CategoryDao;
import dao.OrderDao;
import dao.UserDao;
import domain.Book;
import domain.Cart;
import domain.CartItem;
import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Page;
import domain.User;
import service.BusinessService;
import utils.DaoFactory;
import utils.WebUtils;

public class BusinessServiceImpl implements BusinessService{

	
	private UserDao userDao=DaoFactory.getInstance().createDao("dao.impl.UserDaoImpl", UserDao.class);
	private CategoryDao categorydao=DaoFactory.getInstance().createDao("dao.impl.CategoryDaoImpl", CategoryDao.class);
	private BookDao bookDao=DaoFactory.getInstance().createDao("dao.impl.BookDaoImpl", BookDao.class);
	private OrderDao orderDao=DaoFactory.getInstance().createDao("dao.impl.OrderDaoImpl", OrderDao.class);
	
	
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
	
	//购买书籍
	public void buyBook(Cart cart,Book book){
		cart.add(book);
		
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
	
	public void createOrder(Cart cart,User user){
		if(cart == null){
			throw new RuntimeException("sorry,you havn't bought any product!");
		}
		Order order=new Order();
		order.setId(WebUtils.makeID());
		order.setOrdertime(getDateTime(new Date()));
		order.setPrice(cart.getPrice());
		order.setState(false);
		order.setUser(user);
		for(Map.Entry<String, CartItem> me :cart.getMap().entrySet()){
			//得到一个购物事项就生成一个订单项
			CartItem citem=me.getValue();
			OrderItem oitem=new OrderItem();
			oitem.setBook(citem.getBook());
			oitem.setPrice(citem.getPrice());
			oitem.setId(WebUtils.makeID());
			oitem.setQuantity(citem.getQuantity());
			order.getOrderitems().add(oitem);
			
		}
		orderDao.add(order);
	}
	
	
	public java.sql.Timestamp getDateTime(Date datetime){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS",Locale.ENGLISH);
		dateFormat.setLenient(false);//严格解析
		java.sql.Timestamp dateTime=new java.sql.Timestamp(datetime.getTime());
		return dateTime;
	}
	
	//后台获取所有订单信息
	public List<Order> listOrder(String state){
		return orderDao.getAll(Boolean.parseBoolean(state));
	}
	
	//列出订单明细
	public Order findOrder(String orderid){
		return orderDao.find(orderid);
	}
	
	//把订单设置为发货状态
	public void confirmOrder(String orderid){
		Order order=orderDao.find(orderid);
		order.setState(true);
		orderDao.update(order);
	}
	
	//获取某个用户的订单信息
	public List<Order> listOrder(String state,String userid){
		return orderDao.getAll(Boolean.parseBoolean(state),userid);
	}
	
	//获取某个用户的订单信息
	public List<Order> clientListOrder(String userid){
		return orderDao.getAllOrder(userid);
	}
	
}
