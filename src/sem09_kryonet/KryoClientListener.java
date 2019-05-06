/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;

import com.esotericsoftware.kryo.*;
import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
/**
 *
 * @author edgar
 */
public class KryoClientListener extends Listener{
    
    private Client client;
    
    public void init(Client client) {
        this.client = client;
    }
    
    public void connected() {
        System.out.println("You have been connected to server!");
        
        // Create message and send to server
        Packet.Packet01Message firstMessage = new Packet.Packet01Message();
        firstMessage.message = "Hello, server!";
        
        client.sendTCP(firstMessage);
    }
    
    public void disconnected() {
        System.out.println("You have been disconnected from server");
    }
    
    @Override
    public void received(Connection c, Object o) {
        if (o instanceof Packet.Packet01Message) {
            Packet.Packet01Message p = (Packet.Packet01Message) o;
            System.out.println("[SERVER] Received message from the host:" + p.message);
        }
    }
}
