package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
            httpServer.createContext("/", new MyHttpHandler());
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static class MyHttpHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange t) throws IOException {
            String root = "/res";
            URI uri = t.getRequestURI();
            log(uri.getPath());
            
            File file = checkURI(uri.getPath()); 
            if (!file.isFile()) {
            	// Object does not exist or is not a file: reject with 404 error.
            	String response = "404 (Not Found)\n";
            	t.sendResponseHeaders(404,response.length());
            	OutputStream os = t.getResponseBody();
            	os.write(response.getBytes());
            	os.close();
            } else {
            	// Object exists and is a file: accept with response code 200.
            	t.sendResponseHeaders(200, 0);
            	OutputStream os = t.getResponseBody();
            	FileInputStream fs = new FileInputStream(file);
            	final byte[] buffer = new byte[0x10000];
            	int count = 0;
            	while ((count = fs.read(buffer)) >= 0) {
            		os.write(buffer,0,count);
            	}
            	fs.close();
            	os.close();
          	}
        }
        
        private File checkURI(String path) throws IOException {
        	File file = null;
        	String pathToFile = "";
        	
        	switch(path) {
        		case "/" :
        			pathToFile = "res/index.html";
        			break;
        		case "/test":
        			pathToFile = "res/test.html";
        			break;
        		default:
        			break;
        	}
        	file = new File(pathToFile).getCanonicalFile();
        	return file;
        }
    }
    
    
    // Because "System.out.println" is too long to type...
    public static void log(String str){
    	System.out.println(str);
    }
    
}
