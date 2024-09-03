package webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The main() program in this class is designed to read requests from
 * a Web browser and display the requests on standard output.  The
 * program sets up a listener on port 50505.  It can be contacted
 * by a Web browser running on the same machine using a URL of the
 * form  http://localhost:505050/path/to/resource.html  This method
 * does not return any data to the web browser.  It simply reads the
 * request, writes it to standard output, and then closes the connection.
 * The program continues to run, and the server continues to listen
 * for new connections, until the program is terminated (by clicking the
 * red "stop" square in Eclipse or by Control-C on the command line).
 */
public class test {
	
	/**
	 * The server listens on this port.  Note that the port number must
	 * be greater than 1024 and lest than 65535.
	 */
	private final static int LISTENING_PORT = 50505;
	
	private final static String rootDirectory = "/resource";
	private static String pathToFile;
	
	private static OutputStream os;
	
	private static class ConnectionThread extends Thread {
        Socket connection;
        ConnectionThread(Socket connection) {
           this.connection = connection;
        }
        public void run() {
           handleConnection(connection);
        }
    }
	
	/**
	 * Main program opens a server socket and listens for connection
	 * requests.  It calls the handleConnection() method to respond
	 * to connection requests.  The program runs in an infinite loop,
	 * unless an error occurs.
	 * @param args ignored
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(LISTENING_PORT);
		}
		catch (Exception e) {
			System.out.println("Failed to create listening socket.");
			return;
		}
		System.out.println("Listening on port " + LISTENING_PORT);
		try {
			while (true) {
				Socket connection = serverSocket.accept();
				System.out.println("\nConnection from " 
						+ connection.getRemoteSocketAddress());
				ConnectionThread thread = new ConnectionThread(connection);
				thread.start();
			}
		}
		catch (Exception e) {
			System.out.println("Server socket shut down unexpectedly!");
			System.out.println("Error: " + e);
			System.out.println("Exiting.");
		}
	}

	/**
	 * Handle commuincation with one client connection.  This method reads
	 * lines of text from the client and prints them to standard output.
	 * It continues to read until the client closes the connection or
	 * until an error occurs or until a blank line is read.  In a connection
	 * from a Web browser, the first blank line marks the end of the request.
	 * This method can run indefinitely,  waiting for the client to send a
	 * blank line.
	 * NOTE:  This method does not throw any exceptions.  Exceptions are
	 * caught and handled in the method, so that they will not shut down
	 * the server.
	 * @param connection the connected socket that will be used to
	 *    communicate with the client.
	 */
	private static void handleConnection(Socket connection) {
		String httpMethod = null;
		String header = null;
	
		try {
			Scanner in = new Scanner(connection.getInputStream());
			os = connection.getOutputStream();
			while (true) {
				if ( ! in.hasNextLine() )
					break;
				String line = in.nextLine();
				if (line.trim().length() == 0)
					break;
				System.out.println("   " + line);
				
				if(line.contains("GET")) {
					 httpMethod = line;
				}
			}
			if(httpMethod != null) {
				if(httpMethod.contains("path")) {
					pathToFile = httpMethod.substring(httpMethod.lastIndexOf("GET "), httpMethod.indexOf("HTTP/1.1"));
					pathToFile = pathToFile.substring(pathToFile.lastIndexOf("/"));
					
					File file = new File(System.getProperty("user.dir") + rootDirectory + pathToFile);
					if(!isFile(file)) return;
					getHeader(os, "file", file);
					sendFile(file, os);
			        System.out.println("Sending " + file.getName() + "(" + file.length() + " bytes)");
			        System.out.println("Done.");
				} else {
					getHeader(os, "not found", null);
					File file = new File(System.getProperty("user.dir") + rootDirectory + "/error404.html");
					sendFile(file, os);
				}
			} else {
				 getHeader(os, "not implemented", null);
			}
			
		}
		catch (Exception e) {
			System.out.println("Error while communicating with client: " + e);
		}
		finally {  // make SURE connection is closed before returning!
			try {
				connection.close();
				os.close();
			}
			catch (Exception e) {
			}
			System.out.println("Connection closed.");
		}
	}
	
	public static boolean isFile(File file) {
		 boolean check = false;
		 if(!file.exists()) check = false;
		 else check = true;
		 if(file.isDirectory()) check = false;
		 else check = true;
		 if(file.canRead()) check = true;
		 else check = false;
		 if(file.length() > 0) check = true;
		 else check = false;
		 return check;
	}
	
	private static String getMimeType(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos < 0)  // no file extension in name
            return "x-application/x-unknown";
        String ext = fileName.substring(pos+1).toLowerCase().trim();
        if (ext.equals("txt")) return "text/plain";
        else if (ext.equals("html")) return "text/html";
        else if (ext.equals("htm")) return "text/html";
        else if (ext.equals("css")) return "text/css";
        else if (ext.equals("js")) return "text/javascript";
        else if (ext.equals("java")) return "text/x-java";
        else if (ext.equals("jpeg")) return "image/jpeg";
        else if (ext.equals("jpg")) return "image/jpeg";
        else if (ext.equals("png")) return "image/png";
        else if (ext.equals("gif")) return "image/gif"; 
        else if (ext.equals("ico")) return "image/x-icon";
        else if (ext.equals("class")) return "application/java-vm";
        else if (ext.equals("jar")) return "application/java-archive";
        else if (ext.equals("zip")) return "application/zip";
        else if (ext.equals("xml")) return "application/xml";
        else if (ext.equals("xhtml")) return"application/xhtml+xml";
        else return "x-application/x-unknown";
           // Note:  x-application/x-unknown  is something made up;
           // it will probably make the browser offer to save the file.
    }

	private static void sendFile(File file, OutputStream socketOut) throws IOException {
	    InputStream in = new BufferedInputStream(new FileInputStream(file));
	    OutputStream out = new BufferedOutputStream(socketOut);
	    while (true) {
	      int x = in.read(); // read one byte from file
	      if (x < 0)
	         break; // end of file reached
	      out.write(x);  // write the byte to the socket
	    }
	    out.flush();
	}
	
	private static void getHeader(OutputStream socketOut, String status, File file) throws IOException {
		PrintStream ps = new PrintStream(socketOut); 
		StringBuffer header = new StringBuffer();
		String nextLine = "\r\n";
		String response = "HTTP/1.1 ";
        String connectionHeader = "Connection: close" +  nextLine;
        String contentLength = "Content-Length: ";
        String contentType = "Content-Type: ";
        
        if(status.equals("file") && file != null) {
	        response += "200 OK" +  nextLine;
	        contentLength += file.length() +  nextLine;
	        contentType += getMimeType(file.getName()) +  nextLine;
	        header.append(response).append(contentLength).append(contentType);
        } else if(status.equals("not found")) {
        	response += "404 NOT FOUND" +  nextLine;
        	contentType += getMimeType("error404.html") +  nextLine;
        	header.append(response).append(contentType);
        } else if(status.equals("not implemented")) {
        	response += "501 NOT IMPLEMENTED" +  nextLine;
        	contentType += getMimeType("error501.html") +  nextLine;
        	header.append(response).append(contentType);
        }
        
        ps.println(header.toString());
        ps.flush();
	}
}
