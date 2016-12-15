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
 * Simple spellcheck using a HashTable
 * 
 * @author Cy Hynes
 * @version 1.0
 * @since Nov 24, 2016
 */
public class EC2_SpellChecker {

	public static final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static void main(String[] args)
	{
		File file = new File(args[0]);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HashTable dict = new HashTable(7);
		while (scan.hasNext()) {
			dict.insert(scan.nextLine());
		}
		String original = "";
		String check = "";
		int counter = 0;
		for (int i = 1; i < args.length; i++) {
			if (!dict.lookup(args[i])) {
				System.out.print(args[i] + "? did you mean: ");
				char[] sarr = args[i].toCharArray(); // turn to array of chars
				for (int j = 0; j < sarr.length; j++) {
					for (int k = 0; k < alphabet.length; k++) {
						original = String.valueOf(sarr);

						sarr[j] = alphabet[k]; // try all combos with the kth element of the char array with the
												// alphabet array
						
						
						check = String.valueOf(sarr);
						if (dict.lookup(check)) {
							if (counter == 0) {
								System.out.print(check);
								counter++;
							}
							else {
								System.out.print(", " + check);
							}
						}
						sarr = original.toCharArray();
					}
				}
				System.out.println("");

			}
		}

	}
}
