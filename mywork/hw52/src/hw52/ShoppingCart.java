package hw52;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ShoppingCart {
	
	public class Cart {
		public ProductUtils.Product prod;
		public int num;
	}

	public static final String ATTRIBUTE_NAME = "Shopping Cart";
	
	public ShoppingCart() {
		cartMap = new HashMap<String, Integer>();
		cartList = new ArrayList<Cart>();
	}
	
	public void addProduct(String id) {
		if (cartMap.containsKey(id)) {
			cartMap.put(id, cartMap.get(id) + 1);
		} else {
			cartMap.put(id, 1);
		}
	}
	
	public void updateProductNum(String id, int num) {
		cartMap.put(id, num);
	}
	
	public ArrayList<Cart> getCartList() {
		Set<String> key = cartMap.keySet();
		cartList.clear();
		for (String id: key) {
			Cart cart = new Cart();
			cart.prod = ProductUtils.getInstance().getProductById(id);
			cart.num = cartMap.get(id);
			cartList.add(cart);
		}
		
		return cartList;
	}
	
	private ArrayList<Cart> cartList;
	private HashMap<String, Integer> cartMap;
}
