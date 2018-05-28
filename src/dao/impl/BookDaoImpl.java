package dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import utils.JdbcUtils;
import dao.BookDao;
import domain.Book;

public class BookDaoImpl implements BookDao{

	@Override
	public void add(Book book) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="insert into book (id,name,author,price,image,description,category_id) values (?,?,?,?,?,?,?)";
			Object[] params={book.getId(),book.getName(),book.getAuthor(),book.getPrice(),
					book.getImage(),book.getDescription(),book.getCategory_id()};
			qr.update(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Book find(String id) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from book where id = ?";
			return (Book) qr.query(sql, new BeanHandler(Book.class), id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Book> getPageData(int startindex, int pagesize) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			//未验证
			String sql=" select top "+ pagesize +" *  from book where id not in ( select top " + startindex + " id from book )  ";
			return (List<Book>) qr.query(sql, new BeanListHandler(Book.class));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getTotalRecord() {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr=new QueryRunner(JdbcUtils.getDataSource());
			String sql="select count(*) from book ";
			//计数得到目前书籍的数量
			int totalrecord =(Integer) qr.query(sql, new ScalarHandler());

			return totalrecord;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Book> getPageData(int startindex, int pagesize,
			String category_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String category_id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
