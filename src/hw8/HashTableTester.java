/**
 * Name: <Cy Hynes>
 * PID: <A14179344>
 * Login: <cs12fcf>
 */
package hw8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester for the HashTable class
 * 
 * @author Cy Hynes
 * @version 1.0
 * @since Nov 24, 2016
 */
public class HashTableTester {

	HashTable ht1;
	HashTable ht2;
	HashTable ht3;

	@Before
	public void setUp()
	{
		ht1 = new HashTable(1); // empty

		ht2 = new HashTable(2); // 1 elt
		ht2.insert("element");

		ht3 = new HashTable(1); // multiple elts (4)
		ht3.insert("Hello");
		ht3.insert("Goodbye");
		ht3.insert("OK");
		ht3.insert("GOODbye");
		ht3.insert("no");
		ht3.insert("yes");

	}

	/**
	 * Testing insert with different adds
	 */
	@Test
	public void testInsert()
	{
		assertEquals(true, ht1.insert("Hello"));
		assertEquals(false, ht1.insert("Hello"));
		assertEquals(true, ht1.insert("HEllo"));
		assertEquals(true, ht1.insert("HELLO"));
		assertEquals(true, ht1.insert("another"));
		assertEquals(false, ht1.insert("another"));

		try {
			ht1.insert(null);
			fail();
		}
		catch (NullPointerException e) {
			// do nothing
		}
	}

	/**
	 * testing lookup with different elements
	 */
	@Test
	public void testLookup()
	{
		assertEquals(true, ht3.lookup("Hello"));
		assertEquals(true, ht3.lookup("Goodbye"));
		assertEquals(false, ht3.lookup("Not"));
		assertEquals(false, ht3.lookup("GOodbye"));

		try {
			ht3.lookup(null);
			fail();
		}
		catch (NullPointerException e) {
			// do nothing
		}
	}

	/**
	 * Testing size making sure it returns the right amount of elts
	 */
	@Test
	public void testSize()
	{
		assertEquals(0, ht1.getSize());
		assertEquals(1, ht2.getSize());
		assertEquals(6, ht3.getSize());
	}

	/**
	 * Testing to see that delete is working correctly
	 */
	@Test
	public void testDelete()
	{
		assertEquals(true, ht2.delete("element"));
		assertEquals(false, ht2.delete("HEY"));
		assertEquals(false, ht3.delete("nowhere"));
		assertEquals(0, ht2.getSize());
		ht2.insert("hello");
		assertEquals(1, ht2.getSize());
		assertEquals(true, ht2.delete("hello"));
		assertEquals(0, ht2.getSize());

		try {
			ht3.delete(null);
			fail();
		}
		catch (NullPointerException e) {
			// do nothing
		}

	}

}
