package client.connect;

import java.io.IOException;

public interface ClientInterface {
	public void setupClient();
    public void configurePort() throws IOException;
    public void configureSelector() throws IOException;
    public void processMessage() throws IOException;
    public void run() throws IOException;
}
