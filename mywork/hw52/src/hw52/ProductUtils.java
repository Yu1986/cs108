package hw52;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductUtils {
	public class Product {
		public String id;
		public String name;
		public String img;
		public float price;
	}
	
	public static ProductUtils getInstance() {
		if (mPu == null) {
			mPu = new ProductUtils();
		}
		return mPu;
	}
	
	public ArrayList<Product> getProductList() {
		return Productlist;
	}
	
	public Product getProductById(String id) {
		for (Product p : Productlist) {
			if (p.id.compareTo(id) == 0) {
				return p;
			}
		}
		return null;
	}
	
	private ProductUtils() {
		Productlist = new ArrayList<Product>();
		loadFromDB();
	}

	private static ProductUtils mPu = null;
	private ArrayList<Product> Productlist = null;
	
	private void loadFromDB() {
		Statement stmt;
		try {
			stmt = MyDB.getInstance().getConn().createStatement();
			String sql;
			sql = "SELECT * FROM products";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Product p = new Product();
				p.id = rs.getString("productid");
				p.name = rs.getString("name");
				p.img = rs.getString("imagefile");
				p.price = rs.getFloat("price");
				Productlist.add(p);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
