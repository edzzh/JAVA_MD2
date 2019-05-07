/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author edgar
 */
public class ClientApp {
    KryoClient kryoClient;
    ArrayList<Variation> variations;
    
    private int variationCount = 0;
    static Scanner input = new Scanner(System.in).useDelimiter("\\n");
    private final String userInput;
    
    // Temporary Variables
    private int luckyNumber;
    ArrayList<Integer> tmpNumbersArray;
    
    public ClientApp() throws IOException {
        variations = new ArrayList<Variation>();
        
        // Save and check variation count input from User
        do {
            System.out.println("How many variants you want to register?");
            while(!input.hasNextInt()) {
                System.out.println(input.next() + " - is not a number");
            }
            variationCount = input.nextInt();
        } while ((variationCount > 5) || (variationCount < 1));
        
        System.out.println("Do you want if all the variations is generated randomly? (YES/NO)");
        userInput = input.next();
        
        switch (userInput) {
            case "YES":
                fillTheVariationsByCount(0);
                break;
            case "NO":
                fillTheVariationsByHand();
                break;
            default:
                System.out.println("Given user input is not supported");
                break;
        }
        
        kryoClient = new KryoClient();
        
        for (Variation v : variations) {
            kryoClient.client.sendTCP(v);
        }
    }
    
    private void fillTheVariationsByHand() {
        int cardsToFill;
        do {
            System.out.println("How many variations you want to be filled?");
            while(!input.hasNextInt()) {
                System.out.println(input.next() + " - is not a number");
            }
            cardsToFill = input.nextInt();
        } while ((cardsToFill < 1) && (cardsToFill > variationCount));
        
        fillTheVariationsByCount(cardsToFill);
    }
    
    private void fillTheVariationsByCount(int n) {
        String userEmail;
        
        System.out.println("Insert your email:");
        userEmail = input.next();
        
        if (n != 0) {
            int variationNr = 0;
            
            
            // Add variation cards by user input
            for (int i = 0; i < n; i++) {
                tmpNumbersArray = new ArrayList<Integer>();
                System.out.println("User is filling varition card - Nr." + i + 1);

                for (int j = 0; j < 5; j++) {
                    do {
                        System.out.println("Enter your number " + (j + 1) + " (1-35):");
                        while(!input.hasNextInt()) {
                            System.out.println(input.next() + " - is not a number");
                        }
                        luckyNumber = input.nextInt();
                    } while((luckyNumber > 35) && (luckyNumber < 1));
                    tmpNumbersArray.add(luckyNumber);
                }
                
                // Incriment the variation number to follow the card count
                variationNr++;
                
                Variation variation = new Variation(variationNr, 0, tmpNumbersArray, userEmail);
                variations.add(variation);
            }
            
                
            // If there is any variation cards left, then add them randomly generated
            for (int k = 0; k < variationCount - n; k++) {
                tmpNumbersArray = new ArrayList<Integer>();
                
                for (int j = 0; j < 5; j++) {
                    addLuckyNumber();
                }
                
                //Incriment the variation number to follow the card count
                variationNr++;
                
                Variation variation = new Variation(variationNr, 0, tmpNumbersArray, userEmail);
                variations.add(variation);
            }
        } else {
            System.out.println("Generate random variations!");
            for (int i = 0; i < variationCount - n; i++) {
                tmpNumbersArray = new ArrayList<Integer>();
                for (int j = 0; j < 5; j++) {
                    addLuckyNumber();
                }
                
                Variation variation = new Variation(i + 1, 0, tmpNumbersArray, userEmail);
                variations.add(variation);
            }
        }
    }
    
    private void addLuckyNumber() {
        luckyNumber = 1 + (int)(Math.random() * (35-1) + 1);
        
        if (!tmpNumbersArray.contains(luckyNumber)) {   
            tmpNumbersArray.add(luckyNumber);
        } else {
            addLuckyNumber();
        }
    }
    
    public static void main(String[] args) throws IOException {
        new ClientApp();
    }
}
