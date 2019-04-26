package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import arguments.Options;
import threading.UDPClient;
import threading.UDPServer;

public class ServerClientIT {

    public static Thread serverThread;
    public static Thread clientThread;

    @Test
    void parseStringsFromArgsTest() {

        UDPClient client = new UDPClient(new Options());
        UDPServer server = new UDPServer(new Options());
        Thread serverThread = new Thread(server);
        serverThread.start();

        clientThread = new Thread(client);
        clientThread.start();


        assertNotNull(server.received != null);
        // assertEquals(serverT, 627);
    }

}
