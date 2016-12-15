/**
 * Name: <Cy Hynes>
 * PID: <A14179344>
 * Login: <cs12fcf>
 */
package hw8;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Takes in arguments from the command line and prints stats to how many lines each of the files has in common with each
 * of the other files.
 * 
 * @author Cy Hynes
 * @version 1.0
 * @since Nov 24, 2016
 */

public class LineCounter {

	// Method to print the filename to console

	public static void printFileName(String filename)
	{
		System.out.println("\n" + filename + ":");
	}

	// method to print the statistics to console
	public static void printStatistics(String compareFileName, int percentage)
	{
		System.out.println(percentage + "% of lines are also in " + compareFileName);
	}

	public static void main(String[] args)
	{
		int sameline = 0;
		double linepercent;
		if (args.length < 2) {
			System.err.println("Invalid number of arguments passed");
			return;
		}

		int numArgs = args.length;

		// Create a hash table for every file
		HashTable[] tableList = new HashTable[numArgs];
		File[] filelist = new File[numArgs];
		int[] linelist = new int[numArgs];
		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.DOWN);

		// Preprocessing: Read every file and create a HashTable
		for (int i = 0; i < numArgs; i++) { 

			tableList[i] = new HashTable(5); // put hashtable in tablelist
			filelist[i] = new File(args[i]); // create a list of file objects from passed in args
			Scanner scan = null;
			try { // scan in the file
				scan = new Scanner(filelist[i]);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (scan.hasNextLine()) { // scan each line into the hash table
				tableList[i].insert(scan.nextLine());
				linelist[i]++;
			}

		}

		// Find similarities across files

		for (int i = 0; i < numArgs; i++) {
			Scanner scan = null;
			try { // scan in the file
				scan = new Scanner(filelist[i]); //scanner looks at each file object 
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			printFileName(args[i]);
			for (int j = 0; j < numArgs; j++) {
				if (j == i) { //no need to compare a file to itself
					continue;
				}
				while (scan.hasNextLine()) {
					String nextLine = scan.nextLine();
					if (tableList[j].lookup(nextLine)) { 
						sameline++; //number of lines shared
					}

				}
				linepercent = (double) sameline / linelist[i] * 100;
				printStatistics(args[j], (int) linepercent);
				sameline = 0;
				try {
					scan = new Scanner(filelist[i]); //reset the scanner
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
