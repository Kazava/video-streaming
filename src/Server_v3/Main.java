package Server_v3;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	public static void main(String [] args)
	{
		ServerInterface vs = new VideoServer(8001, 8002);
		vs.setupServer();
		for(;;) {
			try {
				vs.processMessage();
				
			} catch (java.io.IOException e) {
		          Logger l = Logger.getLogger(Main.class.getName());
		          l.log(Level.WARNING, "IOException in Main", e);
		    } catch (Throwable t) {
		          Logger l = Logger.getLogger(Main.class.getName());
		          l.log(Level.SEVERE, "FATAL error in Main", t);
		          System.exit(1);
		    }
		}
	}
}
