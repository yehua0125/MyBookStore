package dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.JdbcUtils;
import dao.OrderDao;
import domain.Book;
import domain.Order;
import domain.OrderItem;
import domain.User;

public class OrderDaoImpl implements OrderDao{

	@Override
	public void add(Order order) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			//1.把order的基本信息保存到order表中
			String sql="insert into orders (id, ordertime, price, state, users_id) values ( ?,?,?,?,?)";
			Object[] params={order.getId(),order.getOrdertime(),order.getPrice(),order.isState(),order.getUser().getId()};
			qr.update(sql,params);
			//2.把order中的订单项保存到orderitem表中
			Set<OrderItem> set= order.getOrderitems();
			for(OrderItem item:set){
				sql="insert into orderitem (id,quantity,price,order_id,book_id) values (?,?,?,?,?)";
				params=new Object[] {item.getId(),item.getQuantity(),item.getPrice(),order.getId(),item.getBook().getId()};
				//这种new的方式有什么意义？
				qr.update(sql,params);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Order find(String id) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			//1.找出订单的基本信息
			String sql="select * from orders where id = ? ";
			Order order=(Order) qr.query(sql, new BeanHandler(Order.class), id);
			//2.找出订单中所有的订单项
			sql="select * from orderitem where order_id = ?";
			List<OrderItem> list =  qr.query(sql, new BeanListHandler(OrderItem.class), id);
			for(OrderItem item : list){
				sql ="select book.* from orderitem , book where orderitem.id = ? and orderitem.book_id = book.id ";
				Book book=(Book)qr.query(sql,new BeanHandler(Book.class), item.getId());
				item.setBook(book);
			}
			//把找出的订单项放入order
			order.getOrderitems().addAll(list);
			//3.找出订单属于哪个用户
			sql="select * from orders,users where orders.id = ? and orders.users_id = users.id";
			User user=(User)qr.query(sql, new BeanHandler(User.class), order.getId());
			order.setUser(user);
			return order;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	//后台获取所有的订单
	public List<Order> getAll(boolean state) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from orders where state = ?";
			List<Order> list = qr.query(sql,new BeanListHandler(Order.class),state);
			for(Order order:list){
				//找出当前订单属于哪个用户
				sql="select users.* from orders , users where orders.id = ? and orders.users_id = users.id";
				User user=(User)qr.query(sql, new BeanHandler(User.class),order.getId());
				order.setUser(user);
			}
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	//前端页面中获取某个用户的所有订单
	public List<Order> getAll(boolean state, String userid) {
		// TODO Auto-generated method stub
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where state=? and orders.users_id=?";
			Object params[] = {state, userid};
			List<Order> list = (List<Order>) runner.query(sql, new BeanListHandler(Order.class), params);
			//将所有该user加到list中
			for(Order order : list){
				sql = "select * from users where user.id=?";
				User user = (User) runner.query(sql, new BeanHandler(User.class), userid);
				order.setUser(user);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Order> getAllOrder(String userid) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from orders where users_id=?";
		List<Order> list = (List<Order>) runner.query(sql, new BeanListHandler(Order.class), userid);
		//将所有该user加到List中去
		for(Order order : list){
			sql = "select * from users where id=?";
			User user = (User) runner.query(sql, new BeanHandler(User.class), userid);
			order.setUser(user);
		}
		return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	//改变发货状态，实际中还可以改变购买数量等其他信息，可以再完善
	public void update(Order order) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="update orders set state = ? where id = ?";
			Object []params={order.isState(),order.getId()};
			qr.update(sql,params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
		
	}

}
