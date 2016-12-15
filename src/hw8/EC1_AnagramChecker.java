/**
 * Name: <Cy Hynes>
 * PID: <A14179344>
 * Login: <cs12fcf>
 */
package hw8;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Prints all anagrams of the words passed in as arguments
 * 
 * @author Cy Hynes
 * @version 1.0
 * @since Nov 24, 2016
 */
public class EC1_AnagramChecker {

	protected static class HashTable implements IHashTable {

		private int nelems; // Number of element stored in the hash table
		private int tablesize; // current size of the table
		private int expand; // Number of times that the table has been expanded
		private int collision; // Number of collisions since last expansion
		private String statsFileName; // FilePath for the file to write statistics upon every rehash
		private boolean printStats = false; // Boolean to decide whether to write statistics to file or not after
											// rehashing
		private LinkedList[] arr;
		private final double MAXLOAD = .66666667;
		private int longest = 0;
		public boolean print;

		/**
		 * Constructor for hash table
		 * 
		 * @param Initial
		 *            size of the hash table
		 */
		public HashTable(int size) {

			arr = new LinkedList[size];
			tablesize = size;
		}

		/**
		 * Constructor for hash table
		 * 
		 * @param Initial
		 *            size of the hash table
		 * @param File
		 *            path to write statistics
		 */
		public HashTable(int size, String fileName) {

			// Set printStats to true and statsFileName to fileName
			arr = new LinkedList[size];
			printStats = true;
			statsFileName = fileName;
			tablesize = size;
		}

		/**
		 * Insert function to add elements into the hash table
		 * 
		 * @param value
		 * @return true if the element was inserted and false otherwise.
		 * @throws NullPointerException
		 *             if value is null
		 */
		@Override
		public boolean insert(String value) throws NullPointerException
		{
			if (value == null) {
				throw new NullPointerException();
			}
			String svalue = sortstring(value);
			if (lookup(value)) {
				return false;
			}
			int idx = hashFunc(svalue);
			svalue = svalue + "," + value;
			if (arr[idx] == null) { // case where bucket is empty
				arr[idx] = new LinkedList();
				arr[idx].add(svalue);
				nelems++;
				if (checkLoad() > MAXLOAD) {
					rehash();
				}

				return true;
			}
			else { // collision case
				arr[idx].add(svalue);
				if (arr[idx].size() > longest) { // keeping track of the longest chain
					longest = arr[idx].size();
				}
				collision++;
				nelems++;
				if (checkLoad() > MAXLOAD) {
					rehash();
				}
				return true;
			}

		}

		/**
		 * delete method for the hashtable.
		 * 
		 * @param value
		 * @return true if the value was deleted and return false if the value was not found
		 * @throws NullPointerException
		 *             if the value passed in is null
		 */
		@Override
		public boolean delete(String value) throws NullPointerException
		{
			if (value == null) {
				throw new NullPointerException();
			}
			if (lookup(value)) {
				int idx = hashFunc(value);
				arr[idx].remove(value);
				nelems--;
				return true;
			}
			else {
				return false;
			}

		}

		/**
		 * Lookup method to see if an element exists in the hashtable
		 * 
		 * @param value
		 * @return true if the element was found and false otherwise
		 * @throws NullPointerException
		 *             if the value passed in is null
		 */
		@Override
		public boolean lookup(String value) throws NullPointerException
		{
			if (value == null) {
				throw new NullPointerException();
			}
			String svalue = sortstring(value);
			int idx = hashFunc(svalue);
			svalue = svalue + "," + value;

			if (arr[idx] != null && arr[idx].contains(svalue)) {
				if (print) {
					for (int i = 0; i < arr[idx].size(); i++) {
						String s = (String) arr[idx].get(i);
						String[] sarr = s.split(",");
						if (sarr[0].equals(sortstring(value))) {
							System.out.println(sarr[1]);
						}

					}
				}
				return true;
			}
			else
				return false;
		}

		/**
		 * Method to print the hashtable to stdout.
		 */
		@Override
		public void printTable()
		{
			String l = "";
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					for (int j = 0; j < arr[i].size(); j++) {
						if (!l.equals("")) {
							l += ", " + arr[i].get(j);
						}
						else {
							l += arr[i].get(j);
						}
					}
					System.out.println(i + ":" + l);
					l = "";
				}

				else {
					System.out.println(i + ":");
				}
			}
		}

		/**
		 * method to get the number of elements currently in the hashtable.
		 * 
		 * @return nelems;
		 */
		@Override
		public int getSize()
		{
			return nelems;
		}

		private int hashFunc(String s) // hashfunc3 from lecture slides.
		{
			int hashVal = 0;
			for (int j = 0; j < s.length(); j++) {
				int letter = s.charAt(j);// - 96;
				hashVal = (hashVal * 27 + letter) % tablesize;

			}
			return hashVal;
		}

		private void rehash() // rehash method when load factor is too high
		{
			expand++;
			printStatistics();
			LinkedList[] temp = new LinkedList[tablesize * 2]; // temp new hashtable
			tablesize = tablesize * 2;
			int sizeold = getSize();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					for (int j = 0; j < arr[i].size(); j++) {
						String key = (String) arr[i].get(j);
						String[] keys = key.split(",");
						int idx = hashFunc(keys[0]);
						if (temp[idx] == null) {
							temp[idx] = new LinkedList();
						}
						temp[idx].add((String) arr[i].get(j));
					}

				}
			}
			arr = temp;
		}

		private double checkLoad() /// helper method to check the load of the HT at anytime
		{
			return (double) nelems / tablesize;
		}

		private void printStatistics() // prints the stats of the hashtable to a file
		{
			if (printStats) {
				DecimalFormat df = new DecimalFormat("#.##");
				PrintStream out = null;
				try {
					out = new PrintStream(new BufferedOutputStream(new FileOutputStream(statsFileName, true)));
					out.println(expand + " resizes, " + "load factor " + df.format(checkLoad()) + ", " + collision
							+ " collisions, " + longest + " longest chain");
					out.close();

				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				collision = 0;
				longest = 0;
			}
			else {
				return;
			}
		}

		private String sortstring(String s)
		{
			char[] chars;
			chars = s.toCharArray();
			Arrays.sort(chars);
			s = new String(chars);
			return s;

		}
		//extra methods to help with printing from lookup()
		public boolean checkPrint()
		{
			if (print) {
				return true;
			}
			else {
				return false;
			}
		}
		//extra methods to help with printing from lookup()
		public void setPrint(boolean f)
		{
			print = f;
		}

	}

	public static void main(String[] args)
	{
		File file1 = new File(args[0]);
		Scanner scan = null;
		HashTable ht = new HashTable(7);

		try { //scan in the dictionaries
			scan = new Scanner(file1);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNextLine()) {
			ht.insert(scan.nextLine());
		}
		
		
		for (int j = 1; j < args.length; j++) {
			System.out.println("Anagram(s) of " + args[j] + ":");
			ht.setPrint(true);
			if (!(ht.lookup(args[j]))){ //lookup prints based on boolean of print
				System.out.println("No anagrams found");
				ht.setPrint(false);
				continue;
			}

		}


	}

}
