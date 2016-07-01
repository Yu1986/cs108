package hw5;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountManagerTest {
	
	private AccountManager am;
	
	@Before
	public void setUp() throws Exception {
		am = AccountManager.getInstance();
	}

	@Test
	public void testDefaultUser() {
		//fail("Not yet implemented");
		assertTrue(am.isCorrectPassword("wandou", "mybaby"));
		assertFalse(am.isCorrectPassword("wandou", "mybby"));
	}
	
	@Test
	public void testAddUser() {
		//fail("Not yet implemented");
		assertTrue(am.addNewAccount("test1", "test1"));
		assertTrue(am.isCorrectPassword("test1", "test1"));
		assertFalse(am.isCorrectPassword("test1", "test"));
		assertFalse(am.isCorrectPassword("test2", "test"));
		assertFalse(am.addNewAccount("test1", "test1"));
	}

}
