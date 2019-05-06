/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;

/**
 *
 * @author edgar
 */
public class MainServer {
    KryoServer kryoServer;
    
    public MainServer() throws Exception {
        kryoServer = new KryoServer();
    }
    
    public static void main(String[] args) throws Exception {
        new MainServer();
    }
}
