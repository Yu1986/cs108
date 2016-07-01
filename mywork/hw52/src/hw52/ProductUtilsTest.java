package hw52;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductUtilsTest {

	@Test
	public void test() {
		assertTrue(MyDB.getInstance().getConn() != null);
		assertTrue(ProductUtils.getInstance().getProductList().size() == 14);
		assertTrue(ProductUtils.getInstance().getProductById("HC").name.compareTo("Classic Hoodie") == 0);
		
	}

}
