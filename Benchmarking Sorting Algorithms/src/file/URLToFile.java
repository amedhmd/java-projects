package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class URLToFile {
	
	private static void copyStream(InputStream in, OutputStream out) throws IOException {
		int oneByte = in.read();
		while (oneByte >= 0) { // negative value indicates end-of-stream
			out.write(oneByte);
			oneByte = in.read();
		}
	}
	
	public static void main(String[] args) {
		InputStream in = null;
		OutputStream out = null;
		
		String path = "c://data/url/";
		
		Scanner input = new Scanner(System.in);
		System.out.print("Input FileName : ");
		String fileName = input.next();
		System.out.print("\n");
		
		System.out.print("Input URL : ");
		String urlStr = input.next();
		System.out.print("\n");
		
		File directory = new File(path);
		if(!directory.exists()) {
			directory.mkdirs();
		} 
		
		try {
			URL url = new URL("https://" + urlStr);
			
			in = url.openStream();
			out = new FileOutputStream(path + "/" + fileName + ".txt");
			
			copyStream(in, out);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong type of URL");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception occured while writing a file");
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Exception occured while closing in/out stream");
			}
			
		}
	}
}
