package server.connect;

import java.io.IOException;

public interface ServerInterface {

    public void setupServer();
    public void configurePort() throws IOException;
    public void configureSelector() throws IOException;
    public void processMessage() throws IOException;
    public void shutdown();
}
