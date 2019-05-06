/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;

import com.esotericsoftware.kryo.*;
import com.esotericsoftware.kryonet.*;

/**
 *
 * @author edgar
 */
public class KryoServer {
    // Conenction info
    int serverPort = 55223;
    
    // Kryonet Server variables
    Server server;
    KryoServerListener kryoServerListener;
    
    public KryoServer() throws Exception {
        // Create server
        server = new Server();
        
        // Add listeners
        kryoServerListener = new KryoServerListener();
        server.addListener(kryoServerListener);
        
        // Bind to the port
        server.bind(serverPort);
        
        // Register all the packets
        registerPackets();
        
        // Connect to DB and create VARIATIONS table
        ActionsDB.connect();
        ActionsDB.createVariationsTable();
        
        // Start the server
        server.start();
        System.out.println("MAIN SERVER HAS BEEN STARTED");
    }
    
    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(Packet.Packet01Message.class);
        kryo.register(sem09_kryonet.Variation.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.Date.class);
    }
}
