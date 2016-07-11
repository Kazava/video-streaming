package client_v5.network;

public interface Client {
	public void startListening();
	public void stopListening();
	public void sendMessage(CMD cmd);
}
