package kaserne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    // Pfad zu deiner SQLite-Datei
    String url = "jdbc:sqlite:P:\\\\IFSY12\\\\sqllite\\\\Kaserne\\\\Datenbank_Kaserne.db";

    public void connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(url)) {
                System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC-Treiber nicht gefunden.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Fehler bei Verbindung: " + e.getMessage());
        }
    }

    public void benutzerErstellen(String benutzername, String passwort) {
        String sql = "INSERT INTO Benutzer (Benutzername, Passwort) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, benutzername);
            pstmt.setString(2, passwort);

            int rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    

    public boolean benutzerLogin(String benutzername, String passwort) {
        String sql = "SELECT 1 FROM Benutzer WHERE Benutzername = ? AND Passwort = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, benutzername);
            pstmt.setString(2, passwort);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true, wenn Benutzer existiert
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
