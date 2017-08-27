// h15h4m

import java.io.*;

public class Solve {
	public static void main( String args[] ) {

		
		if (args.length == 0)
		{
			System.out.println("Usage: java Solve evil.py");
			System.exit(1);
		}


		
		File f = new File(args[0]);
		long originalfileSize = f.length() - 1;


		// EDIT THIS
		long targetCksum = 2805500100L;
			
		
		long currentCksum = 0L;	


		byte [] originalfileBytes = new byte[ (int)originalfileSize - 1 ];
		byte [] evilfileBytes  = new byte[ (int) originalfileSize  + 4 ];


		try 
		{
			FileInputStream readingHandler = new FileInputStream(args[0]);
			readingHandler.read(originalfileBytes);
			readingHandler.close();
		} 
		catch (IOException e) { System.out.println(e); }

		System.out.println(args[0] + " was read!");
		System.out.println("Copying the original file byte array to an evil one.......");

		int i;
		for (i = 0 ; i  < originalfileBytes.length  ;  i++)
		{
			evilfileBytes[i] = originalfileBytes[i];
		}
		
		int evilfileSize = evilfileBytes.length;
		

		System.out.println("Brute forcing........");

		// from integers starting value till the ending value.
		for (i = -2147483648 ; i < 2147483647 ; i++ ) 
		{
			
			// brute forcing the last 4 bytes until we find the correct combo.
			evilfileBytes[evilfileSize - 4] = (byte) (i >>> 24 &  0xff);
			evilfileBytes[evilfileSize - 3] = (byte) (i >>> 16 &  0xff);
			evilfileBytes[evilfileSize - 2] = (byte) (i >>> 8  &  0xff);
			evilfileBytes[evilfileSize - 1] = (byte) (i >>> 0  &  0xff);

			
			Cksum cksumHandler = new Cksum();
			cksumHandler.update(evilfileBytes);
			currentCksum = cksumHandler.getValue();

			if (currentCksum == targetCksum) 
			{
				try 
				{
					FileOutputStream writingHandler = new FileOutputStream("pwn.py");
					writingHandler.write(evilfileBytes);
					writingHandler.close();
				}
	 
				catch (IOException e) 
				{
					System.out.println(e);
				}

				System.out.println("Success! Check pwn.py!\n");
				break;
			}
		}

	}
}
