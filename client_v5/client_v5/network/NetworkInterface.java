package client_v5.network;

import java.io.IOException;
import java.nio.channels.Channel;

public interface NetworkInterface {
	public void setupNetwork() throws IOException;
	public Channel choosingConnection();
}
