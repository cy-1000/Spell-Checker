/**
 * Name: <Cy Hynes>
 * PID: <A14179344>
 * Login: <cs12fcf>
 */
package hw8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author cy
 *
 */
public class main {

	public static void main (String [] args){
		HashTable ht = new HashTable(10 , "/Users/cy/Desktop/OneDrive/CSE12/HW8/output.txt");
		
		
		
		File file = new File("/Users/cy/Desktop/OneDrive/CSE12/HW8/dictionary");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println(ht.hashCRC("Hello"));
		System.out.println(ht.hashCRC("Goodbye"));
		System.out.println(ht.hashCRC("GOODBYE"));
		System.out.println(ht.hashCRC("GOODBYE"));
		System.out.println(ht.hashCRC("GOODBye"));*/
		//hashCRC needs to be public for this to work....
		
		while (scan.hasNext()){
			ht.insert(scan.next());
		}
		
		ht.printTable();
		System.out.println(ht.getSize());
		
		
		
		
		
	}
}
