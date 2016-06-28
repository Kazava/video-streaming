package Server_v3;

import java.io.IOException;

// Für Sublime
public interface ServerInterface {

    public void setupServer();
    public void configurePort() throws IOException;
    public void configureSelector() throws IOException;
    public void processMessage() throws IOException;
}
