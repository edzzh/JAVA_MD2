/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem09_kryonet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author edgar
 */
public class ActionsDB {
    private static String databaseUrl = "jdbc:sqlite:bingo_results.db";
    
    // Connect to a database
    public static void connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(databaseUrl);
            
            System.out.println("CONNECTION TO SQLITE HAS BEEN ESTABLISHED");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // If conection is not equal to null, close the connection
                if (con != null) {
                    con.close();
                }
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    // Create VARIAIONS data table
    public static void createVariationsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS VARIATIONS (\n"
                + "ID integer PRIMARY KEY,\n"
                + "VARIATION_ID integer NOT NULL,\n" 
                + "SELECTED_NUMBERS text NOT NULL,\n" 
                + "DATE text NOT NULL,\n"
                + "EMAIL text NOT NULL" + ")";
        
        try (Connection con = DriverManager.getConnection(databaseUrl)){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("CREATED VARIATIONS TABLE");
        } catch (SQLException e) {
           System.out.println(e.getMessage()); 
        }
    }
    
    public static void insertInVariationsTable(Variation v) {
        if ( v instanceof Variation) {
            try (Connection con = DriverManager.getConnection(databaseUrl)){                
                String sql = "INSERT INTO VARIATIONS(VARIATION_ID, SELECTED_NUMBERS, DATE, EMAIL) "
                        + "VALUES (?, ?, ?, ?)";
                
                // Set query values by their indexes by using PreparedStatement
                PreparedStatement pstmt = con.prepareStatement(sql);
                
                pstmt.setInt(1, v.getVariationNr());
                pstmt.setString(2, Arrays.toString(v.getSelectedNumbers().toArray()));
                pstmt.setString(3, v.getDateTime().toString());
                pstmt.setString(4, v.getClientEmail());
                
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Passed object is not instance of Variation class");
        }
    }
    
    public static void selectData(Date d) {
        try (Connection con = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT DATE, EMAIL, SELECTED_NUMBERS FROM VARIATIONS");
            
            while(rs.next()) {
                String email = rs.getString("EMAIL");
                String date = rs.getString("DATE");
                String selectedNumbers = rs.getString("SELECTED_NUMBERS");

                if (date.substring(0, 19).equals(d.toString().substring(0, 19))) {
                    System.out.println("[");
                    System.out.println("Date: " + date + "\n");
                    System.out.println("EMAIL: " + email + "\n");
                    System.out.println("SELECTED NUMBERS: " + selectedNumbers);
                    System.out.println("]");
                }
            }
            
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
