package server_v2;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A more robust daytime service, that handles TCP and UDP connections and
 * provides exception handling and error logging.
 */
public class Server_v2 {
  public static void main(String args[]) {
    try { // Handle startup exceptions at the end of this block
      // Get an encoder for converting strings to bytes
      CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();

      // Allow an alternative port for testing with non-root accounts
      int port = 8001; // RFC867 specifies this port.
      if (args.length > 0)
        port = Integer.parseInt(args[0]);

      // The port we'll listen on
      SocketAddress localport = new InetSocketAddress(port);

      // Create and bind a tcp channel to listen for connections on.
      ServerSocketChannel tcpserver = ServerSocketChannel.open();
      tcpserver.socket().bind(localport);

      // Also create and bind a DatagramChannel to listen on.
      DatagramChannel udpserver = DatagramChannel.open();
      udpserver.socket().bind(localport);

      // Specify non-blocking mode for both channels, since our
      // Selector object will be doing the blocking for us.
      tcpserver.configureBlocking(false);
      udpserver.configureBlocking(false);

      // The Selector object is what allows us to block while waiting
      // for activity on either of the two channels.
      Selector selector = Selector.open();

      // Register the channels with the selector, and specify what
      // conditions (a connection ready to accept, a datagram ready
      // to read) we'd like the Selector to wake up for.
      // These methods return SelectionKey objects, which we don't
      // need to retain in this example.
      tcpserver.register(selector, SelectionKey.OP_ACCEPT);
      udpserver.register(selector, SelectionKey.OP_READ);

      // This is an empty byte buffer to receive emtpy datagrams with.
      // If a datagram overflows the receive buffer size, the extra bytes
      // are automatically discarded, so we don't have to worry about
      // buffer overflow attacks here.
      ByteBuffer receiveBuffer = ByteBuffer.allocate(0);

      // Now loop forever, processing client connections
      for (;;) {
        try { // Handle per-connection problems below
          // Wait for a client to connect
          selector.select();

          // If we get here, a client has probably connected, so
          // put our response into a ByteBuffer.
          String date = new java.util.Date().toString() + "\r\n";
          ByteBuffer response = encoder.encode(CharBuffer.wrap(date));

          // Get the SelectionKey objects for the channels that have
          // activity on them. These are the keys returned by the
          // register() methods above. They are returned in a
          // java.util.Set.
          Set keys = selector.selectedKeys();

          // Iterate through the Set of keys.
          for (Iterator i = keys.iterator(); i.hasNext();) {
            // Get a key from the set, and remove it from the set
            SelectionKey key = (SelectionKey) i.next();
            i.remove();

            // Get the channel associated with the key
            Channel c = (Channel) key.channel();

            // Now test the key and the channel to find out
            // whether something happend on the TCP or UDP channel
            if (key.isAcceptable() && c == tcpserver) {
              // A client has attempted to connect via TCP.
              // Accept the connection now.
              SocketChannel client = tcpserver.accept();
              // If we accepted the connection successfully,
              // the send our respone back to the client.
              if (client != null) {
                client.write(response); // send respone
                client.close(); // close connection
                System.out.println("Sent TCP");
              }
            } else if (key.isReadable() && c == udpserver) {
              // A UDP datagram is waiting. Receive it now,
              // noting the address it was sent from.
              SocketAddress clientAddress = udpserver.receive(receiveBuffer);
              // If we got the datagram successfully, send
              // the date and time in a response packet.
              if (clientAddress != null) {
                udpserver.send(response, clientAddress);
                System.out.println("Sent UDP");
              }
            }
          }
        } catch (java.io.IOException e) {
          // This is a (hopefully transient) problem with a single
          // connection: we log the error, but continue running.
          // We use our classname for the logger so that a sysadmin
          // can configure logging for this server independently
          // of other programs.
          Logger l = Logger.getLogger(Server_v2.class.getName());
          l.log(Level.WARNING, "IOException in DaytimeServer", e);
        } catch (Throwable t) {
          // If anything else goes wrong (out of memory, for example)
          // then log the problem and exit.
          Logger l = Logger.getLogger(Server_v2.class.getName());
          l.log(Level.SEVERE, "FATAL error in DaytimeServer", t);
          System.exit(1);
        }
      }
    } catch (Exception e) {
      // This is a startup error: there is no need to log it;
      // just print a message and exit
      System.err.println(e);
      System.exit(1);
    }
  }
}