/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;
import com.esotericsoftware.kryonet.*;
import java.util.ArrayList;

/**
 *
 * @author edgar
 */
public class KryoServerListener extends Listener{
    private final ArrayList<Integer> winningNumbers;
    private int luckyNumber;
    
    public KryoServerListener() {
        winningNumbers = new ArrayList<Integer>();
        winningNumbers.clear();
        
        for (int i = 0; i < 5; i++) {
            addLuckyNumber();
        }
    }
    
    private void addLuckyNumber() {
        luckyNumber = 1 + (int)(Math.random() * (35-1) + 1);
        
        if (!winningNumbers.contains(luckyNumber)) {   
            winningNumbers.add(luckyNumber);
        } else {
            addLuckyNumber();
        }
    }
    
    private boolean compareVariationNumbers(int num) {
        boolean temp = false;
        
        for (int i = 0; i < 5; i++) {
            if (winningNumbers.get(i) == num) {
                temp = true;
            }
        }
        
        return temp;
    }
    
    @Override
    public void connected(Connection c) {
        System.out.println("Received a connection from: " + c.getRemoteAddressTCP().getHostString());
        System.out.println("Winning nummbers: ");
        for (int i = 0; i < 5; i++) {
            System.out.println(winningNumbers.get(i).toString());
        }
    }
    
    public void disconnected() {
        System.out.println("A client has disconnected!");
    }
    
    @Override
    public void received(Connection c, Object o) {
        // Check if client is sending message to server
        if (o instanceof Packet.Packet01Message) {
            Packet.Packet01Message p = (Packet.Packet01Message) o;
            System.out.println("[CLIENT]: Received message - " + p.message);
        }
        
        // Client nummber check against server provided number list
        if (o instanceof Variation) {
            Variation v = (Variation) o;
            ArrayList<Integer> selectedNumbers = v.getSelectedNumbers();
            
            for (int i = 0; i < 5; i++) {
                if (compareVariationNumbers(selectedNumbers.get(i))) {
                    v.setCorrectNumbers(v.getCorrectNumbers() + 1);
                }
            }
            
            // Save the variation in the database
            ActionsDB.insertInVariationsTable(v);
            
            System.out.println("Variation number - " + v.getVariationNr() + ", correct numbers: " + v.getCorrectNumbers());
            
            // Check if any of the variations has 5 winning numbers
            if (v.getCorrectNumbers() == 5) {
                System.out.println("\nWE HAVE A WINNER! :D");
//                ActionsDB.selectData(v.getDateTime());
            } else {
                System.out.println("\nBETTER NEXT TIME. :(");
//                ActionsDB.selectData(v.getDateTime());
            }
        }
    }
}
