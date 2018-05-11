package dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dao.UserDao;
import domain.User;
import utils.JdbcUtils;

public class UserDaoImpl implements UserDao{

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="insert into users(id,username,password,phone,address,email) values(?,?,?,?,?)";
			Object params[]={user.getId(),user.getUsername(),user.getPassword(),user.getPhone(),user.getAddress(),user.getEmail()};
			qr.update(sql, params);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		try{
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from users where id = ?";
			return (User)qr.query(sql, new BeanHandler(User.class),id);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public User find(String username, String password) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from users where username = ? and password = ?";
			Object []params ={username,password};
			return (User)qr.query(sql, new BeanHandler(User.class), params);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

}
