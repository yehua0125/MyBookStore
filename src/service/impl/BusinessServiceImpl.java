package service.impl;

import java.util.List;

import dao.UserDao;
import domain.Category;
import domain.User;
import service.BusinessService;
import utils.DaoFactory;

public class BusinessServiceImpl implements BusinessService{

	
	private UserDao userDao=DaoFactory.getInstance().createDao("dao.impl.UserDaoImpl", UserDao.class);
	
	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category findCategory(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	
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
