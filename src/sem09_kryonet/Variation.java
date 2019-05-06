/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author edgar
 */
public class Variation {
    private int variationNr;
    private int correctNumbers = 0;
    private ArrayList<Integer> selectedNumbers = new ArrayList<Integer>();
    private String clientEmail;
    private Date dateTime;
    
    public Variation() {}
    
    public Variation(int variationNr, int correctNumbers, ArrayList<Integer> selectedNumbers, String clientEmail) {
        this.variationNr = variationNr;
        this.correctNumbers = correctNumbers;
        this.selectedNumbers = selectedNumbers;
        this.clientEmail = clientEmail;
        this.dateTime = new Date();
    }

    public int getVariationNr() {
        return variationNr;
    }

    public int getCorrectNumbers() {
        return correctNumbers;
    }

    public ArrayList<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setVariationNr(int variationNr) {
        this.variationNr = variationNr;
    }

    public void setCorrectNumbers(int correctNumbers) {
        this.correctNumbers = correctNumbers;
    }

    public void setSelectedNumbers(ArrayList<Integer> selectedNumbers) {
        this.selectedNumbers = selectedNumbers;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        return "Variation{" + 
                "\nvariationNr=" + variationNr + 
                "\ncorrectNumbers=" + correctNumbers + 
                "\nselectedNumbers=" + selectedNumbers + 
                "\nclientEmail=" + clientEmail + 
                "\ndate=" + dateTime +
                "\n}";
    }
}
