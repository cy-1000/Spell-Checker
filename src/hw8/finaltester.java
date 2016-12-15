/**
 * Name: <Cy Hynes>
 * PID: <A14179344>
 * Login: <cs12fcf>
 */
package hw8;

/**
 * TODO ADD CLASS DESCRIPTION
 * 
 * @author Cy Hynes
 * @version 1.0
 * @since Dec 7, 2016
 */
public class finaltester {

	int i = 5;

	public finaltester() {
		i = 10;
	}

	public int getI()
	{
		return i;
	}

	protected class innerclass {

		public innerclass() {
			i = 15;
		}

		public int getI()
		{
			return i;
		}

	}

	public static void main(String[] args)
	{
		finaltester test = new finaltester();
		System.out.println("The output of x in finaltester is  : " + test.getI());
		finaltester.innerclass ic = test.new innerclass();
		System.out.println("the output of x in innerclass is : " + ic.getI());
		
	}
}
