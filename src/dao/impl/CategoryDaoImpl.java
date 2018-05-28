package dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.JdbcUtils;
import dao.CategoryDao;
import domain.Category;

public class CategoryDaoImpl implements CategoryDao{

	@Override
	public void add(Category category) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="insert into category (id,name,description) values (?,?,?)";
			Object[] params={category.getId(),category.getName(),category.getDescription()};
			qr.update(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Category find(String id) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from category where id = ?";
			return (Category) qr.query(sql, new BeanHandler(Category.class), id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from category";
			return (List<Category>) qr.query(sql, new BeanListHandler(Category.class));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
