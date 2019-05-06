/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
/**
 *
 * @author edgar
 */
public class KryoClient {
    // Connection variables
    int portSocket = 55223;
    String ipAddress = "127.0.0.1";
    
    // Kryonet instances
    public Client client;
    private KryoClientListener kryoClientListener;
    
    public KryoClient() throws IOException{
        // Create client
        client = new Client();
        
        // Add client instance to kryo client listener
        kryoClientListener = new KryoClientListener();
        kryoClientListener.init(client);
        
        // Add Kryo client listener to client instance
        client.addListener(kryoClientListener);
        
        //registering all packets
        registerPackets();
        
        //start client
        client.start();
        
        //connect to server
        client.connect(5000, ipAddress, portSocket);
    }
    
    private void registerPackets() {
        Kryo kryo = client.getKryo();
        kryo.register(Packet.Packet01Message.class);
        kryo.register(sem09_kryonet.Variation.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.Date.class);
    }
}
